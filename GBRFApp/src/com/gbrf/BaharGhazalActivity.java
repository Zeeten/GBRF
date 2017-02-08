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

public class BaharGhazalActivity extends ActionBarActivity {

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
	private Button playsong7;
	private Button downloadsong7;
	// Media Player Object
	private MediaPlayer mPlayer;
	private String account;
	private static String APP_ID = "888751001205538";
	private Facebook facebook = new Facebook(APP_ID);
	private AsyncFacebookRunner mAsyncRunner;
	// Progress Dialog Object
	private ProgressDialog prgDialog;
	// Progress Dialog type (0 - for Horizontal progress bar)
	public static final int progress_bar_type = 0;
	// Music resource URL
	private static String song1_file_url = "http://theacademicworld.com/sites/academicworld/uploads/videos/01-Mujhko-Ik-Jaam-Bhi.mp4";
	private static String song2_file_url = "http://theacademicworld.com/sites/academicworld/uploads/videos/02-Shart-Itni-To.mp4";
	private static String song3_file_url = "http://theacademicworld.com/sites/academicworld/uploads/videos/03-Jahan-Tera-Naqsh-e-Kadam.mp4";
	private static String song4_file_url = "http://theacademicworld.com/sites/academicworld/uploads/videos/04-Roz-o-Shab-Milta-Hai.mp4";
	private static String song5_file_url = "http://theacademicworld.com/sites/academicworld/uploads/videos/05-Husn-Wale.mp4";
	private static String song6_file_url = "http://theacademicworld.com/sites/academicworld/uploads/videos/06-Taqdeer-Banata-Hoon.mp4";
	private static String song7_file_url = "http://theacademicworld.com/sites/academicworld/uploads/videos/07-Aap-Kyu-Ruk-Gae.mp4";

