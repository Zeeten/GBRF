package com.gbrf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.app.ProgressDialog;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.facebook.Session;
import com.gbrf.adapter.AlertDialogManager;
import com.gbrf.adapter.ConnectionDetector;
import com.gbrf.dto.UserDTO;

@SuppressLint("NewApi")
public class BookDetailActivity extends ActionBarActivity implements
		LoaderCallbacks<Cursor> {

	// Declare Variables
	JSONObject json;
	JSONArray bookJson;
	JSONArray convertJson;
	ListView listview;
	ListViewAdapter adapter;
	ProgressDialog mProgressDialog;
	// Connection detector
	ConnectionDetector cd;
	private UserLogoutTask mLogoutTask = null;
	private String uId, currency;
	private View mProgressView;
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	ArrayList<HashMap<String, String>> arraylist;
	static String BOOKNAME = "bookname";
	static String AUTHERNAME = "authername";
	static String PRICE = "price";
	static String IMAGE = "image";
	static String PUBLISHER = "publisher";
	static String ISBN = "isbn";
	static String PAGES = "pages";
	static String FORMAT = "format";
	static String DESCRIPTION = "description";
	static String NID = "nid";
	static String ORDERID = "oid";
	static String PURCHASED = "purchased";
	static String INSTOCK = "in_stock";
	private String muId, msid, mssid, account;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_detail);
		ActionBar actionBar = getSupportActionBar();
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		actionBar.setTitle("Global Book Store");
		actionBar.setDisplayUseLogoEnabled(true);
		getSupportActionBar().setIcon(R.drawable.logo);
		cd = new ConnectionDetector(getApplicationContext());
		// Check for internet connection
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(BookDetailActivity.this,
					"Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		uId = preferences.getString("UID", "Test");
		muId = preferences.getString("UID", "Test");
		currency = preferences.getString("CurrencyCode", "Test");
		System.out.println("CurrencyCode" + currency);
		account = preferences.getString("account", "Test");
		msid = preferences.getString("sid", "Test");
		mssid = preferences.getString("ssid", "Test");
		new DownloadJSON().execute();
		mProgressView = findViewById(R.id.logout_progress);

	}

	// DownloadJSON AsyncTask
	private class DownloadJSON extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(BookDetailActivity.this);
			mProgressDialog.setMessage("Listing Book ...");
			mProgressDialog.setIndeterminate(false);
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
		}

		@SuppressWarnings("deprecation")
		@Override
		protected Void doInBackground(Void... params) {
			// Create an array
			arraylist = new ArrayList<HashMap<String, String>>();
			// Retrieve JSON Objects from the given URL address
			JSONParser jsonParser = new JSONParser();
			String url = "http://theacademicworld.com/api/rest/books";
			String method = "GET";
			List param = new ArrayList();
			param.add(new BasicNameValuePair("uid", uId));
			param.add(new BasicNameValuePair("currency", currency));
			json = jsonParser.makeHttpRequest(url, method, param);
			System.out.println("lll" + json);

			try {

				// Locate the array name in JSON
				bookJson = json.getJSONArray("data");
				System.out.println("jjj" + bookJson);
				for (int i = 0; i < bookJson.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject jsonOBject = bookJson.getJSONObject(i);

					String price = jsonOBject.getString("cost_price");

					String name = jsonOBject.getString("title");
					String image = jsonOBject.getString("image");
					image.replace("\\", "");
					String authername = jsonOBject.getString("author");
					String publisher = jsonOBject.getString("publisher");
					String isbn = jsonOBject.getString("isbn");
					String pages = jsonOBject.getString("pages");
					String format = jsonOBject.getString("format");
					String description = jsonOBject.getString("description");
					String nid = jsonOBject.getString("nid");
					String purchase = jsonOBject.getString(PURCHASED);
					if (purchase.equalsIgnoreCase("true")) {
						String oid = jsonOBject.getString(ORDERID);
						map.put(ORDERID, oid);
					}

					String instock = jsonOBject.getString(INSTOCK);
					map.put("authername", authername);
					map.put("publisher", publisher);
					map.put("isbn", isbn);
					map.put("pages", pages);
					map.put("format", format);
					map.put("description", description);
					map.put("nid", nid);
					map.put(PURCHASED, purchase);
					map.put(INSTOCK, instock);

					// Set the JSON Objects into the array
					System.out.println(image);
					map.put("bookname", name);

					map.put("price", price);
					map.put("image", image);
					arraylist.add(map);
				}
			} catch (JSONException e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void args) {
			// Locate the listview in listview_main.xml
			listview = (ListView) findViewById(R.id.listview);
			// Pass the results into ListViewAdapter.java
			adapter = new ListViewAdapter(BookDetailActivity.this, arraylist);
			// Set the adapter to the ListView
			listview.setAdapter(adapter);
			// Close the progressdialog
			mProgressDialog.dismiss();
		}
	}

	private void savePreferences(String key, boolean value) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor edit = sp.edit();
		edit.putBoolean(key, value);
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
				BookDetailActivity.this,
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
				Intent homeIntent = new Intent(BookDetailActivity.this,
						LoginActivity.class);
				startActivity(homeIntent);
				finish();

			} else {

				Intent homeIntent = new Intent(BookDetailActivity.this,
						BookDetailActivity.class);
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
		getMenuInflater().inflate(R.menu.book_detail, menu);
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
