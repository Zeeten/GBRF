package com.gbrf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import android.widget.ListView;

import com.gbrf.adapter.AlertDialogManager;
import com.gbrf.adapter.ConnectionDetector;

public class AlbumsActivity extends ActionBarActivity {

	// Declare Variables
	JSONObject json;
	JSONArray albumJson;
	ListView listview;
	AlbumListItem adapter;
	ProgressDialog mProgressDialog;
	// Connection detector
	ConnectionDetector cd;
	
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	ArrayList<HashMap<String, String>> arraylist;
	// ALL JSON node names
	 static  String TAG_ID = "nid";
	 static  String TITLE = "title";
	 static  String IMAGE = "body";
	 static  String DESCRIPTION = "description";
//	private static final String TAG_SONGS_COUNT = "songs_count";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_albums);
		ActionBar actionBar = getSupportActionBar();
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		actionBar.setTitle("Literary Music");
		actionBar.setDisplayUseLogoEnabled(true);
		getSupportActionBar().setIcon(R.drawable.logo);
		cd = new ConnectionDetector(getApplicationContext());
		   // Check for internet connection
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(AlbumsActivity.this, "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return;
        }
		new DownloadJSON().execute();

	}

	// DownloadJSON AsyncTask
	private class DownloadJSON extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(AlbumsActivity.this);
			mProgressDialog.setMessage("Listing Album ...");
			mProgressDialog.setIndeterminate(false);
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// Create an array
			arraylist = new ArrayList<HashMap<String, String>>();
			// Retrieve JSON Objects from the given URL address
			JSONParser jsonParser = new JSONParser();
			String url="http://theacademicworld.com/api/rest/albums";
			String method="GET";
			List param = new ArrayList();
			json = jsonParser.makeHttpRequest(url, method, param);

			try {

				// Locate the array name in JSON
				albumJson = json.getJSONArray("data");
				for (int i = 0; i < albumJson.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					 JSONObject jsonOBject = albumJson.getJSONObject(i);
						String id = jsonOBject.getString(TAG_ID);
						String name = jsonOBject.getString(TITLE);
						String image = jsonOBject.getString(IMAGE);
					    image.replace("\\", "");
					    String description = jsonOBject.getString(DESCRIPTION);
					    map.put(TAG_ID, id);
						map.put(TITLE, name);
						map.put(IMAGE, image);
						map.put(DESCRIPTION, description);
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
			adapter = new AlbumListItem(AlbumsActivity.this, arraylist);
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
