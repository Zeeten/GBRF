package com.gbrf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gbrf.adapter.AlertDialogManager;
import com.gbrf.adapter.ConnectionDetector;

public class EventFragment extends Fragment {

	public EventFragment() {
	}
	// Declare Variables
		JSONObject json;
		JSONArray eventJson;
		ListView listview;
		EventListViewAdapter adapter;
		ProgressDialog mProgressDialog;
		// Connection detector
		ConnectionDetector cd;
		private String uId;
		// Alert dialog manager
		AlertDialogManager alert = new AlertDialogManager();
		ArrayList<HashMap<String, String>> arraylist;
		static String BOOKNAME = "bookname";
		static String IMAGE = "image";
		static String THUMBNAILIMAGE = "thumbnailimage";
		static String DESCRIPTION = "description";
		static String NID = "nid";
		static String ORDERID = "oid";
		static String RELEASEON="releaseon";
		static String STARTDATE="startdate";
		static String ENDDATE="enddate";
		static String IID="iid";
		static String ACCEPTEDON="accepted_on";
		static String RELEASEDON="released_on";
		static String INVITATIONSTATUS="invitationstatus";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.event_fragment,
				container, false);
		cd = new ConnectionDetector(getActivity().getApplicationContext());
		   // Check for internet connection
     if (!cd.isConnectingToInternet()) {
         // Internet Connection is not present
         alert.showAlertDialog(getActivity(), "Internet Connection Error",
                 "Please connect to working Internet connection", false);
         // stop executing code by return
         return rootView;
     }
 	SharedPreferences preferences = PreferenceManager
			.getDefaultSharedPreferences(getActivity());
	uId = preferences.getString("UID", "Test");
		new DownloadJSON(null, rootView).execute();

		return rootView;
	}
	
	private void savePreferences(String key, String value) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(getActivity().getApplicationContext());
		Editor edit = sp.edit();
		edit.putString(key, value);
		edit.commit();
	}
	
	// DownloadJSON AsyncTask
	private class DownloadJSON extends AsyncTask<Void, Void, Void> {

		private Context mContext;
		private View rootView;

		public DownloadJSON(Context context, View rootView) {
			this.mContext = context;
			this.rootView = rootView;
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(getActivity());
			mProgressDialog.setMessage("Listing Event ...");
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
			String url="http://theacademicworld.com/api/rest/events";
			String method="GET";
			List param = new ArrayList();
			param.add(new BasicNameValuePair("uid", uId));
			json = jsonParser.makeHttpRequest(url, method, param);
			System.out.println("lll"+json);

			try {

				if (json.getString("status").equalsIgnoreCase("success")) {
					// Locate the array name in JSON
					eventJson = json.getJSONArray("data");
					System.out.println("jjj" + eventJson);
				for (int i = 0; i < eventJson.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject jsonOBject = eventJson.getJSONObject(i);
					String name = jsonOBject.getString("title");
					String image = jsonOBject.getString("cover");
					image.replace("\\", "");
					String thumbnailimage = jsonOBject.getString("thumbnail");
					thumbnailimage.replace("\\", "");
					String description = jsonOBject.getString("description");
					String releaseon = jsonOBject.getString("released_on");
					savePreferences("releasedon", releaseon);
					String startdate = jsonOBject.getString("startdate");
					String enddate = jsonOBject.getString("enddate");
					String nid = jsonOBject.getString("nid");
					String invitationstatus = jsonOBject.getString("invitaion_status");
					if(jsonOBject.getString("invitaion_status").equalsIgnoreCase("accepted")){
						
						String invitations = jsonOBject.getString("invitations");
						JSONArray invitationsjsarray = new JSONArray(invitations);
						for (int j = 0; j < invitationsjsarray.length(); j++) {
							JSONObject jsonOBj = invitationsjsarray.getJSONObject(j);
							String iid = jsonOBj.getString("iid");
							String accepted_on = jsonOBj.getString("accepted_on");
							String released_on = jsonOBj.getString("released_on");
							//savePreferences("iid", iid);
							map.put("iid", iid);
							map.put("accepted_on", accepted_on);
							map.put("released_on", released_on);
						}
					}
					map.put("description", description);
					map.put("nid", nid);
					// Set the JSON Objects into the array
					System.out.println(image);
					map.put("bookname", name);
					map.put("startdate", startdate);
					map.put("releaseon", releaseon);
					map.put("enddate", enddate);
					map.put("invitationstatus", invitationstatus);
					map.put("image", image);
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
			listview = (ListView) rootView.findViewById(R.id.listview);
			// Pass the results into ListViewAdapter.java
			adapter = new EventListViewAdapter(getActivity(), arraylist);
			// Set the adapter to the ListView
			listview.setAdapter(adapter);
			// Close the progressdialog
			mProgressDialog.dismiss();
		}
	}
	
	
}
