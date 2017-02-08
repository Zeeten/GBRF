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

public class CapterListActivity  extends ActionBarActivity {

	// Declare Variables
	JSONObject json;
	JSONArray bookJson;
	JSONArray chapterJson;
	ListView listview;
	ChapterListViewAdapter adapter;
	ProgressDialog mProgressDialog;
	// Connection detector
	ConnectionDetector cd;
	String uId;
	
	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();
	ArrayList<HashMap<String, String>> arraylist;
	static String NUMBER = "number";
	static String TITLE = "title";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capter_list);
		ActionBar actionBar = getSupportActionBar();
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		actionBar.setTitle("Award");
		actionBar.setDisplayUseLogoEnabled(true);
		getSupportActionBar().setIcon(R.drawable.logo);
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		uId = preferences.getString("UID", "Test");
		cd = new ConnectionDetector(getApplicationContext());
		   // Check for internet connection
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(CapterListActivity.this, "Internet Connection Error",
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
			mProgressDialog = new ProgressDialog(CapterListActivity.this);
			mProgressDialog.setMessage("Chapter List...");
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
			String url="http://theacademicworld.com/api/rest/awards?uid="+uId;
			String method="GET";
			List param = new ArrayList();
			json = jsonParser.makeHttpRequest(url, method, param);
			System.out.println("lll"+json);

			try {

				// Locate the array name in JSON
				bookJson = json.getJSONArray("data");
 System.out.println("jjj"+bookJson);
				for (int i = 0; i < bookJson.length(); i++) {
					 JSONObject jsonOBject = bookJson.getJSONObject(i);
					
					 chapterJson = jsonOBject.getJSONArray("chapters");
					 for (int j = 0; j < chapterJson.length(); j++) {
							HashMap<String, String> map = new HashMap<String, String>();
							JSONObject chapterOBject = chapterJson.getJSONObject(j);
						 String title = chapterOBject.getString("title");
						  String number = chapterOBject.getString("number");
						  map.put("title", title);
							 map.put("number", number);
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
			adapter = new ChapterListViewAdapter(CapterListActivity.this, arraylist);
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
