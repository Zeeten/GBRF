package com.gbrf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gbrf.adapter.AlertDialogManager;
import com.gbrf.adapter.ConnectionDetector;

public class TrackListActivity extends ActionBarActivity {

	// Declare Variables
	JSONObject json;
	JSONArray albumJson;
	ListView listview;
	TrackListItem adapter;
	ProgressDialog mProgressDialog;
	// Connection detector
	ConnectionDetector cd;
	
	String album_id, album_name, title;
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	ArrayList<HashMap<String, String>> arraylist;
	// ALL JSON node names
	 static  String TAG_ID = "fid";
	 static  String TAG_ALBUM_ID = "nid";
	 static  String FILENAME = "filename";
	 static  String PATH = "path";
//	private static final String TAG_SONGS_COUNT = "songs_count";
	private TextView titletxt;
	ImageLoader imageLoader;
	private ImageView imageView;
	private Button download;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tracks);
		ActionBar actionBar = getSupportActionBar();
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		actionBar.setTitle("Literary Music");
		actionBar.setDisplayUseLogoEnabled(true);
		getSupportActionBar().setIcon(R.drawable.logo);
		cd = new ConnectionDetector(getApplicationContext());
		   // Check for internet connection
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(TrackListActivity.this, "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }
        Intent i = getIntent();
		album_id = i.getStringExtra("album_id");
		title = i.getStringExtra("title");
		String image=i.getStringExtra("image");
		titletxt = (TextView) findViewById(R.id.musicname);
		titletxt.setText(title);
		imageLoader = new ImageLoader(this);
		imageView = (ImageView) findViewById(R.id.image);
		imageLoader.DisplayImage(image, imageView);
		download = (Button) findViewById(R.id.downloadsong);
		new DownloadJSON().execute();

	}

	// DownloadJSON AsyncTask
	private class DownloadJSON extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(TrackListActivity.this);
			mProgressDialog.setMessage("Listing songs ...");
			mProgressDialog.setIndeterminate(false);
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// Create an array
			arraylist = new ArrayList<HashMap<String, String>>();
			List<NameValuePair> paramname = new ArrayList<NameValuePair>();
			// Retrieve JSON Objects from the given URL address
			JSONParser jsonParser = new JSONParser();
			paramname.add(new BasicNameValuePair(TAG_ALBUM_ID, album_id));
			String url="http://theacademicworld.com/api/rest/albums";
			String method="GET";
			List param = new ArrayList();
			json = jsonParser.makeHttpRequest(url, method, param);

			try {

				// Locate the array name in JSON
				albumJson = json.getJSONArray("data");
				for (int i = 0; i < albumJson.length(); i++) {
					 JSONObject jsonOBject = albumJson.getJSONObject(i);
						JSONArray songsarry = jsonOBject.getJSONArray("songs");
						for (int j = 0; j < songsarry.length(); j++) {
							HashMap<String, String> map = new HashMap<String, String>();
							JSONObject jsonsong = songsarry.getJSONObject(j);
							String song_id = jsonsong.getString(TAG_ID);
							String songs = jsonsong.getString("filename");
							String path = jsonsong.getString("path");
							map.put(TAG_ID, song_id);
							map.put(FILENAME, songs);
							map.put(PATH, path);
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
			adapter = new TrackListItem(TrackListActivity.this, arraylist);
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
		if (id == R.id.action_logout) {
			savePreferences("Login", false);
			Intent homeIntent = new Intent(this.getApplicationContext(),
					LoginActivity.class);
			startActivity(homeIntent);

			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
