package com.gbrf;

import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
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
import android.os.Build.VERSION;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;

import com.facebook.Session;
import com.gbrf.dto.UserDTO;

@SuppressLint("NewApi")
public class MainActivity extends ActionBarActivity implements
		LoaderCallbacks<Cursor> {

	private UserInvitationTask mInvitationTask = null;
	private UserLogoutTask mLogoutTask = null;
	private UserCurrentLocationTask mUserCurrentLocationTask = null;
	GPSTracker gps;
	double latitude = 0.0;
	double longitude = 0.0;
	private String muId,msid,mssid,account,name,countryName;
	private View mProgressView;
	//private static String APP_ID = "888751001205538"; 
	// ID

	// Instance of Facebook Class
	//private Facebook facebook = new Facebook(APP_ID);
	//private AsyncFacebookRunner mAsyncRunner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gps = new GPSTracker(MainActivity.this);
		// check if GPS enabled
		if (gps.canGetLocation()) {

			latitude = gps.getLatitude();
			longitude = gps.getLongitude();

		} else {
			gps.showSettingsAlert();
		}
		ActionBar actionBar = getSupportActionBar();
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		actionBar.setTitle("GBRF");
		actionBar.setDisplayUseLogoEnabled(true);
		getSupportActionBar().setIcon(R.drawable.logo);
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		muId = preferences.getString("UID", "Test");
		msid = preferences.getString("sid", "Test");
		mssid=preferences.getString("ssid", "Test");
		name=preferences.getString("Name", "Test");
		countryName=preferences.getString("CountryName", "Test");
		System.out.println("uid" + muId);
		System.out.println("name" + name);
		account = preferences.getString("account", "Test");
		System.out.println("account"+account);
		RelativeLayout gbrf = (RelativeLayout) findViewById(R.id.relative_layout_gbrf);
		RelativeLayout store = (RelativeLayout) findViewById(R.id.relative_layout_global_book_resource);
		RelativeLayout award = (RelativeLayout) findViewById(R.id.relative_layout_awards);
/*		RelativeLayout music = (RelativeLayout) findViewById(R.id.relative_layout_literary_music);
		RelativeLayout authoragore = (RelativeLayout) findViewById(R.id.relative_layout_authors_agora);
		RelativeLayout publishinghouse = (RelativeLayout) findViewById(R.id.relative_layout_tbh);
		RelativeLayout securityskills = (RelativeLayout) findViewById(R.id.relative_layout_security_skills);*/
		mUserCurrentLocationTask = new UserCurrentLocationTask(latitude,
				longitude);
		mUserCurrentLocationTask.execute((Void) null);
	//	mAsyncRunner = new AsyncFacebookRunner(facebook);
		store.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
			/*	Intent intent = new Intent(MainActivity.this,
						BookDetailActivity.class);
				startActivity(intent);*/
				AlertDialog.Builder alert = new AlertDialog.Builder(
						MainActivity.this);
				alert.setMessage("Being released shortly");
				alert.setPositiveButton("OK", null);
				alert.show();
			}
		});

		award.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
			/*	Intent intent = new Intent(MainActivity.this,
						AwardActivity.class);
				startActivity(intent);*/
				AlertDialog.Builder alert = new AlertDialog.Builder(
						MainActivity.this);
				alert.setMessage("Being released shortly");
				alert.setPositiveButton("OK", null);
				alert.show();
			}		
		});

		/*music.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						MusicHomeActivity.class);
				startActivity(intent);
			}
		});
		authoragore.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				AlertDialog.Builder alert = new AlertDialog.Builder(
						MainActivity.this);
				alert.setMessage("Being released shortly");
				alert.setPositiveButton("OK", null);
				alert.show();
			}
		});
		securityskills.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Intent	intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=com.acmatics.securitygaurdexchange"));
				startActivity(intent);

			}
		});

		publishinghouse.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(
						MainActivity.this);
				alert.setMessage("Being released shortly");
				alert.setPositiveButton("OK", null);
				alert.show();
			}
		});*/
		//populateAutoComplete();
		gbrf.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//attemptLogin();
				Intent intent = new Intent(MainActivity.this,
						EventActivity.class);
				startActivity(intent);
			}
		});
		mProgressView = findViewById(R.id.gbrf_progress);

	}

	private void populateAutoComplete() {
		if (VERSION.SDK_INT >= 14) {
			// Use ContactsContract.Profile (API 14+)
			getLoaderManager().initLoader(0, null, this);
		} else if (VERSION.SDK_INT >= 8) {
			// Use AccountManager (API 8+)
			new SetupEmailAutoCompleteTask().execute(null, null);
		}
	}

	public void attemptLogin() {
		if (mInvitationTask != null) {
			return;
		}

		String uid = muId;

		boolean cancel = false;
		View focusView = null;

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			showProgress(true);
			mInvitationTask = new UserInvitationTask(uid);
			mInvitationTask.execute((Void) null);
		}
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
				MainActivity.this, android.R.layout.simple_dropdown_item_1line,
				emailAddressCollection);

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
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserInvitationTask extends AsyncTask<Void, Void, Boolean> {

		private final String muid;

		UserInvitationTask(String uid) {
			muid = uid;

		}

		@Override
		protected Boolean doInBackground(Void... params) {

			DataSyncService service = new DataSyncService();
			try {
				UserDTO dto = service.invitationcheck(muid);
				((PE) getApplicationContext()).setUserDTO(dto);
				UserDTO userdto = service.releasebook(muid);
				((PE) getApplicationContext()).setUserDTO(userdto);
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
			mInvitationTask = null;

			if (success) {
				savePreferences("bookImage", ((PE) getApplicationContext())
						.getUserDTO().getBookImage());
				savePreferences("releasedon", ((PE) getApplicationContext())
						.getUserDTO().getReleaseOn());
				savePreferences("nId", ((PE) getApplicationContext())
						.getUserDTO().getnId());
				savePreferences("bookname", ((PE) getApplicationContext())
						.getUserDTO().getTitle());
				savePreferences("authorname", ((PE) getApplicationContext())
						.getUserDTO().getAuthorName());
				savePreferences("price", ((PE) getApplicationContext())
						.getUserDTO().getPrice());
				savePreferences("image", ((PE) getApplicationContext())
						.getUserDTO().getBookImage());
				savePreferences("publisher", ((PE) getApplicationContext())
						.getUserDTO().getPublisher());
				savePreferences("isbn", ((PE) getApplicationContext())
						.getUserDTO().getIsbn());
				savePreferences("pages", ((PE) getApplicationContext())
						.getUserDTO().getPages());
				savePreferences("format", ((PE) getApplicationContext())
						.getUserDTO().getFormat());
				savePreferences("description", ((PE) getApplicationContext())
						.getUserDTO().getDescription());
				savePreferences("purchased", ((PE) getApplicationContext())
						.getUserDTO().getPurchase());
				if (((PE) getApplicationContext()).getUserDTO().getPurchase()
						.equalsIgnoreCase("true")) {
					savePreferences("oid", ((PE) getApplicationContext())
							.getUserDTO().getOrderId());
				}
				Intent homeIntent = new Intent(MainActivity.this,
						AwardDetailActivity.class);
				startActivity(homeIntent);

			} else {
				savePreferences("bookImage", ((PE) getApplicationContext())
						.getUserDTO().getBookImage());
				savePreferences("releasedon", ((PE) getApplicationContext())
						.getUserDTO().getReleaseOn());
				savePreferences("nId", ((PE) getApplicationContext())
						.getUserDTO().getnId());
				savePreferences("bookname", ((PE) getApplicationContext())
						.getUserDTO().getTitle());
				savePreferences("authorname", ((PE) getApplicationContext())
						.getUserDTO().getAuthorName());
				savePreferences("price", ((PE) getApplicationContext())
						.getUserDTO().getPrice());
				savePreferences("image", ((PE) getApplicationContext())
						.getUserDTO().getBookImage());
				savePreferences("publisher", ((PE) getApplicationContext())
						.getUserDTO().getPublisher());
				savePreferences("isbn", ((PE) getApplicationContext())
						.getUserDTO().getIsbn());
				savePreferences("pages", ((PE) getApplicationContext())
						.getUserDTO().getPages());
				savePreferences("format", ((PE) getApplicationContext())
						.getUserDTO().getFormat());
				savePreferences("description", ((PE) getApplicationContext())
						.getUserDTO().getDescription());
				savePreferences("purchased", ((PE) getApplicationContext())
						.getUserDTO().getPurchase());
				if (((PE) getApplicationContext()).getUserDTO().getPurchase()
						.equalsIgnoreCase("true")) {
					savePreferences("oid", ((PE) getApplicationContext())
							.getUserDTO().getOrderId());
				}
				Intent homeIntent = new Intent(MainActivity.this,
						InviteActivity.class);
				startActivity(homeIntent);

			}
			showProgress(false);

		}

	}

	protected void onCancelled() {
		mInvitationTask = null;
		showProgress(false);

	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserCurrentLocationTask extends AsyncTask<Void, Void, Boolean> {

		private final double mlatitude;
		private final double mlongitude;

		UserCurrentLocationTask(double latitude, double longitude) {
			mlatitude = latitude;
			mlongitude = longitude;
		}

		@Override
		protected Boolean doInBackground(Void... params) {

			DataSyncService service = new DataSyncService();
			try {
				UserDTO dto = service.currentlocation(mlatitude, mlongitude);
				((PE) getApplicationContext()).setUserDTO(dto);
				System.out.println("data" + dto.getStatus());
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
			mUserCurrentLocationTask = null;

			System.out.println(success);

			if (success) {
				savePreferences("TimeZone", ((PE) getApplicationContext())
						.getUserDTO().getTimeZone());
				savePreferences("CurrencyCode", ((PE) getApplicationContext())
						.getUserDTO().getCurrencycode());
				savePreferences("CurrencySymbol",
						((PE) getApplicationContext()).getUserDTO()
								.getCurrencysymbol());

			}

		}

		@Override
		protected void onCancelled() {
			mUserCurrentLocationTask = null;
		}
	}
	
	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLogoutTask extends AsyncTask<Void, Void, Boolean> {

		private final String muid;
		private final String msid;
		private final String mssid;

		UserLogoutTask(String uid,String sid,String ssid) {
			muid = uid;
			msid=sid;
			mssid=ssid;

		}

		@Override
		protected Boolean doInBackground(Void... params) {

			DataSyncService service = new DataSyncService();
			try {
				UserDTO dto = service.logout(muid,msid,mssid);
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
				Intent homeIntent = new Intent(MainActivity.this,
						LoginActivity.class);
				startActivity(homeIntent);
				finish();

			} else {
			
				Intent homeIntent = new Intent(MainActivity.this,
						MainActivity.class);
				startActivity(homeIntent);

			}
			showProgress(false);

		}
		protected void onCancelled() {
			mLogoutTask = null;
			showProgress(false);

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
				//return true;
			} else if(account.equalsIgnoreCase("facebook")) {
				logoutFromFacebook(getApplicationContext());
				//return true;
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
			mLogoutTask = new UserLogoutTask(uid,sid,ssid);
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
	            //clear your preferences if saved

	    }
	}

}
