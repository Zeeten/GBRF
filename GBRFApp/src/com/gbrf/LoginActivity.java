package com.gbrf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;

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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.gbrf.adapter.AlertDialogManager;
import com.gbrf.adapter.ConnectionDetector;
import com.gbrf.dto.UserDTO;

@SuppressLint("NewApi")
public class LoginActivity extends ActionBarActivity implements
		LoaderCallbacks<Cursor> {

	private UserLoginTask mLoginTask = null;
	private UserFacebookLoginTask mFacebookLoginTask = null;
	
	private AutoCompleteTextView mEmailView;
	private AutoCompleteTextView mPasswordView;
	private View mProgressView;
	AlertDialogManager alert = new AlertDialogManager();
	//private static String APP_ID = "888751001205538";
	//private Facebook facebook = new Facebook(APP_ID);
	//private AsyncFacebookRunner mAsyncRunner;
	String FILENAME = "AndroidSSO_data";
	private SharedPreferences mPrefs;
	Button btnFbLogin;
	String name, password;
	String email;
	String sId;
	String currentLocation, currentcuntry;
	double latitude = 0.0;
	double longitude = 0.0;
	DataSyncService service;
	ConnectionDetector cd;
	SharedPreferences SM;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ActionBar actionBar = getSupportActionBar();
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		actionBar.setTitle("GBRF");
		actionBar.setDisplayUseLogoEnabled(true);
		getSupportActionBar().setIcon(R.drawable.logo);
		SM = getSharedPreferences("userrecord", 0);
        Boolean islogin = SM.getBoolean("userlogin", false);
        if(islogin){
           Intent intent = new Intent(LoginActivity.this, MainActivity.class);
           startActivity(intent);
           finish();
           return;
        }
		TextView signupbtn = (TextView) findViewById(R.id.SignUp);
		btnFbLogin = (Button) findViewById(R.id.btn_fblogin);

		//mAsyncRunner = new AsyncFacebookRunner(facebook);
		signupbtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this,
						SignupActivity.class);
				startActivity(intent);
			}
		});
		btnFbLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				cd = new ConnectionDetector(getApplicationContext());
				// Check for internet connection
				if (!cd.isConnectingToInternet()) {
					// Internet Connection is not present
					alert.showAlertDialog(LoginActivity.this,
							"Internet Connection Error",
							"Please connect to working Internet connection",
							false);
					// stop executing code by return
					return;
				}
				loginToFacebook();
			}
		});

		TextView forgotpwd = (TextView) findViewById(R.id.forgotpassword);
		forgotpwd.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this,
						ForgotPasswordActivity.class);
				startActivity(intent);
			}
		});
		mEmailView = (AutoCompleteTextView) findViewById(R.id.EnterEmail);
		mPasswordView = (AutoCompleteTextView) findViewById(R.id.password);
		RelativeLayout touchInterceptor = (RelativeLayout) findViewById(R.id.login);
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
					if (mPasswordView.isFocused()) {
						Rect outRect = new Rect();
						mPasswordView.getGlobalVisibleRect(outRect);
						if (!outRect.contains((int) event.getRawX(),
								(int) event.getRawY())) {
							mPasswordView.clearFocus();
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

		//loadSavedPreferences();
		//populateAutoComplete();
		Button mEmailLoginInButton = (Button) findViewById(R.id.Login);
		mEmailLoginInButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				cd = new ConnectionDetector(getApplicationContext());
				// Check for internet connection
				if (!cd.isConnectingToInternet()) {
					// Internet Connection is not present
					alert.showAlertDialog(LoginActivity.this,
							"Internet Connection Error",
							"Please connect to working Internet connection",
							false);
					// stop executing code by return
					return;
				}
				attemptLogin();
			}
		});
		mProgressView = findViewById(R.id.login_progress);
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

	/**
	 * Function to login into facebook
	 * */
	
	private void loginToFacebook()
	{
		 Log.d("FACEBOOK", "performFacebookLogin");
		    final Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(this, Arrays.asList("email"));
        // start Facebook Login
        Session.openActiveSession(this, true, new Session.StatusCallback() {

          // callback when session changes state
          @Override
          public void call(Session session, SessionState state, Exception exception) {
        	  Log.d("FACEBOOK", "call");
        	  if (session.isOpened()) {
        		  Log.d("FACEBOOK", "if (session.isOpened() && !isFetching)");
            	session.requestNewReadPermissions(newPermissionsRequest);
              Request.newMeRequest(session, new Request.GraphUserCallback() {

                // callback after Graph API response with user object
                @Override
                public void onCompleted(GraphUser user, Response response) {
                  if (user != null) {
                	  Log.d("FACEBOOK", "onCompleted");
                	  try{
                      org.json.JSONObject graphResponse = response.getGraphObject().getInnerJSONObject();         	
  					name = graphResponse.getString("name");
					// getting email of the user
					email = graphResponse.getString("email");
					password = graphResponse.getString("name");
					sId = graphResponse.getString("id");
					currentLocation = graphResponse.getJSONObject("location")
							.getString("name");
					String[] token1 = currentLocation.split(",");
					String currentcity = token1[0];
					 currentcuntry = token1[1];
					System.out.println(sId + " " + email+" "+ name +" "+ currentcuntry);
					if (mFacebookLoginTask != null) {
						return;
					}
					showProgress(true);
					mFacebookLoginTask = new UserFacebookLoginTask(name, email,sId, latitude, longitude, currentcuntry);
					mFacebookLoginTask.execute((Void) null);

                	  } catch (JSONException e) {
      					e.printStackTrace();
      				}

                  }
                }
              }).executeAsync();
            } else
            {
                if (!session.isOpened())
                    Log.d("FACEBOOK", "!session.isOpened()");
                else
                    Log.d("FACEBOOK", "isFetching");

            }
          }
        });
      }
	   @Override
	      public void onActivityResult(int requestCode, int resultCode, Intent data) {
	          super.onActivityResult(requestCode, resultCode, data);
	          Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	      }
