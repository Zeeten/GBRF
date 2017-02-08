package com.gbrf;

import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
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
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.gbrf.dto.UserDTO;

@SuppressLint("NewApi")
public class InvitationActivity extends ActionBarActivity implements
		LoaderCallbacks<Cursor> {

	private UserRegistrationTask mRegistrationTask = null;

	// UI references.
	private AutoCompleteTextView mNameView;
	private AutoCompleteTextView mMobileNoView;
	private AutoCompleteTextView mEmailView;
	private View mProgressView;
	private View mRegistrationFormView;
	GPSTracker gps;
	double latitude = 0.0;
	double longitude = 0.0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invitation);
		// create class object
		gps = new GPSTracker(InvitationActivity.this);

		// check if GPS enabled
		if (gps.canGetLocation()) {

			latitude = gps.getLatitude();
			longitude = gps.getLongitude();

			// \n is for new line
			// Toast.makeText(getApplicationContext(),
			// "Your Location is - \nLat: " + latitude + "\nLong: " + longitude,
			// Toast.LENGTH_LONG).show();
		} else {
			// can't get location
			// GPS or Network is not enabled
			// Ask user to enable GPS/network in settings
			gps.showSettingsAlert();
		}
		ActionBar actionBar = getSupportActionBar();
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		actionBar.setTitle("");
		actionBar.setDisplayUseLogoEnabled(true);
		getSupportActionBar().setIcon(R.drawable.logo);
		mNameView = (AutoCompleteTextView) findViewById(R.id.name);
		mNameView.setHeight(60);
		mMobileNoView = (AutoCompleteTextView) findViewById(R.id.mobileno);
		mMobileNoView.setHeight(60);
		mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
		mEmailView.setHeight(60);
		loadSavedPreferences();
		//populateAutoComplete();
		Button mEmailSubmitInButton = (Button) findViewById(R.id.email_submit_button);
		mEmailSubmitInButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				attemptLogin();
			}
		});
		mRegistrationFormView = findViewById(R.id.registration_form);
		mProgressView = findViewById(R.id.registration_progress);

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
		if (mRegistrationTask != null) {
			return;
		}

		// Reset errors.
		mNameView.setError(null);
		mMobileNoView.setError(null);
		mEmailView.setError(null);

		// Store values at the time of the login attempt.
		String name = mNameView.getText().toString();
		String mobileNo = mMobileNoView.getText().toString();
		String email = mEmailView.getText().toString();
		String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
		double lat = latitude;
		double longe = longitude;

		boolean cancel = false;
		View focusView = null;

		// Check for a valid FirstName, if the user entered one.
		if (TextUtils.isEmpty(name)) {
			mNameView.setError(getString(R.string.error_field_required));
			focusView = mNameView;
			cancel = true;
		}

		/*
		 * // Check for a valid Mobile, if the user entered one. if
		 * (TextUtils.isEmpty(mobileNo)) {
		 * mMobileNoView.setError(getString(R.string.error_field_required));
		 * focusView = mMobileNoView; cancel = true; }
		 */

		// Check for a valid email address.
		if (TextUtils.isEmpty(email)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		}
		if (!(email.matches(emailPattern))) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			showProgress(true);
			mRegistrationTask = new UserRegistrationTask(name, mobileNo, email,
					lat, longe);
			mRegistrationTask.execute((Void) null);
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

			mRegistrationFormView
					.setVisibility(show ? View.GONE : View.VISIBLE);
			mRegistrationFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mRegistrationFormView
									.setVisibility(show ? View.GONE
											: View.VISIBLE);
						}
					});

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
			mRegistrationFormView
					.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

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

	@Override
	public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
		List<String> emails = new ArrayList<String>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			emails.add(cursor.getString(ProfileQuery.ADDRESS));
			cursor.moveToNext();
		}

		addEmailsToAutoComplete(emails);
	}

	@Override
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
				InvitationActivity.this,
				android.R.layout.simple_dropdown_item_1line,
				emailAddressCollection);

		mEmailView.setAdapter(adapter);
	}

	private void loadSavedPreferences() {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		boolean cbValue = sp.getBoolean("CHECKBOX", false);
		if (cbValue) {
			Intent intent = new Intent(InvitationActivity.this,
					LandingPageActivity.class);
			startActivity(intent);
		}

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
	public class UserRegistrationTask extends AsyncTask<Void, Void, Boolean> {

		private final String mName;
		private final String mmobileNo;
		private final String mEmail;
		private final double mlatitude;
		private final double mlongitude;

		UserRegistrationTask(String Name, String mobileNo, String email,
				double latitude, double longitude) {
			mName = Name;
			mmobileNo = mobileNo;
			mEmail = email;
			mlatitude = latitude;
			mlongitude = longitude;
		}

		@Override
		protected Boolean doInBackground(Void... params) {

			DataSyncService service = new DataSyncService();
			try {
				UserDTO dto = service.invitation(mName, mmobileNo,mlatitude,mlongitude);
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
			mRegistrationTask = null;

			System.out.println(success);

			if (success) {

				savePreferences("CHECKBOX", true);
				savePreferences("NAME", mNameView.getText().toString());
				savePreferences("TimeZone", ((PE) getApplicationContext())
						.getUserDTO().getTimeZone());

				Toast.makeText(getApplicationContext(),
						"Registration Successful", Toast.LENGTH_SHORT).show();
				Intent homeIntent = new Intent(getApplicationContext(),
						LandingPageActivity.class);
				startActivity(homeIntent);

				finish();

			} else {

				Toast.makeText(
						getApplicationContext(),
						((PE) getApplicationContext()).getUserDTO()
								.getMessage(), Toast.LENGTH_SHORT).show();

			}

			showProgress(false);
		}

		@Override
		protected void onCancelled() {
			mRegistrationTask = null;
			showProgress(false);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.invitation, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
