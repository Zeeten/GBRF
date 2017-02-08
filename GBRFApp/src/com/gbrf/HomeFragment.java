package com.gbrf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gbrf.adapter.AlertDialogManager;
import com.gbrf.adapter.ConnectionDetector;

@SuppressLint("NewApi")
public class HomeFragment extends Fragment {

	public HomeFragment() {
	}
	// Declare Variables
		JSONObject json;
		JSONArray bookJson;
		ListView listview;
		ListViewAdapter adapter;
		ProgressDialog mProgressDialog;
		// Connection detector
		ConnectionDetector cd;
		private String uId;
		// Alert dialog manager
		AlertDialogManager alert = new AlertDialogManager();
		ArrayList<HashMap<String, String>> arraylist;
		static String BOOKNAME = "bookname";
		static String AUTHERNAME = "authername";
		static String PRICE = "price";
		static String IMAGE = "image";
		static String PUBLISHER = "publisher";
		static String ISBN = "isbn";
		static String PAGES = "pages";
		static String FORMAT = "format";
		static String DESCRIPTION = "description";
		static String NID = "nid";
		static String ORDERID = "oid";
		static String PURCHASED = "purchased";
		static String INSTOCK = "in_stock";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.activity_home_fragment,
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
			mProgressDialog.setMessage("Listing Book ...");
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
			String url="http://theacademicworld.com/api/rest/books";
			String method="GET";
			List param = new ArrayList();
			param.add(new BasicNameValuePair("uid", uId));
			json = jsonParser.makeHttpRequest(url, method, param);
			System.out.println("lll"+json);

			try {

				// Locate the array name in JSON
				bookJson = json.getJSONArray("data");
 System.out.println("jjj"+bookJson);
				for (int i = 0; i < bookJson.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					 JSONObject jsonOBject = bookJson.getJSONObject(i);

					 String price = jsonOBject.getString("cost_price");
					
					 String name = jsonOBject.getString("title");
                        String image = jsonOBject.getString("image");
                        image.replace("\\", "");
                        String authername = jsonOBject.getString("author");
                        String publisher = jsonOBject.getString("publisher");
                        String isbn = jsonOBject.getString("isbn");
                        String pages = jsonOBject.getString("pages");
                        String format = jsonOBject.getString("format");
                        String description = jsonOBject.getString("description");
                        String nid = jsonOBject.getString("nid");
                        String purchase = jsonOBject.getString(PURCHASED);
                        String oid = jsonOBject.getString(ORDERID);
                        String instock = jsonOBject.getString(INSTOCK);
						 map.put("authername", authername);
						 map.put("publisher", publisher);
						 map.put("isbn", isbn);
						 map.put("pages", pages);
						 map.put("format", format);
						 map.put("description", description);
						 map.put("nid", nid);
						 map.put(ORDERID, oid);
						 map.put(PURCHASED, purchase);
						 map.put(INSTOCK, instock);
				
					// Set the JSON Objects into the array
					 System.out.println(image);
					 map.put("price", price);
					 map.put("bookname", name);
					 map.put("image",image);
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
			listview = (ListView) rootView.findViewById(R.id.listview);
			// Pass the results into ListViewAdapter.java
			adapter = new ListViewAdapter(getActivity(), arraylist);
			// Set the adapter to the ListView
			listview.setAdapter(adapter);
			// Close the progressdialog
			mProgressDialog.dismiss();
		}
	}



}