/*	public void loginToFacebook() {

		mPrefs = getPreferences(MODE_PRIVATE);
		String access_token = mPrefs.getString("access_token", null);
		System.out.println("att" + access_token);
		long expires = mPrefs.getLong("access_expires", 0);

		if (access_token != null) {
			facebook.setAccessToken(access_token);
			String mUserToken = facebook.getAccessToken();
			System.out.println("mUserToken" + mUserToken);
			//btnFbLogin.setVisibility(View.INVISIBLE);
			//getProfileInformation();
			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(intent);
              finish();
		}

		if (expires != 0) {
			facebook.setAccessExpires(expires);
		}

		if (!facebook.isSessionValid()) {
			facebook.authorize(this, new String[] { "email", "user_location" },

			new DialogListener() {

				@Override
				public void onCancel() {
					// Function to handle cancel event
				}

				@Override
				public void onComplete(Bundle values) {
					// Function to handle complete event
					// Edit Preferences and update facebook acess_token
					SharedPreferences.Editor editor = mPrefs.edit();
					editor.putString("access_token", facebook.getAccessToken());
					editor.putLong("access_expires",
							facebook.getAccessExpires());
					editor.commit();
					getProfileInformation();
					Intent intent = new Intent(LoginActivity.this,
							MainActivity.class);
					startActivity(intent);
					finish();

				}

				@Override
				public void onError(DialogError error) {
					// Function to handle error

				}

				@Override
				public void onFacebookError(FacebookError fberror) {
					// Function to handle Facebook errors

				}

			});
		}
	}*/

