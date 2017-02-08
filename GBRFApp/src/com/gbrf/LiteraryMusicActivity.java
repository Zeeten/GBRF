package com.gbrf;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
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
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.Session;
import com.gbrf.adapter.AlertDialogManager;
import com.gbrf.adapter.ConnectionDetector;
import com.gbrf.dto.UserDTO;

public class LiteraryMusicActivity extends ActionBarActivity implements
LoaderCallbacks<Cursor> {

	// Button to download and play Music
	private Button downloadsong1;
	private Button playsong1;
	private Button downloadsong2;
	private Button playsong2;
	private Button downloadsong3;
	private Button playsong3;
	private Button downloadsong4;
	private Button playsong4;
	private Button downloadsong5;
	private Button playsong5;
	private Button downloadsong6;
	private Button playsong6;
	// Media Player Object
	private MediaPlayer mPlayer;
	// Progress Dialog Object
	private ProgressDialog prgDialog;
	// Connection detector
	ConnectionDetector cd;
	private String muId,msid,mssid,account;
	private UserLogoutTask mLogoutTask = null;
	private View mProgressView;

	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	// Progress Dialog type (0 - for Horizontal progress bar)
	public static final int progress_bar_type = 0;
	// Music resource URL
	private static String song1_file_url = "http://theacademicworld.com/sites/academicworld/uploads/videos/01-Chirag-E-rah.mp4";
	private static String song2_file_url = "http://theacademicworld.com/sites/academicworld/uploads/videos/02-Woh-Kahaan-Hai.mp4";
	private static String song3_file_url = "http://theacademicworld.com/sites/academicworld/uploads/videos/03-Mere-Liye-Yeh-Subah-Kya.mp4";
	private static String song4_file_url = "http://theacademicworld.com/sites/academicworld/uploads/videos/04-Jab-Suna-Ansuna-Karey-Koi.mp4";
	private static String song5_file_url = "http://theacademicworld.com/sites/academicworld/uploads/videos/05-Kaash-Woh-Jhaankta.mp4";
	private static String song6_file_url = "http://theacademicworld.com/sites/academicworld/uploads/videos/06-Hai-Tareekh-Go-Inki.mp4";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_literary_music);
		ActionBar actionBar = getSupportActionBar();
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		actionBar.setTitle("Literary Music");
		actionBar.setDisplayUseLogoEnabled(true);
		getSupportActionBar().setIcon(R.drawable.logo);
		downloadsong1 = (Button) findViewById(R.id.downloadsong1);
		playsong1 = (Button) findViewById(R.id.playsong1);
		downloadsong2 = (Button) findViewById(R.id.downloadsong2);
		playsong2 = (Button) findViewById(R.id.playsong2);
		downloadsong3 = (Button) findViewById(R.id.downloadsong3);
		playsong3 = (Button) findViewById(R.id.playsong3);
		downloadsong4 = (Button) findViewById(R.id.downloadsong4);
		playsong4 = (Button) findViewById(R.id.playsong4);
		downloadsong5 = (Button) findViewById(R.id.downloadsong5);
		playsong5 = (Button) findViewById(R.id.playsong5);
		downloadsong6 = (Button) findViewById(R.id.downloadsong6);
		playsong6 = (Button) findViewById(R.id.playsong6);
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		account = preferences.getString("account", "Test");
		muId = preferences.getString("UID", "Test");
		msid = preferences.getString("sid", "Test");
		mssid=preferences.getString("ssid", "Test");
		File song1_file = new File(Environment.getExternalStorageDirectory()
				.getPath() + "/01-Chirag-E-rah.mp4");
		File song2_file = new File(Environment.getExternalStorageDirectory()
				.getPath() + "/02-Woh-Kahaan-Hai.mp4");
		File song3_file = new File(Environment.getExternalStorageDirectory()
				.getPath() + "/03-Mere-Liye-Yeh-Subah-Kya.mp4");
		File song4_file = new File(Environment.getExternalStorageDirectory()
				.getPath() + "/04-Jab-Suna-Ansuna-Karey-Koi.mp4");
		File song5_file = new File(Environment.getExternalStorageDirectory()
				.getPath() + "/05-Kaash-Woh-Jhaankta.mp4");
		File song6_file = new File(Environment.getExternalStorageDirectory()
				.getPath() + "/06-Hai-Tareekh-Go-Inki.mp4");
		File allsong_file = new File(Environment.getExternalStorageDirectory()
				.getPath() + "/Yadoon_Ki_Chinar.zip");
		// Check if the Music file already exists
		if (song1_file.exists()) {
			downloadsong1.setVisibility(View.GONE);
			playsong1.setVisibility(View.VISIBLE);
		} else {
			downloadsong1.setVisibility(View.VISIBLE);
			playsong1.setVisibility(View.GONE);
		}
		if (song2_file.exists()) {
			downloadsong2.setVisibility(View.GONE);
			playsong2.setVisibility(View.VISIBLE);
		} else {
			downloadsong2.setVisibility(View.VISIBLE);
			playsong2.setVisibility(View.GONE);
		}
		if (song3_file.exists()) {
			downloadsong3.setVisibility(View.GONE);
			playsong3.setVisibility(View.VISIBLE);
		} else {
			downloadsong3.setVisibility(View.VISIBLE);
			playsong3.setVisibility(View.GONE);
		}
		if (song4_file.exists()) {
			downloadsong4.setVisibility(View.GONE);
			playsong4.setVisibility(View.VISIBLE);
		} else {
			downloadsong4.setVisibility(View.VISIBLE);
			playsong4.setVisibility(View.GONE);
		}
		if (song5_file.exists()) {
			downloadsong5.setVisibility(View.GONE);
			playsong5.setVisibility(View.VISIBLE);
		} else {
			downloadsong5.setVisibility(View.VISIBLE);
			playsong5.setVisibility(View.GONE);
		}

		if (song6_file.exists()) {
			downloadsong6.setVisibility(View.GONE);
			playsong6.setVisibility(View.VISIBLE);
		} else {
			downloadsong6.setVisibility(View.VISIBLE);
			playsong6.setVisibility(View.GONE);
		}

		// Download Music Button click listener
		downloadsong1.setOnClickListener(new View.OnClickListener() {
			// When Download Music Button is clicked
			public void onClick(View v) {
				cd = new ConnectionDetector(getApplicationContext());
				// Check for internet connection
				if (!cd.isConnectingToInternet()) {
					// Internet Connection is not present
					alert.showAlertDialog(LiteraryMusicActivity.this,
							"Internet Connection Error",
							"Please connect to working Internet connection",
							false);
					// stop executing code by return
					return;
				}
				new DownloadMusicfromInternet().execute(song1_file_url);

			}
		});
		// Download Music Button click listener
		downloadsong2.setOnClickListener(new View.OnClickListener() {
			// When Download Music Button is clicked
			public void onClick(View v) {
				cd = new ConnectionDetector(getApplicationContext());
				// Check for internet connection
				if (!cd.isConnectingToInternet()) {
					// Internet Connection is not present
					alert.showAlertDialog(LiteraryMusicActivity.this,
							"Internet Connection Error",
							"Please connect to working Internet connection",
							false);
					// stop executing code by return
					return;
				}
				new Downloadson2().execute(song2_file_url);

			}
		});
		// Download Music Button click listener
		downloadsong3.setOnClickListener(new View.OnClickListener() {
			// When Download Music Button is clicked
			public void onClick(View v) {
				cd = new ConnectionDetector(getApplicationContext());
				// Check for internet connection
				if (!cd.isConnectingToInternet()) {
					// Internet Connection is not present
					alert.showAlertDialog(LiteraryMusicActivity.this,
							"Internet Connection Error",
							"Please connect to working Internet connection",
							false);
					// stop executing code by return
					return;
				}
				new Downloadson3().execute(song3_file_url);

			}
		});
		// Download Music Button click listener
		downloadsong4.setOnClickListener(new View.OnClickListener() {
			// When Download Music Button is clicked
			public void onClick(View v) {
				cd = new ConnectionDetector(getApplicationContext());
				// Check for internet connection
				if (!cd.isConnectingToInternet()) {
					// Internet Connection is not present
					alert.showAlertDialog(LiteraryMusicActivity.this,
							"Internet Connection Error",
							"Please connect to working Internet connection",
							false);
					// stop executing code by return
					return;
				}
				new Downloadson4().execute(song4_file_url);

			}
		});
		// Download Music Button click listener
		downloadsong5.setOnClickListener(new View.OnClickListener() {
			// When Download Music Button is clicked
			public void onClick(View v) {
				cd = new ConnectionDetector(getApplicationContext());
				// Check for internet connection
				if (!cd.isConnectingToInternet()) {
					// Internet Connection is not present
					alert.showAlertDialog(LiteraryMusicActivity.this,
							"Internet Connection Error",
							"Please connect to working Internet connection",
							false);
					// stop executing code by return
					return;
				}
				new Downloadson5().execute(song5_file_url);

			}
		});
		// Download Music Button click listener
		downloadsong6.setOnClickListener(new View.OnClickListener() {
			// When Download Music Button is clicked
			public void onClick(View v) {
				cd = new ConnectionDetector(getApplicationContext());
				// Check for internet connection
				if (!cd.isConnectingToInternet()) {
					// Internet Connection is not present
					alert.showAlertDialog(LiteraryMusicActivity.this,
							"Internet Connection Error",
							"Please connect to working Internet connection",
							false);
					// stop executing code by return
					return;
				}
				new Downloadson6().execute(song6_file_url);

			}
		});

		playsong1.setOnClickListener(new View.OnClickListener() {
			// When Download Music Button is clicked
			public void onClick(View v) {
				String title = "Chirag-E-rah";
				String url = "file:///sdcard/01-Chirag-E-rah.mp4";
				DataSyncService service = new DataSyncService();
				UserDTO dto = service.music(title, url);
				((PE) getApplicationContext()).setUserDTO(dto);
				Intent intent = new Intent(LiteraryMusicActivity.this,
						VideoViewActivity.class);
				startActivity(intent);

			}
		});
		playsong2.setOnClickListener(new View.OnClickListener() {
			// When Download Music Button is clicked
			public void onClick(View v) {
				String title = "Woh-Kahaan-Hai";
				String url = "file:///sdcard/02-Woh-Kahaan-Hai.mp4";
				DataSyncService service = new DataSyncService();
				UserDTO dto = service.music(title, url);
				((PE) getApplicationContext()).setUserDTO(dto);
				Intent intent = new Intent(LiteraryMusicActivity.this,
						VideoViewActivity.class);
				startActivity(intent);

			}
		});
		playsong3.setOnClickListener(new View.OnClickListener() {
			// When Download Music Button is clicked
			public void onClick(View v) {
				String title = "Mere-Liye-Yeh-Subah-Kya";
				String url = "file:///sdcard/03-Mere-Liye-Yeh-Subah-Kya.mp4";
				DataSyncService service = new DataSyncService();
				UserDTO dto = service.music(title, url);
				((PE) getApplicationContext()).setUserDTO(dto);
				Intent intent = new Intent(LiteraryMusicActivity.this,
						VideoViewActivity.class);
				startActivity(intent);

			}
		});
		playsong4.setOnClickListener(new View.OnClickListener() {
			// When Download Music Button is clicked
			public void onClick(View v) {
				String title = "Jab-Suna-Ansuna-Karey-Koi";
				String url = "file:///sdcard/04-Jab-Suna-Ansuna-Karey-Koi.mp4";
				DataSyncService service = new DataSyncService();
				UserDTO dto = service.music(title, url);
				((PE) getApplicationContext()).setUserDTO(dto);
				Intent intent = new Intent(LiteraryMusicActivity.this,
						VideoViewActivity.class);
				startActivity(intent);

			}
		});
		playsong5.setOnClickListener(new View.OnClickListener() {
			// When Download Music Button is clicked
			public void onClick(View v) {
				String title = "Kaash-Woh-Jhaankta";
				String url = "file:///sdcard/05-Kaash-Woh-Jhaankta.mp4";
				DataSyncService service = new DataSyncService();
				UserDTO dto = service.music(title, url);
				((PE) getApplicationContext()).setUserDTO(dto);
				Intent intent = new Intent(LiteraryMusicActivity.this,
						VideoViewActivity.class);
				startActivity(intent);

			}
		});
		playsong6.setOnClickListener(new View.OnClickListener() {
			// When Download Music Button is clicked
			public void onClick(View v) {
				String title = "Hai-Tareekh-Go-Inki";
				String url = "file:///sdcard/06-Hai-Tareekh-Go-Inki.mp4";
				DataSyncService service = new DataSyncService();
				UserDTO dto = service.music(title, url);
				((PE) getApplicationContext()).setUserDTO(dto);
				Intent intent = new Intent(LiteraryMusicActivity.this,
						VideoViewActivity.class);
				startActivity(intent);

			}
		});
		mProgressView = findViewById(R.id.logout_progress);
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
				LiteraryMusicActivity.this, android.R.layout.simple_dropdown_item_1line,
				emailAddressCollection);

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
				Intent homeIntent = new Intent(LiteraryMusicActivity.this,
						LoginActivity.class);
				startActivity(homeIntent);
				finish();

			} else {
			
				Intent homeIntent = new Intent(LiteraryMusicActivity.this,
						LiteraryMusicActivity.class);
				startActivity(homeIntent);

			}
			showProgress(false);

		}
		protected void onCancelled() {
			mLogoutTask = null;
			showProgress(false);

		}
	}

	// Show Dialog Box with Progress bar
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case progress_bar_type:
			prgDialog = new ProgressDialog(this);
			prgDialog.setMessage("Downloading MP4 file. Please wait...");
			prgDialog.setIndeterminate(false);
			prgDialog.setMax(100);
			prgDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			prgDialog.setCancelable(false);
			prgDialog.show();
			return prgDialog;
		default:
			return null;
		}
	}

	// Async Task Class
	class DownloadMusicfromInternet extends AsyncTask<String, String, String> {

		// Show Progress bar before downloading Music
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Shows Progress Bar Dialog and then call doInBackground method
			showDialog(progress_bar_type);
		}

		// Download Music File from Internet
		@Override
		protected String doInBackground(String... f_url) {
			int count;
			try {
				URL url = new URL(f_url[0]);
				URLConnection conection = url.openConnection();
				conection.connect();
				// Get Music file length
				int lenghtOfFile = conection.getContentLength();
				// input stream to read file - with 8k buffer
				InputStream input = new BufferedInputStream(url.openStream(),
						10 * 1024);
				// Output stream to write file in SD card
				OutputStream output = new FileOutputStream(Environment
						.getExternalStorageDirectory().getPath()
						+ "/01-Chirag-E-rah.mp4");
				byte data[] = new byte[1024];
				long total = 0;
				while ((count = input.read(data)) != -1) {
					total += count;
					// Publish the progress which triggers onProgressUpdate
					// method
					publishProgress("" + (int) ((total * 100) / lenghtOfFile));

					// Write data to file
					output.write(data, 0, count);
				}
				// Flush output
				output.flush();
				// Close streams
				output.close();
				input.close();
			} catch (Exception e) {
				Log.e("Error: ", e.getMessage());
			}
			return null;
		}

		// While Downloading Music File
		protected void onProgressUpdate(String... progress) {
			// Set progress percentage
			prgDialog.setProgress(Integer.parseInt(progress[0]));
		}

		// Once Music File is downloaded
		@Override
		protected void onPostExecute(String file_url) {
			// Dismiss the dialog after the Music file was downloaded
			dismissDialog(progress_bar_type);
			Toast.makeText(getApplicationContext(),
					"Download complete, playing Music", Toast.LENGTH_LONG)
					.show();
			downloadsong1.setVisibility(View.GONE);
			playsong1.setVisibility(View.VISIBLE);
		}
	}

	// Async Task Class
	class Downloadson2 extends AsyncTask<String, String, String> {

		// Show Progress bar before downloading Music
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Shows Progress Bar Dialog and then call doInBackground method
			showDialog(progress_bar_type);
		}

		// Download Music File from Internet
		@Override
		protected String doInBackground(String... f_url) {
			int count;
			try {
				URL url = new URL(f_url[0]);
				URLConnection conection = url.openConnection();
				conection.connect();
				// Get Music file length
				int lenghtOfFile = conection.getContentLength();
				// input stream to read file - with 8k buffer
				InputStream input = new BufferedInputStream(url.openStream(),
						10 * 1024);
				// Output stream to write file in SD card
				OutputStream output = new FileOutputStream(Environment
						.getExternalStorageDirectory().getPath()
						+ "/02-Woh-Kahaan-Hai.mp4");
				byte data[] = new byte[1024];
				long total = 0;
				while ((count = input.read(data)) != -1) {
					total += count;
					// Publish the progress which triggers onProgressUpdate
					// method
					publishProgress("" + (int) ((total * 100) / lenghtOfFile));

					// Write data to file
					output.write(data, 0, count);
				}
				// Flush output
				output.flush();
				// Close streams
				output.close();
				input.close();
			} catch (Exception e) {
				Log.e("Error: ", e.getMessage());
			}
			return null;
		}

		// While Downloading Music File
		protected void onProgressUpdate(String... progress) {
			// Set progress percentage
			prgDialog.setProgress(Integer.parseInt(progress[0]));
		}

		// Once Music File is downloaded
		@Override
		protected void onPostExecute(String file_url) {
			// Dismiss the dialog after the Music file was downloaded
			dismissDialog(progress_bar_type);
			Toast.makeText(getApplicationContext(),
					"Download complete, playing Music", Toast.LENGTH_LONG)
					.show();
			downloadsong2.setVisibility(View.GONE);
			playsong2.setVisibility(View.VISIBLE);
		}
	}

	// Async Task Class
	class Downloadson3 extends AsyncTask<String, String, String> {

		// Show Progress bar before downloading Music
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Shows Progress Bar Dialog and then call doInBackground method
			showDialog(progress_bar_type);
		}

		// Download Music File from Internet
		@Override
		protected String doInBackground(String... f_url) {
			int count;
			try {
				URL url = new URL(f_url[0]);
				URLConnection conection = url.openConnection();
				conection.connect();
				// Get Music file length
				int lenghtOfFile = conection.getContentLength();
				// input stream to read file - with 8k buffer
				InputStream input = new BufferedInputStream(url.openStream(),
						10 * 1024);
				// Output stream to write file in SD card
				OutputStream output = new FileOutputStream(Environment
						.getExternalStorageDirectory().getPath()
						+ "/03-Mere-Liye-Yeh-Subah-Kya.mp4");
				byte data[] = new byte[1024];
				long total = 0;
				while ((count = input.read(data)) != -1) {
					total += count;
					// Publish the progress which triggers onProgressUpdate
					// method
					publishProgress("" + (int) ((total * 100) / lenghtOfFile));

					// Write data to file
					output.write(data, 0, count);
				}
				// Flush output
				output.flush();
				// Close streams
				output.close();
				input.close();
			} catch (Exception e) {
				Log.e("Error: ", e.getMessage());
			}
			return null;
		}

		// While Downloading Music File
		protected void onProgressUpdate(String... progress) {
			// Set progress percentage
			prgDialog.setProgress(Integer.parseInt(progress[0]));
		}

		// Once Music File is downloaded
		@Override
		protected void onPostExecute(String file_url) {
			// Dismiss the dialog after the Music file was downloaded
			dismissDialog(progress_bar_type);
			Toast.makeText(getApplicationContext(),
					"Download complete, playing Music", Toast.LENGTH_LONG)
					.show();
			downloadsong3.setVisibility(View.GONE);
			playsong3.setVisibility(View.VISIBLE);
		}
	}

	// Async Task Class
	class Downloadson4 extends AsyncTask<String, String, String> {

		// Show Progress bar before downloading Music
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Shows Progress Bar Dialog and then call doInBackground method
			showDialog(progress_bar_type);
		}

		// Download Music File from Internet
		@Override
		protected String doInBackground(String... f_url) {
			int count;
			try {
				URL url = new URL(f_url[0]);
				URLConnection conection = url.openConnection();
				conection.connect();
				// Get Music file length
				int lenghtOfFile = conection.getContentLength();
				// input stream to read file - with 8k buffer
				InputStream input = new BufferedInputStream(url.openStream(),
						10 * 1024);
				// Output stream to write file in SD card
				OutputStream output = new FileOutputStream(Environment
						.getExternalStorageDirectory().getPath()
						+ "/04-Jab-Suna-Ansuna-Karey-Koi.mp4");
				byte data[] = new byte[1024];
				long total = 0;
				while ((count = input.read(data)) != -1) {
					total += count;
					// Publish the progress which triggers onProgressUpdate
					// method
					publishProgress("" + (int) ((total * 100) / lenghtOfFile));

					// Write data to file
					output.write(data, 0, count);
				}
				// Flush output
				output.flush();
				// Close streams
				output.close();
				input.close();
			} catch (Exception e) {
				Log.e("Error: ", e.getMessage());
			}
			return null;
		}

		// While Downloading Music File
		protected void onProgressUpdate(String... progress) {
			// Set progress percentage
			prgDialog.setProgress(Integer.parseInt(progress[0]));
		}

		// Once Music File is downloaded
		@Override
		protected void onPostExecute(String file_url) {
			// Dismiss the dialog after the Music file was downloaded
			dismissDialog(progress_bar_type);
			Toast.makeText(getApplicationContext(),
					"Download complete, playing Music", Toast.LENGTH_LONG)
					.show();
			downloadsong4.setVisibility(View.GONE);
			playsong4.setVisibility(View.VISIBLE);
		}
	}

	// Async Task Class
	class Downloadson5 extends AsyncTask<String, String, String> {

		// Show Progress bar before downloading Music
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Shows Progress Bar Dialog and then call doInBackground method
			showDialog(progress_bar_type);
		}

		// Download Music File from Internet
		@Override
		protected String doInBackground(String... f_url) {
			int count;
			try {
				URL url = new URL(f_url[0]);
				URLConnection conection = url.openConnection();
				conection.connect();
				// Get Music file length
				int lenghtOfFile = conection.getContentLength();
				// input stream to read file - with 8k buffer
				InputStream input = new BufferedInputStream(url.openStream(),
						10 * 1024);
				// Output stream to write file in SD card
				OutputStream output = new FileOutputStream(Environment
						.getExternalStorageDirectory().getPath()
						+ "/05-Kaash-Woh-Jhaankta.mp4");
				byte data[] = new byte[1024];
				long total = 0;
				while ((count = input.read(data)) != -1) {
					total += count;
					// Publish the progress which triggers onProgressUpdate
					// method
					publishProgress("" + (int) ((total * 100) / lenghtOfFile));

					// Write data to file
					output.write(data, 0, count);
				}
				// Flush output
				output.flush();
				// Close streams
				output.close();
				input.close();
			} catch (Exception e) {
				Log.e("Error: ", e.getMessage());
			}
			return null;
		}

		// While Downloading Music File
		protected void onProgressUpdate(String... progress) {
			// Set progress percentage
			prgDialog.setProgress(Integer.parseInt(progress[0]));
		}

		// Once Music File is downloaded
		@Override
		protected void onPostExecute(String file_url) {
			// Dismiss the dialog after the Music file was downloaded
			dismissDialog(progress_bar_type);
			Toast.makeText(getApplicationContext(),
					"Download complete, playing Music", Toast.LENGTH_LONG)
					.show();
			downloadsong5.setVisibility(View.GONE);
			playsong5.setVisibility(View.VISIBLE);
		}
	}

	// Async Task Class
	class Downloadson6 extends AsyncTask<String, String, String> {

		// Show Progress bar before downloading Music
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Shows Progress Bar Dialog and then call doInBackground method
			showDialog(progress_bar_type);
		}

		// Download Music File from Internet
		@Override
		protected String doInBackground(String... f_url) {
			int count;
			try {
				URL url = new URL(f_url[0]);
				URLConnection conection = url.openConnection();
				conection.connect();
				// Get Music file length
				int lenghtOfFile = conection.getContentLength();
				// input stream to read file - with 8k buffer
				InputStream input = new BufferedInputStream(url.openStream(),
						10 * 1024);
				// Output stream to write file in SD card
				OutputStream output = new FileOutputStream(Environment
						.getExternalStorageDirectory().getPath()
						+ "/06-Hai-Tareekh-Go-Inki.mp4");
				byte data[] = new byte[1024];
				long total = 0;
				while ((count = input.read(data)) != -1) {
					total += count;
					// Publish the progress which triggers onProgressUpdate
					// method
					publishProgress("" + (int) ((total * 100) / lenghtOfFile));

					// Write data to file
					output.write(data, 0, count);
				}
				// Flush output
				output.flush();
				// Close streams
				output.close();
				input.close();
			} catch (Exception e) {
				Log.e("Error: ", e.getMessage());
			}
			return null;
		}

		// While Downloading Music File
		protected void onProgressUpdate(String... progress) {
			// Set progress percentage
			prgDialog.setProgress(Integer.parseInt(progress[0]));
		}

		// Once Music File is downloaded
		@Override
		protected void onPostExecute(String file_url) {
			// Dismiss the dialog after the Music file was downloaded
			dismissDialog(progress_bar_type);
			Toast.makeText(getApplicationContext(),
					"Download complete, playing Music", Toast.LENGTH_LONG)
					.show();
			downloadsong6.setVisibility(View.GONE);
			playsong6.setVisibility(View.VISIBLE);
		}
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


		if (id == R.id.action_mybook) {
			Intent homeIntent = new Intent(this.getApplicationContext(),
					OrderHistoryActivity.class);
			startActivity(homeIntent);
			return true;

		}
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
