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

public class EventActivity extends ActionBarActivity implements
LoaderCallbacks<Cursor> {

	// Declare Variables
		JSONObject json;
		JSONArray eventJson;
		//JSONArray invitationArrayJson;
		JSONArray convertJson;
		ListView listview;
		EventListViewAdapter adapter;
		ProgressDialog mProgressDialog;
		// Connection detector
		ConnectionDetector cd;

		private String uId;
		private UserLogoutTask mLogoutTask = null;
		private View mProgressView;
		// Alert dialog manager
		AlertDialogManager alert = new AlertDialogManager();
		ArrayList<HashMap<String, String>> arraylist;
		static String BOOKNAME = "bookname";
		static String IMAGE = "image";
		static String THUMBNAILIMAGE = "thumbnailimage";
		static String DESCRIPTION = "description";
		static String NID = "nid";
		static String ORDERID = "oid";
		static String RELEASEON="releaseon";
		static String STARTDATE="startdate";
		static String ENDDATE="enddate";
		static String IID="iid";
		static String ACCEPTEDON="accepted_on";
		static String RELEASEDON="released_on";
		static String INVITATIONSTATUS="invitationstatus";
		private String  muId, msid, mssid,account;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event);
		ActionBar actionBar = getSupportActionBar();
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		actionBar.setTitle("GBRF");
		actionBar.setDisplayUseLogoEnabled(true);
		getSupportActionBar().setIcon(R.drawable.logo);
		cd = new ConnectionDetector(getApplicationContext());
		// Check for internet connection
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(EventActivity.this,
					"Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		uId = preferences.getString("UID", "Test");
		muId = preferences.getString("UID", "Test");
		account = preferences.getString("account", "Test");
		msid = preferences.getString("sid", "Test");
		mssid = preferences.getString("ssid", "Test");
		new DownloadJSON().execute();
		mProgressView = findViewById(R.id.logout_progress);

	}
	private void savePreferences(String key, String value) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor edit = sp.edit();
		edit.putString(key, value);
		edit.commit();
	}

	// DownloadJSON AsyncTask
	private class DownloadJSON extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(EventActivity.this);
			mProgressDialog.setMessage("Listing Event ...");
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
			String url = "http://theacademicworld.com/api/rest/events";
			String method = "GET";
			List param = new ArrayList();
			param.add(new BasicNameValuePair("uid", uId));
			json = jsonParser.makeHttpRequest(url, method, param);
			System.out.println("lll" + json);

			try {

				if (json.getString("status").equalsIgnoreCase("success")) {
					// Locate the array name in JSON
					eventJson = json.getJSONArray("data");
					System.out.println("jjj" + eventJson);
				for (int i = 0; i < eventJson.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject jsonOBject = eventJson.getJSONObject(i);
					String name = jsonOBject.getString("title");
					String image = jsonOBject.getString("cover");
					image.replace("\\", "");
					String thumbnailimage = jsonOBject.getString("thumbnail");
					thumbnailimage.replace("\\", "");
					String description = jsonOBject.getString("description");
					String releaseon = jsonOBject.getString("released_on");
					//savePreferences("releasedon", releaseon);
					String startdate = jsonOBject.getString("startdate");
					String enddate = jsonOBject.getString("enddate");
					String nid = jsonOBject.getString("nid");
					//savePreferences("nId", nid);
					String invitationstatus = jsonOBject.getString("invitaion_status");
					if(jsonOBject.getString("invitaion_status").equalsIgnoreCase("accepted")){
						
						String invitations = jsonOBject.getString("invitations");
						JSONArray invitationsjsarray = new JSONArray(invitations);
						for (int j = 0; j < invitationsjsarray.length(); j++) {
							JSONObject jsonOBj = invitationsjsarray.getJSONObject(j);
							String iid = jsonOBj.getString("iid");
							String accepted_on = jsonOBj.getString("accepted_on");
							String released_on = jsonOBj.getString("released_on");
							//savePreferences("released_on", released_on);
							//savePreferences("iid", iid);
							map.put("iid", iid);
							map.put("accepted_on", accepted_on);
							map.put("released_on", released_on);
							
						}
					}
					map.put("description", description);
					map.put("nid", nid);
					// Set the JSON Objects into the array
					System.out.println(image);
					map.put("bookname", name);
					map.put("startdate", startdate);
					map.put("releaseon", releaseon);
					map.put("enddate", enddate);
					map.put("invitationstatus", invitationstatus);
					map.put("image", image);
					map.put("thumbnailimage", thumbnailimage);
					arraylist.add(map);
				}
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
			adapter = new EventListViewAdapter(EventActivity.this, arraylist);
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
				EventActivity.this,
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
				Intent homeIntent = new Intent(EventActivity.this,
						LoginActivity.class);
				startActivity(homeIntent);
				finish();

			} else {

				Intent homeIntent = new Intent(EventActivity.this,
						EventActivity.class);
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