/*	public void getProfileInformation() {
		mAsyncRunner.request("me", new RequestListener() {
			@Override
			public void onComplete(String response, Object state) {
				// Log.d("Profile", response);
				System.out.println("Profile" + response);
				String json = response;
				try {
					JSONObject profile = new JSONObject(json);
					// getting name of the user
					name = profile.getString("name");
					// getting email of the user
					email = profile.getString("email");
					password = profile.getString("name");
					sId = profile.getString("id");
					currentLocation = profile.getJSONObject("location")
							.getString("name");
					String[] token1 = currentLocation.split(",");
					String currentcity = token1[0];
					String currentcuntry = token1[1];
					service = new DataSyncService();
					UserDTO dto = service.Facebookregistration(name, email,sId, latitude, longitude, currentcuntry);
					((PE) getApplicationContext()).setUserDTO(dto);
					if (dto.getStatus().equals("success")) {
						savePreferences("Login", true);
						savePreferences("UID", ((PE) getApplicationContext())
								.getUserDTO().getuId());
						savePreferences("Name", ((PE) getApplicationContext())
								.getUserDTO().getFirstName());
						savePreferences("Email", ((PE) getApplicationContext())
								.getUserDTO().getEmail());
						savePreferences("sid", ((PE) getApplicationContext())
								.getUserDTO().getSid());
						savePreferences("ssid", ((PE) getApplicationContext())
								.getUserDTO().getSsid());
						savePreferences("account",
								((PE) getApplicationContext()).getUserDTO()
										.getAccount());
					
					}
					runOnUiThread(new Runnable() {

						@Override
						public void run() {

						}

					});

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onIOException(IOException e, Object state) {
			}

			@Override
			public void onFileNotFoundException(FileNotFoundException e,
					Object state) {
			}

			@Override
			public void onMalformedURLException(MalformedURLException e,
					Object state) {
			}

			@Override
			public void onFacebookError(FacebookError e, Object state) {
			}
		});
	}*/

	/*@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		facebook.authorizeCallback(requestCode, resultCode, data);
	}*/

	/**
	 * Function to Logout user from Facebook
	 * */
