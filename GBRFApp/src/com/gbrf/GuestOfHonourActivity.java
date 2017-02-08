package com.gbrf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Session;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.gbrf.adapter.FacebookConnector;
import com.gbrf.dto.UserDTO;
import com.lib.VerticalMarqueeTextView;

@SuppressLint("NewApi")
public class GuestOfHonourActivity extends ActionBarActivity implements
LoaderCallbacks<Cursor> {

	private UserInvitationTask mInvitationTask = null;
	private UserGlobalTask mGlobalTask = null;
	private String mnId;
	private String mcountry;
	private String bookName;
	private String authorName;
	private String price;
	private String image;
	private String publisher;
	private String isbn;
	private String pages;
	private String format;
	private String name,countryName,releasedate;
	private String description,purchased,oid;
	private View mProgressView;
	private View mguestFormView;
	Button imageViewCapture;
	Bitmap bitmap;
	private Animation animation;
	private String muId,msid,mssid,account;
	private static final String FACEBOOK_APPID = "888751001205538";
	private static final String FACEBOOK_PERMISSION = "publish_actions";
	private static final String TAG = "FacebookSample";
	private static final String MSG = "Message from FacebookSample";
	private final Handler mFacebookHandler = new Handler();
	private FacebookConnector facebookConnector;
	 private UserLogoutTask mLogoutTask = null;
	 private UiLifecycleHelper uiHelper;
	
    final Runnable mUpdateFacebookNotification = new Runnable() {
        public void run() {
        	AlertDialog.Builder alert = new AlertDialog.Builder(
        			GuestOfHonourActivity.this);
			alert.setMessage("Thank you");
			alert.setPositiveButton("OK", null);
			alert.show();
        	//Toast.makeText(getBaseContext(), "Facebook updated !", Toast.LENGTH_LONG).show();
        }
    };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guest_of_honour);
		ActionBar actionBar = getSupportActionBar();
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		actionBar.setTitle("Guests of Honour");
		actionBar.setDisplayUseLogoEnabled(true);
		getSupportActionBar().setIcon(R.drawable.logo);
		  // this.facebookConnector = new FacebookConnector(FACEBOOK_APPID, this, getApplicationContext(), new String[] {FACEBOOK_PERMISSION});
		uiHelper = new UiLifecycleHelper(this, null);
        uiHelper.onCreate(savedInstanceState);
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		Intent in = getIntent();
		mnId = in.getStringExtra("nId");
/*		//name = in.getStringExtra("name");
		//countryName = in.getStringExtra("CountryName");
		//releasedate = in.getStringExtra("released_on");
		System.out.println("Gro"+releasedate);
		System.out.println("name"+name);
		System.out.println("countryName"+countryName);*/
		
		mcountry = preferences.getString("Country", "Test");
		 muId = preferences.getString("UID", "Test");
		 System.out.println("muId"+muId);
		bookName = preferences.getString("bookname", "Test");
		authorName = preferences.getString("authorname", "Test");
		price = preferences.getString("price", "Test");
		image = preferences.getString("image", "Test");
		publisher = preferences.getString("publisher", "Test");
		isbn = preferences.getString("isbn", "Test");
		pages = preferences.getString("pages", "Test");
		format = preferences.getString("format", "Test");
		description = preferences.getString("description", "Test");
		account = preferences.getString("account", "Test");
		purchased = preferences.getString("purchased", "Test");
		oid = preferences.getString("oid", "Test");
		msid = preferences.getString("sid", "Test");
		mssid = preferences.getString("ssid", "Test");
		ArrayList mylist = new ArrayList();
		PE pe = (PE) getApplicationContext();
		mylist = (ArrayList) pe.getList();
		StringBuilder sb = new StringBuilder();