	// Connection detector
	ConnectionDetector cd;

	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bahar_ghazal);
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
		downloadsong7 = (Button) findViewById(R.id.downloadsong7);
		playsong7 = (Button) findViewById(R.id.playsong7);
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		account = preferences.getString("account", "Test");
		mAsyncRunner = new AsyncFacebookRunner(facebook);
		File song1_file = new File(Environment.getExternalStorageDirectory()
				.getPath() + "/01-Mujhko-Ik-Jaam-Bhi.mp4");
		File song2_file = new File(Environment.getExternalStorageDirectory()
				.getPath() + "/02-Shart-Itni-To.mp4");
		File song3_file = new File(Environment.getExternalStorageDirectory()
				.getPath() + "/03-Jahan-Tera-Naqsh-e-Kadam.mp4");
		File song4_file = new File(Environment.getExternalStorageDirectory()
				.getPath() + "/04-Roz-o-Shab-Milta-Hai.mp4");
		File song5_file = new File(Environment.getExternalStorageDirectory()
				.getPath() + "/05-Husn-Wale.mp4");
		File song6_file = new File(Environment.getExternalStorageDirectory()
				.getPath() + "/06-Taqdeer-Banata-Hoon.mp4");
		File song7_file = new File(Environment.getExternalStorageDirectory()
				.getPath() + "/07-Aap-Kyu-Ruk-Gae.mp4");
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
		if (song7_file.exists()) {
			downloadsong7.setVisibility(View.GONE);
			playsong7.setVisibility(View.VISIBLE);
		} else {
			downloadsong7.setVisibility(View.VISIBLE);
			playsong7.setVisibility(View.GONE);
		}
		// Download Music Button click listener
		downloadsong1.setOnClickListener(new View.OnClickListener() {
			// When Download Music Button is clicked
			public void onClick(View v) {
				cd = new ConnectionDetector(getApplicationContext());
				// Check for internet connection
				if (!cd.isConnectingToInternet()) {
					// Internet Connection is not present
					alert.showAlertDialog(BaharGhazalActivity.this,
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
					alert.showAlertDialog(BaharGhazalActivity.this,
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
					alert.showAlertDialog(BaharGhazalActivity.this,
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
					alert.showAlertDialog(BaharGhazalActivity.this,
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
					alert.showAlertDialog(BaharGhazalActivity.this,
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
					alert.showAlertDialog(BaharGhazalActivity.this,
							"Internet Connection Error",
							"Please connect to working Internet connection",
							false);
					// stop executing code by return
					return;
				}
				new Downloadson6().execute(song6_file_url);

			}
		});
		// Download Music Button click listener
		downloadsong7.setOnClickListener(new View.OnClickListener() {
			// When Download Music Button is clicked
			public void onClick(View v) {
				cd = new ConnectionDetector(getApplicationContext());
				// Check for internet connection
				if (!cd.isConnectingToInternet()) {
					// Internet Connection is not present
					alert.showAlertDialog(BaharGhazalActivity.this,
							"Internet Connection Error",
							"Please connect to working Internet connection",
							false);
					// stop executing code by return
					return;
				}
				new Downloadson7().execute(song7_file_url);

			}
		});

		playsong1.setOnClickListener(new View.OnClickListener() {
			// When Download Music Button is clicked
			public void onClick(View v) {
				String title = "Mujhko-Ik-Jaam-Bhi";
				String url = "file:///sdcard/01-Mujhko-Ik-Jaam-Bhi.mp4";
				DataSyncService service = new DataSyncService();
				UserDTO dto = service.music(title, url);
				((PE) getApplicationContext()).setUserDTO(dto);
				Intent intent = new Intent(BaharGhazalActivity.this,
						VideoViewActivity.class);
				startActivity(intent);

			}
		});
		playsong2.setOnClickListener(new View.OnClickListener() {
			// When Download Music Button is clicked
			public void onClick(View v) {
				String title = "Shart-Itni-To";
				String url = "file:///sdcard/02-Shart-Itni-To.mp4";
				DataSyncService service = new DataSyncService();
				UserDTO dto = service.music(title, url);
				((PE) getApplicationContext()).setUserDTO(dto);
				Intent intent = new Intent(BaharGhazalActivity.this,
						VideoViewActivity.class);
				startActivity(intent);

			}
		});
		playsong3.setOnClickListener(new View.OnClickListener() {
			// When Download Music Button is clicked
			public void onClick(View v) {
				String title = "Jahan-Tera-Naqsh-e-Kadam";
				String url = "file:///sdcard/03-Jahan-Tera-Naqsh-e-Kadam.mp4";
				DataSyncService service = new DataSyncService();
				UserDTO dto = service.music(title, url);
				((PE) getApplicationContext()).setUserDTO(dto);
				Intent intent = new Intent(BaharGhazalActivity.this,
						VideoViewActivity.class);
				startActivity(intent);

			}
		});
		playsong4.setOnClickListener(new View.OnClickListener() {
			// When Download Music Button is clicked
			public void onClick(View v) {
				String title = "Roz-o-Shab-Milta-Hai";
				String url = "file:///sdcard/04-Roz-o-Shab-Milta-Hai.mp4";
				DataSyncService service = new DataSyncService();
				UserDTO dto = service.music(title, url);
				((PE) getApplicationContext()).setUserDTO(dto);
				Intent intent = new Intent(BaharGhazalActivity.this,
						VideoViewActivity.class);
				startActivity(intent);

			}
		});
		playsong5.setOnClickListener(new View.OnClickListener() {
			// When Download Music Button is clicked
			public void onClick(View v) {
				String title = "Husn-Wale";
				String url = "file:///sdcard/05-Husn-Wale.mp4";
				DataSyncService service = new DataSyncService();
				UserDTO dto = service.music(title, url);
				((PE) getApplicationContext()).setUserDTO(dto);
				Intent intent = new Intent(BaharGhazalActivity.this,
						VideoViewActivity.class);
				startActivity(intent);

			}
		});
		playsong6.setOnClickListener(new View.OnClickListener() {
			// When Download Music Button is clicked
			public void onClick(View v) {
				String title = "Taqdeer-Banata-Hoon";
				String url = "file:///sdcard/06-Taqdeer-Banata-Hoon.mp4";
				DataSyncService service = new DataSyncService();
				UserDTO dto = service.music(title, url);
				((PE) getApplicationContext()).setUserDTO(dto);
				Intent intent = new Intent(BaharGhazalActivity.this,
						VideoViewActivity.class);
				startActivity(intent);

			}
		});

		playsong7.setOnClickListener(new View.OnClickListener() {
			// When Download Music Button is clicked
			public void onClick(View v) {
				String title = "Aap-Kyu-Ruk-Gae";
				String url = "file:///sdcard/07-Aap-Kyu-Ruk-Gae.mp4";
				DataSyncService service = new DataSyncService();
				UserDTO dto = service.music(title, url);
				((PE) getApplicationContext()).setUserDTO(dto);
				Intent intent = new Intent(BaharGhazalActivity.this,
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
						+ "/01-Mujhko-Ik-Jaam-Bhi.mp4");
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
						+ "/02-Shart-Itni-To.mp4");
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
						+ "/03-Jahan-Tera-Naqsh-e-Kadam.mp4");
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
						+ "/04-Roz-o-Shab-Milta-Hai.mp4");
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
						+ "/05-Husn-Wale.mp4");
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
						+ "/06-Taqdeer-Banata-Hoon.mp4");
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

	// Async Task Class
	class Downloadson7 extends AsyncTask<String, String, String> {

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
						+ "/07-Aap-Kyu-Ruk-Gae.mp4");
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
			downloadsong7.setVisibility(View.GONE);
			playsong7.setVisibility(View.VISIBLE);
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
							Intent homeIntent = new Intent(BaharGhazalActivity.this,
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
