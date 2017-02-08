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
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Rect;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.gbrf.adapter.AlertDialogManager;
import com.gbrf.adapter.ConnectionDetector;
import com.gbrf.dto.UserDTO;
@SuppressLint("NewApi")
public class ForgotPasswordActivity extends ActionBarActivity implements
		LoaderCallbacks<Cursor> {

	private UserForgetTask mForgetTask = null;
	private AutoCompleteTextView mEmailView;
	AlertDialogManager alert = new AlertDialogManager();
	private View mProgressView;
	ConnectionDetector cd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_password);
		ActionBar actionBar = getSupportActionBar();
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		actionBar.setTitle("GBRF");
		actionBar.setDisplayUseLogoEnabled(true);
		getSupportActionBar().setIcon(R.drawable.logo);
		mEmailView = (AutoCompleteTextView) findViewById(R.id.EnterEmail);
		RelativeLayout touchInterceptor = (RelativeLayout) findViewById(R.id.forget);
		touchInterceptor.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					if (mEmailView.isFocused()) {
						Rect outRect = new Rect();
						mEmailView.getGlobalVisibleRect(outRect);
						if (!outRect.contains((int) event.getRawX(),
								(int) event.getRawY())) {
							mEmailView.clearFocus();
							InputMethodManager imm = (InputMethodManager) v
									.getContext().getSystemService(
											Context.INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
						}
					}

				}
				return false;
			}
		});
		//populateAutoComplete();
		Button mforgotInButton = (Button) findViewById(R.id.forgot);
		mforgotInButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				cd = new ConnectionDetector(getApplicationContext());
				// Check for internet connection
				if (!cd.isConnectingToInternet()) {
					// Internet Connection is not present
					alert.showAlertDialog(ForgotPasswordActivity.this,
							"Internet Connection Error",
							"Please connect to working Internet connection",
							false);
					// stop executing code by return
					return;
				}
				attemptLogin();
			}
		});
		mProgressView = findViewById(R.id.forget_progress);
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
		if (mForgetTask != null) {
			return;
		}
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		String email = mEmailView.getText().toString();
		String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


		// Check for a valid email address.
		if (TextUtils.isEmpty(email)) {
			   alert.setMessage("Please enter your email"); 
			    alert.setPositiveButton("OK", null);
			    alert.show();
		}
		else if (!(email.matches(emailPattern))) {
			   alert.setMessage("Please enter valid email"); 
			    alert.setPositiveButton("OK", null);
			    alert.show();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			showProgress(true);
			mForgetTask = new UserForgetTask(email);
			mForgetTask.execute((Void) null);
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
				ForgotPasswordActivity.this,
				android.R.layout.simple_dropdown_item_1line,
				emailAddressCollection);

		mEmailView.setAdapter(adapter);
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
	 * Represents an asynchronous forget task used to authenticate the user.
	 */
	public class UserForgetTask extends AsyncTask<Void, Void, Boolean> {

		private final String mEmail;

		UserForgetTask(String email) {

			mEmail = email;

		}

		@Override
		protected Boolean doInBackground(Void... params) {

			DataSyncService service = new DataSyncService();
			try {
				UserDTO dto = service.forgetpassword(mEmail);
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
			mForgetTask = null;

			System.out.println(success);

			if (success) {
				AlertDialog.Builder alert = new AlertDialog.Builder(
						ForgotPasswordActivity.this);
				alert.setMessage(((PE) getApplicationContext()).getUserDTO()
						.getMessage());
				alert.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent homeIntent = new Intent(
										getApplicationContext(),
										LoginActivity.class);
								startActivity(homeIntent);

								finish();
							}
						});
				alert.show();

			} else {
				AlertDialog.Builder alert = new AlertDialog.Builder(ForgotPasswordActivity.this);
				alert.setMessage(((PE) getApplicationContext()).getUserDTO()
						.getMessage());
				alert.setPositiveButton("OK", null);
				alert.show();
			}
			showProgress(false);
		}

		@Override
		protected void onCancelled() {
			mForgetTask = null;
			showProgress(false);
		}
	}
}
