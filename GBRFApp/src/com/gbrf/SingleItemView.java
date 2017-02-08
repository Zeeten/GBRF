package com.gbrf;

import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.Session;
import com.gbrf.dto.UserDTO;
@SuppressLint("NewApi")
public class SingleItemView extends ActionBarActivity implements
LoaderCallbacks<Cursor> {
	String bookname;
	String authername;
	String price;
	String image;
	String publisher;
	String isbn,currencySymbol;
	String pages;
	String format;
	String description;
	String position;
	String nid;
	String instock;
	String purchased;
	String muId,msid,mssid,uId,oid;
	private String account;
	private UserLogoutTask mLogoutTask = null;

	ImageLoader imageLoader = new ImageLoader(this);
	private UserOrderTask mOrderTask = null;
	private View mProgressView;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from singleitemview.xml
		setContentView(R.layout.activity_single_item_view);
		ActionBar actionBar = getSupportActionBar();
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		actionBar.setTitle("Global Book Store");
		actionBar.setDisplayUseLogoEnabled(true);
		getSupportActionBar().setIcon(R.drawable.logo);
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		msid = preferences.getString("sid", "Test");
		mssid = preferences.getString("ssid", "Test");
		uId = preferences.getString("UID", "Test");
		muId = preferences.getString("UID", "Test");
		currencySymbol = preferences.getString("CurrencySymbol", "Test");
		account = preferences.getString("account", "Test");

	   // getSupportActionBar().setBackgroundDrawable(colorDrawable);
		Intent i = getIntent();
		// Get the result of rank
		bookname = i.getStringExtra("bookname");
		// Get the result of country
		authername = i.getStringExtra("authername");
		// Get the result of population
		price = i.getStringExtra("price");
		// Get the result of flag
		image = i.getStringExtra("image");
		publisher = i.getStringExtra("publisher");
		isbn = i.getStringExtra("isbn");
		pages = i.getStringExtra("pages");
		format = i.getStringExtra("format");
		description = i.getStringExtra("description");
		instock = i.getStringExtra("in_stock");
		nid = i.getStringExtra("nid");
		oid = i.getStringExtra("oid");
		purchased = i.getStringExtra("purchased");
		savePreferences("nid",nid);

		// Locate the TextViews in singleitemview.xml
		TextView txtbookname = (TextView) findViewById(R.id.bookname);
		TextView txtauthername = (TextView) findViewById(R.id.authorinfo);
		TextView txtprice = (TextView) findViewById(R.id.textView8);
		TextView txtpublisher = (TextView) findViewById(R.id.publisherinfo);
		TextView txtisbn = (TextView) findViewById(R.id.textView2);
		TextView txtpages = (TextView) findViewById(R.id.textView4);
		TextView txtformat = (TextView) findViewById(R.id.textView6);
		TextView txtpricelable = (TextView) findViewById(R.id.textView7);
		txtpricelable.setText("Price:"+currencySymbol);
		// Locate the ImageView in singleitemview.xml
		ImageView txtimage = (ImageView) findViewById(R.id.product_detail_img);
		TextView txtdescription = (TextView) findViewById(R.id.textView10);
		Button btnbuynow=(Button)findViewById(R.id.buynow);
		Button btnreadnow=(Button)findViewById(R.id.readnow);

		// Set results to the TextViews
		txtbookname.setText(bookname);
		txtauthername.setText(authername);
		txtpublisher.setText(publisher);
		txtisbn.setText(isbn);
		txtpages.setText(pages);
		txtformat.setText(format);
		txtdescription.setText(description);
		txtprice.setText(price);
 
		// Capture position and set results to the ImageView
		// Passes flag images URL into ImageLoader.class
		imageLoader.DisplayImage(image, txtimage);
		if(purchased.equalsIgnoreCase("true")){
			btnreadnow.setVisibility(View.VISIBLE);
			btnbuynow.setVisibility(View.GONE); 
		 }else{
			 btnbuynow.setVisibility(View.VISIBLE);
			 btnreadnow.setVisibility(View.GONE); 
		 }
		btnbuynow.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(SingleItemView.this,
						CheckoutActivity.class);
				intent.putExtra("bookname",bookname);

				intent.putExtra("price",price);
				// Pass all data flag
				intent.putExtra("image",image);
				intent.putExtra("in_stock",instock);

				startActivity(intent);
			}
		});
		btnreadnow.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mOrderTask = new UserOrderTask(nid, uId,oid,msid,mssid);
				mOrderTask.execute((Void) null);
			}
		});
		mProgressView = findViewById(R.id.logout_progress);
		
	}
	private void savePreferences(String key, boolean value) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor edit = sp.edit();
		edit.putBoolean(key, value);
		edit.commit();
	}
	private void savePreferences(String key, String value) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor edit = sp.edit();
		edit.putString(key, value);
		edit.commit();
	}
	
	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mProgressView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mProgressView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);

		}
	}

	@SuppressLint("NewApi")
	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		return new CursorLoader(this,
				// Retrieve data rows for the device user's 'profile' contact.
				Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
						ContactsContract.Contacts.Data.CONTENT_DIRECTORY),
				ProfileQuery.PROJECTION,

				// Select only email addresses.
				ContactsContract.Contacts.Data.MIMETYPE + " = ?",
				new String[] { ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE },

				// Show primary email addresses first. Note that there won't be
				// a primary email address if the user hasn't specified one.
				ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
	}

	public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
		List<String> emails = new ArrayList<String>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			emails.add(cursor.getString(ProfileQuery.ADDRESS));
			cursor.moveToNext();
		}

		addEmailsToAutoComplete(emails);
	}

	public void onLoaderReset(Loader<Cursor> cursorLoader) {

	}

	private interface ProfileQuery {
		String[] PROJECTION = { ContactsContract.CommonDataKinds.Email.ADDRESS,
				ContactsContract.CommonDataKinds.Email.IS_PRIMARY, };

		int ADDRESS = 0;
		int IS_PRIMARY = 1;
	}

	/**
	 * Use an AsyncTask to fetch the user's email addresses on a background
	 * thread, and update the email text field with results on the main UI
	 * thread.
	 */
	class SetupEmailAutoCompleteTask extends
			AsyncTask<Void, Void, List<String>> {

		@Override
		protected List<String> doInBackground(Void... voids) {
			ArrayList<String> emailAddressCollection = new ArrayList<String>();

			// Get all emails from the user's contacts and copy them to a list.
			ContentResolver cr = getContentResolver();
			Cursor emailCur = cr.query(
					ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
					null, null, null);
			while (emailCur.moveToNext()) {
				String email = emailCur
						.getString(emailCur
								.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
				emailAddressCollection.add(email);
			}
			emailCur.close();

			return emailAddressCollection;
		}

		@Override
		protected void onPostExecute(List<String> emailAddressCollection) {
			addEmailsToAutoComplete(emailAddressCollection);
		}
	}

	private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
		// Create adapter to tell the AutoCompleteTextView what to show in its
		// dropdown list.
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				SingleItemView.this,
				android.R.layout.simple_dropdown_item_1line,
				emailAddressCollection);

	}

	public class UserLogoutTask extends AsyncTask<Void, Void, Boolean> {

		private final String muid;
		private final String msid;
		private final String mssid;

		UserLogoutTask(String uid, String sid, String ssid) {
			muid = uid;
			msid = sid;
			mssid = ssid;

		}

		@Override
		protected Boolean doInBackground(Void... params) {

			DataSyncService service = new DataSyncService();
			try {
				UserDTO dto = service.logout(muid, msid, mssid);
				((PE) getApplicationContext()).setUserDTO(dto);
				if (dto.getStatus().equals("success")) {
					return true;
				}
			} catch (Exception e) {

				e.printStackTrace();

			}

			return false;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mLogoutTask = null;

			if (success) {
				SharedPreferences SM = getSharedPreferences("userrecord", 0);
				Editor edit = SM.edit();
				edit.putBoolean("userlogin", false);
				edit.commit();
				Intent homeIntent = new Intent(SingleItemView.this,
						LoginActivity.class);
				startActivity(homeIntent);
				finish();

			} else {

				Intent homeIntent = new Intent(SingleItemView.this,
						SingleItemView.class);
				startActivity(homeIntent);

			}
			showProgress(false);

		}

		protected void onCancelled() {
			mLogoutTask = null;
			showProgress(false);

		}
	}
	
	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserOrderTask extends AsyncTask<Void, Void, Boolean> {

		private final String mnId;
		private final String muId;
		private final String msId;
		private final String mssId;
		private final String morderId;

		UserOrderTask(String nId, String uId,String orderId,String sId,String ssId) {

			mnId = nId;
			muId = uId;
			morderId=orderId;
			msId=sId;
			mssId=ssId;

		}

		@Override
		protected Boolean doInBackground(Void... params) {
			DataSyncService service=new DataSyncService();
/*
			StringBuilder ORDER_URL = new StringBuilder("http://theacademicworld.com/book/read?nid="+mnId);
			ORDER_URL.append("&uid="+muId);
			ORDER_URL.append("&oid="+morderId);
			ORDER_URL.append("&device=android");
			String URL=ORDER_URL.toString();
			savePreferences("URL",URL);
*/
			try {
				UserDTO dto = service.BookRead(muId, mnId, morderId,msId,mssId);
				((PE)getApplicationContext()).setUserDTO(dto);
				if (dto.getStatus().equals("success")) {
					return true;
				}
				

			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}

	 @Override
		protected void onPostExecute(final Boolean success) {
			mOrderTask = null;
			if (success) {
				savePreferences("URL", ((PE) getApplicationContext())
						.getUserDTO().getUrl());
				Intent browserIntent = new Intent(getApplicationContext(), WebViewBookActivity.class);
				browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getApplicationContext().startActivity(browserIntent);
			}else{
				Builder alert = new AlertDialog.Builder(SingleItemView.this,
						R.style.AlertDialogCustom);
				alert.setMessage(((PE) getApplicationContext()).getUserDTO()
						.getMessage());
				alert.setPositiveButton("OK", null);
				alert.show();
			}
		}

		@Override
		protected void onCancelled() {
			mOrderTask = null;
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		// noinspection SimplifiableIfStatement
		if (id == R.id.action_mybook) {
			Intent homeIntent = new Intent(this.getApplicationContext(),
					OrderHistoryActivity.class);
			startActivity(homeIntent);
			return true;

		}
		// noinspection SimplifiableIfStatement
		if (id == R.id.action_logout) {
			System.out.println("logout");
			if (account.equalsIgnoreCase("taw")) {
				attemptLogout();
				// return true;
			} else if (account.equalsIgnoreCase("facebook")) {
				System.out.println("if facebook condition");
				logoutFromFacebook(getApplicationContext());
				// return true;
			}

		}
		return super.onOptionsItemSelected(item);
	}
	
	public void attemptLogout() {
		if (mLogoutTask != null) {
			return;
		}

		String uid = muId;
		String sid = msid;
		String ssid = mssid;

		boolean cancel = false;
		View focusView = null;

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			showProgress(true);
			mLogoutTask = new UserLogoutTask(uid, sid, ssid);
			mLogoutTask.execute((Void) null);
		}
	}

	/**
	 * Function to Logout user from Facebook
	 * */
	public void logoutFromFacebook(Context context) {

		Session session = Session.getActiveSession();
		if (session != null) {

			if (!session.isClosed()) {
				session.closeAndClearTokenInformation();
				attemptLogout();
			}
		} else {
			session = new Session(context);
			Session.setActiveSession(session);

			session.closeAndClearTokenInformation();
			// clear your preferences if saved

		}
	}
	

}