int i=1;
		String name = "";
		String releaseon = "";
		String country = "";
		Iterator it = mylist.iterator();

		while (it.hasNext()) {
			Map<String, String> map = (Map) it.next();

			name = map.get("name");
			releaseon = map.get("releaseon");
			country = map.get("country");
			sb.append(i+". "+name+"\n");
			sb.append(releaseon+"\n");
			sb.append(country+"\n");
			i++;
		}
		 name = sb.toString();
		System.out.print(name);
        VerticalMarqueeTextView textView =(VerticalMarqueeTextView) findViewById(R.id.text);
        textView.setEnableScrolling(false); 
        textView.setText(name);
        
/*		TextView txt1 = (TextView) findViewById(R.id.name);
		TextView txt2 = (TextView) findViewById(R.id.releaseDate);
		TextView txt3 = (TextView) findViewById(R.id.countryName);
		txt1.setText(name);
		txt2.setText(releasedate);
		 txt3.setText(countryName);*/


	     imageViewCapture = (Button) findViewById(R.id.FbshareImage);
	        imageViewCapture.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					postMessage();
				}
			});
	/*	Button btn = (Button) findViewById(R.id.buy);
		btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(
						GuestOfHonourActivity.this);
				alert.setMessage("Being released shortly");
				alert.setPositiveButton("OK", null);
				alert.show();
				
				Intent intent = new Intent(GuestOfHonourActivity.this, SingleItemView.class);
				// Pass all data rank
				intent.putExtra("bookname",bookName);
				// Pass all data country
				intent.putExtra("authername",authorName);
				// Pass all data population
				intent.putExtra("price",price);
				// Pass all data flag
				intent.putExtra("image",image);
				intent.putExtra("publisher",publisher);
				intent.putExtra("isbn",isbn);
				intent.putExtra("pages",pages);
				intent.putExtra("format",format);
				intent.putExtra("description", description);
				intent.putExtra("purchased", purchased);
				intent.putExtra("oid", oid);
				// Start SingleItemView Class
				startActivity(intent);
 
			}
			
		});*/
		//populateAutoComplete();
		TextView regionaltbtn = (TextView) findViewById(R.id.regional);
		regionaltbtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showProgress(true);
				mInvitationTask = new UserInvitationTask(mnId,mcountry);
				mInvitationTask.execute((Void) null);
			}
		});
		TextView globalbtn = (TextView) findViewById(R.id.global);
		globalbtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showProgress(true);
				mGlobalTask = new UserGlobalTask(mnId);
				mGlobalTask.execute((Void) null);
			}
		});
		mProgressView = findViewById(R.id.guest_progress);
		mguestFormView = findViewById(R.id.guest_form);

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
	   public Bitmap getBitmapOfView(View v)
	    {
	       	View rootview = v.getRootView();
	    	rootview.setDrawingCacheEnabled(true);
	    	Bitmap bmp = rootview.getDrawingCache();
	    	return bmp;
	    }
	    public void createImageFromBitmap(Bitmap bmp)
	    {
	        Date now = new Date();
	        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
	    	ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			bmp.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
			File file = new File( Environment.getExternalStorageDirectory() +  "/" + now + ".jpg");
			try 
			{
				file.createNewFile();
				FileOutputStream ostream = new FileOutputStream(file);
				ostream.write(bytes.toByteArray());        
				ostream.close();
				Toast.makeText(getApplicationContext(),
						"Thank you", Toast.LENGTH_LONG)
						.show();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
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
	@SuppressLint("NewApi") @Override
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
				GuestOfHonourActivity.this,
				android.R.layout.simple_dropdown_item_1line,
				emailAddressCollection);

	}
	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserInvitationTask extends AsyncTask<Void, Void, Boolean> {

		private final String mnid;
		private final String mcountry;

		UserInvitationTask(String nid,String country) {
			mnid = nid;
			mcountry=country;
		}

		@Override
		protected Boolean doInBackground(Void... params) {

			DataSyncService service = new DataSyncService();
			ArrayList mylist = new ArrayList();
			try {
				mylist = (ArrayList) service.regionalguest(mnid,mcountry);
				((PE) getApplicationContext()).setList(mylist);
					return true;
			} catch (Exception e) {

				e.printStackTrace();

			}

			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mInvitationTask = null;

			System.out.println(success);

			if (success) {
				Intent homeIntent = new Intent(GuestOfHonourActivity.this,
						GuestOfHonourActivity.class);
				homeIntent.putExtra("nId", mnId);
				startActivity(homeIntent);
				finish();

			}
			showProgress(false);
		}

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
				Intent homeIntent = new Intent(GuestOfHonourActivity.this,
						LoginActivity.class);
				startActivity(homeIntent);
				finish();

			} else {

				Intent homeIntent = new Intent(GuestOfHonourActivity.this,
						GuestOfHonourActivity.class);
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
	public class UserGlobalTask extends AsyncTask<Void, Void, Boolean> {

		private final String mnid;
		UserGlobalTask(String nid) {
			mnid = nid;
		}

		@Override
		protected Boolean doInBackground(Void... params) {

			DataSyncService service = new DataSyncService();
			ArrayList mylist = new ArrayList();
			try {
				mylist = (ArrayList) service.guest(mnid);
				((PE) getApplicationContext()).setList(mylist);
					return true;
			} catch (Exception e) {

				e.printStackTrace();

			}

			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mGlobalTask = null;

			System.out.println(success);

			if (success) {
				Intent homeIntent = new Intent(GuestOfHonourActivity.this,
						GuestOfHonourActivity.class);
				homeIntent.putExtra("nId", mnId);
				startActivity(homeIntent);
				finish();

			}
			showProgress(false);
		}

	}

	protected void onCancelled() {
		mInvitationTask = null;
		mGlobalTask = null;
		showProgress(false);

	}
	private void savePreferences(String key, boolean value) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor edit = sp.edit();
		edit.putBoolean(key, value);
		edit.commit();
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
	private String getFacebookMsg() {
		return MSG + " at " + new Date().toLocaleString();
	}	
	public void postMessage() {
		
	
		 FacebookDialog.ShareDialogBuilder builder = new FacebookDialog.ShareDialogBuilder(
	                this)
	                .setLink("http://theacademicworld.com/")
	                .setName("Globle Book Release Forum")
	                .setPicture("http://www.theacademicworld.com/invitation/farmaan?uid="+muId);
	 
	        // If the Facebook Messenger app is installed and we can present the share dialog
	        if (builder.canPresent()) {
	            // Enable button or other UI to initiate launch of the Message Dialog
	            FacebookDialog dialog = builder.build();
	            uiHelper.trackPendingDialogCall(dialog.present());
	        } else {
	            // Disable button or other UI for Message Dialog
	            Toast.makeText(getApplicationContext(), "Facebook  app is not installed in your device", Toast.LENGTH_SHORT).show();
	            //v.setEnabled(false);
	        }
	}
	 // After Facebook Messenger dialog is closed
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
 
        uiHelper.onActivityResult(requestCode, resultCode, data,
                new FacebookDialog.Callback() {
 
                    @Override
                    public void onError(FacebookDialog.PendingCall pendingCall,
                            Exception error, Bundle data) {
                        Toast.makeText(
                                getApplicationContext(),
                                "Error OccurednMost Common Errors:n1. Device not connected to Internetn2.Faceboook APP Id is not changed in Strings.xml",
                                Toast.LENGTH_LONG).show();
                    }
 
                    @Override
                    public void onComplete(
                            FacebookDialog.PendingCall pendingCall, Bundle data) {
                        Toast.makeText(getApplicationContext(), "Done!!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
 
    @Override
    protected void onResume() {
        super.onResume();
        uiHelper.onResume();
    }
 
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }
 
    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }
 
    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }
	private void postMessageInThread() {
		Thread t = new Thread() {
			public void run() {
				System.out.println("in postMessageInThread");
		    	try {
		    		facebookConnector.postMessageOnWall(getFacebookMsg(),muId);
					mFacebookHandler.post(mUpdateFacebookNotification);
				} catch (Exception ex) {
					Log.e(TAG, "Error sending msg",ex);
				}
		    }
		};
		t.start();
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
