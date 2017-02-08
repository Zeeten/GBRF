package com.gbrf;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.gbrf.adapter.AlertDialogManager;
import com.gbrf.adapter.ConnectionDetector;
import com.gbrf.dto.UserDTO;

public class IdharBhiMusicActivity extends ActionBarActivity {

	// Button to download and play Music
	private Button downloadsong1;
	private Button playsong1;
	private Button downloadsong2;
	private Button playsong2;

	// Media Player Object
	private MediaPlayer mPlayer;
	// Progress Dialog Object
	private ProgressDialog prgDialog;
	// Progress Dialog type (0 - for Horizontal progress bar)
	public static final int progress_bar_type = 0;
	// Music resource URL
	private static String song1_file_url = "http://theacademicworld.com/sites/academicworld/uploads/videos/Kyun-Jung-Ke-Saamaan.mp4";
	private static String song2_file_url = "http://theacademicworld.com/sites/academicworld/uploads/music/Kyun-Jung-Ke-Saamaan-Ringtone.mp3";
	// Connection detector
	ConnectionDetector cd;
	private String account;
	private static String APP_ID = "888751001205538";
	private Facebook facebook = new Facebook(APP_ID);
	private AsyncFacebookRunner mAsyncRunner;

	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_idhar_bhi_music);
		ActionBar actionBar = getSupportActionBar();
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		actionBar.setTitle("Literary Music");
		actionBar.setDisplayUseLogoEnabled(true);
		getSupportActionBar().setIcon(R.drawable.logo);
		downloadsong1 = (Button) findViewById(R.id.downloadsong1);
		playsong1 = (Button) findViewById(R.id.playsong1);
		downloadsong2 = (Button) findViewById(R.id.downloadsong2);
		playsong2 = (Button) findViewById(R.id.playsong2);
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		account = preferences.getString("account", "Test");
		mAsyncRunner = new AsyncFacebookRunner(facebook);

		File song1_file = new File(Environment.getExternalStorageDirectory()
				.getPath() + "/Kyun-Jung-Ke-Saamaan.mp4");
		File song2_file = new File(Environment.getExternalStorageDirectory()
				.getPath() + "/Kyun-Jung-Ke-Saamaan-Ringtone.mp3");

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

		// Download Music Button click listener
		downloadsong1.setOnClickListener(new View.OnClickListener() {
			// When Download Music Button is clicked
			public void onClick(View v) {
				cd = new ConnectionDetector(getApplicationContext());
				// Check for internet connection
				if (!cd.isConnectingToInternet()) {
					// Internet Connection is not present
					alert.showAlertDialog(IdharBhiMusicActivity.this,
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
					alert.showAlertDialog(IdharBhiMusicActivity.this,
							"Internet Connection Error",
							"Please connect to working Internet connection",
							false);
					// stop executing code by return
					return;
				}
				new Downloadson2().execute(song2_file_url);

			}
		});

		playsong1.setOnClickListener(new View.OnClickListener() {
			// When Download Music Button is clicked
			public void onClick(View v) {
				String title = "Kyun Jung Ke Saamaan";
				String url = "file:///sdcard/Kyun-Jung-Ke-Saamaan.mp4";
				DataSyncService service = new DataSyncService();
				UserDTO dto = service.music(title, url);
				((PE) getApplicationContext()).setUserDTO(dto);
				Intent intent = new Intent(IdharBhiMusicActivity.this,
						VideoViewActivity.class);
				startActivity(intent);

			}
		});
		playsong2.setOnClickListener(new View.OnClickListener() {
			// When Download Music Button is clicked
			public void onClick(View v) {
				String title = "Kyun Jung Ke Saamaan";
				String url = "file:///sdcard/Kyun-Jung-Ke-Saamaan-Ringtone.mp3";
				DataSyncService service = new DataSyncService();
				UserDTO dto = service.music(title, url);
				((PE) getApplicationContext()).setUserDTO(dto);
				Intent intent = new Intent(IdharBhiMusicActivity.this,
						VideoViewActivity.class);
				startActivity(intent);

			}
		});

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
						+ "/Kyun-Jung-Ke-Saamaan.mp4");
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
						+ "/Kyun-Jung-Ke-Saamaan-Ringtone.mp3");
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
		// noinspection SimplifiableIfStatement
		if (id == R.id.action_logout) {
			if (account.equalsIgnoreCase("taw")) {
				System.out.println("ifff");
				savePreferences("Login", false);
				Intent homeIntent = new Intent(this.getApplicationContext(),
						LoginActivity.class);
				startActivity(homeIntent);

				finish();
				return true;
			} else {

				logoutFromFacebook();
				return true;
			}

		}

		return super.onOptionsItemSelected(item);
	}
	/**
	 * Function to Logout user from Facebook
	 * */
	public void logoutFromFacebook() {

		mAsyncRunner.logout(this, new RequestListener() {
			@Override
			public void onComplete(String response, Object state) {
				if (Boolean.parseBoolean(response) == true) {
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Intent homeIntent = new Intent(IdharBhiMusicActivity.this,
									LoginActivity.class);
							startActivity(homeIntent);

							finish();

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
	}


}