/*	public void logoutFromFacebook() {

		mAsyncRunner.logout(this, new RequestListener() {
			@Override
			public void onComplete(String response, Object state) {
				if (Boolean.parseBoolean(response) == true) {
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// make Login button visible
							btnFbLogin.setVisibility(View.VISIBLE);

						}

					});

				}
			}

			@Override
			public void onIOException(IOException e, Object state) {
			}

			@Override
			public void onFileNotFoundException(FileNotFoundException e,
					Object state) {
			}

			@Override
			public void onMalformedURLException(MalformedURLException e,
					Object state) {
			}

			@Override
			public void onFacebookError(FacebookError e, Object state) {
			}
		});
	}*/

	public void attemptLogin() {
		if (mLoginTask != null) {
			return;
		}
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		// Builder alert = new AlertDialog.Builder(LoginActivity.this,
		// R.style.AlertDialogCustom);

		String email = mEmailView.getText().toString();
		String password = mPasswordView.getText().toString();

		// Check for a valid email address.
		if (TextUtils.isEmpty(email)) {
			// mEmailView.setError(getString(R.string.error_invalid_email));
			alert.setMessage("Please enter valid email");
			alert.setPositiveButton("OK", null);
			alert.show();
		}
		/*
		 * if (!(email.matches(emailPattern))) {
		 * mEmailView.setError(getString(R.string.error_invalid_email));
		 * focusView = mEmailView; cancel = true; }
		 */
		else if (TextUtils.isEmpty(password)) {
			alert.setMessage("Please enter password");
			alert.setPositiveButton("OK", null);
			alert.show();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			showProgress(true);
			mLoginTask = new UserLoginTask(email, password);
			mLoginTask.execute((Void) null);
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
				LoginActivity.this,
				android.R.layout.simple_dropdown_item_1line,
				emailAddressCollection);

		mEmailView.setAdapter(adapter);
	}

	private void loadSavedPreferences() {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		boolean cbValue = sp.getBoolean("Login", false);
		if (cbValue) {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			finish();
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
	
	
	public class UserFacebookLoginTask extends AsyncTask<Void, Void, Boolean> {

		private final String mName;
		private final String mEmail;
		private final String msId;
		private final double mlatitude;
		private final double mlongitude;
		private final String mcurrentcuntry;

		UserFacebookLoginTask(String name, String email,String sId,double latitude,double longitute,String currentcuntry) {

			mName = name;
			mEmail = email;
			msId=sId;
			mlatitude=latitude;
			mlongitude=longitute;
			mcurrentcuntry=currentcuntry;

		}

		@Override
		protected Boolean doInBackground(Void... params) {

			DataSyncService service = new DataSyncService();
			try {
				UserDTO dto = service.Facebookregistration(mName, mEmail,msId, mlatitude, mlongitude, mcurrentcuntry);
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
			mLoginTask = null;

			System.out.println(success);

			if (success) {
				savePreferences("UID", ((PE) getApplicationContext())
						.getUserDTO().getuId());
				savePreferences("Name", ((PE) getApplicationContext())
						.getUserDTO().getFirstName());
				savePreferences("Email", ((PE) getApplicationContext())
						.getUserDTO().getEmail());
				savePreferences("sid", ((PE) getApplicationContext())
						.getUserDTO().getSid());
				savePreferences("ssid", ((PE) getApplicationContext())
						.getUserDTO().getSsid());
				savePreferences("account",
						((PE) getApplicationContext()).getUserDTO()
								.getAccount());
          	Intent homeIntent = new Intent(getApplicationContext(),
					MainActivity.class);
			startActivity(homeIntent);
			finish();

			} else {
				AlertDialog.Builder alert = new AlertDialog.Builder(
						LoginActivity.this);
				alert.setMessage(((PE) getApplicationContext()).getUserDTO()
						.getMessage());
				alert.setPositiveButton("OK", null);
				alert.show();

			}

			showProgress(false);
		}

		@Override
		protected void onCancelled() {
			mLoginTask = null;
			showProgress(false);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

		private final String mEmail;
		private final String mPassword;

		UserLoginTask(String email, String password) {

			mEmail = email;
			mPassword = password;

		}

		@Override
		protected Boolean doInBackground(Void... params) {

			DataSyncService service = new DataSyncService();
			try {
				UserDTO dto = service.login(mEmail, mPassword);
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
			mLoginTask = null;

			System.out.println(success);

			if (success) {
				savePreferences("UID", ((PE) getApplicationContext())
						.getUserDTO().getuId());
				System.out.println("uid" + ((PE) getApplicationContext())
						.getUserDTO().getuId());
				savePreferences("Name", ((PE) getApplicationContext())
						.getUserDTO().getFirstName());
				savePreferences("Email", ((PE) getApplicationContext())
						.getUserDTO().getEmail());
				savePreferences("sid", ((PE) getApplicationContext())
						.getUserDTO().getSid());
				savePreferences("ssid", ((PE) getApplicationContext())
						.getUserDTO().getSsid());
				savePreferences("account", ((PE) getApplicationContext())
						.getUserDTO().getAccount());
				savePreferences("CurrencyCode", ((PE) getApplicationContext())
						.getUserDTO().getCurrencycode());
				savePreferences("CountryName", ((PE) getApplicationContext())
						.getUserDTO().getCountry());
				/*savePreferences("CurrencySymbol", ((PE) getApplicationContext())
						.getUserDTO().getCurrencysymbol());*/
				Editor edit = SM.edit();
                edit.putBoolean("userlogin", true);
                edit.commit();
				Intent homeIntent = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(homeIntent);

				finish();

			} else {
				AlertDialog.Builder alert = new AlertDialog.Builder(
						LoginActivity.this);
				alert.setMessage(((PE) getApplicationContext()).getUserDTO()
						.getMessage());
				alert.setPositiveButton("OK", null);
				alert.show();

			}

			showProgress(false);
		}

		@Override
		protected void onCancelled() {
			mLoginTask = null;
			showProgress(false);
		}
	}
}
