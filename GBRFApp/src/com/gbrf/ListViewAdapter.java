package com.gbrf;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {
	// Declare Variables
		Context context;
		LayoutInflater inflater;
		ArrayList<HashMap<String, String>> data;
		ImageLoader imageLoader;
		String currencySymbol;
		HashMap<String, String> resultp = new HashMap<String, String>();
	 
		public ListViewAdapter(Context context,
				ArrayList<HashMap<String, String>> arraylist) {
			this.context = context;
			data = arraylist;
			imageLoader = new ImageLoader(context);
		}
	 
		@Override
		public int getCount() {
			return data.size();
		}
	 
		@Override
		public Object getItem(int position) {
			return null;
		}
	 
		@Override
		public long getItemId(int position) {
			return 0;
		}
	 
		public View getView(final int position, View convertView, ViewGroup parent) {
			// Declare Variables
			TextView bookname;
			TextView authername;
			TextView price;
			TextView pricelabel;
			ImageView image;
	 
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 
			View itemView = inflater.inflate(R.layout.listview_item, parent, false);
			// Get the position
			resultp = data.get(position);
			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(context);
			currencySymbol = preferences.getString("CurrencySymbol", "Test");
			System.out.println("currencySymbol"+currencySymbol);
			// Locate the TextViews in listview_item.xml
			bookname = (TextView) itemView.findViewById(R.id.bookname);
			authername = (TextView) itemView.findViewById(R.id.authername);
			price = (TextView) itemView.findViewById(R.id.price);
			pricelabel = (TextView) itemView.findViewById(R.id.pricelabel);
			pricelabel.setText(currencySymbol);
			// Locate the ImageView in listview_item.xml
			image = (ImageView) itemView.findViewById(R.id.image);
	 
			// Capture position and set results to the TextViews
			bookname.setText(resultp.get(HomeFragment.BOOKNAME));
			authername.setText(resultp.get(HomeFragment.AUTHERNAME));
			price.setText(resultp.get(HomeFragment.PRICE));
			// Capture position and set results to the ImageView
			// Passes flag images URL into ImageLoader.class
			imageLoader.DisplayImage(resultp.get(HomeFragment.IMAGE), image);
			// Capture ListView item click
			itemView.setOnClickListener(new OnClickListener() {
	 
				@Override
				public void onClick(View arg0) {
					// Get the position
					resultp = data.get(position);
					Intent intent = new Intent(context, SingleItemView.class);
					// Pass all data rank
					intent.putExtra("bookname", resultp.get(HomeFragment.BOOKNAME));
					// Pass all data country
					intent.putExtra("authername", resultp.get(HomeFragment.AUTHERNAME));
					// Pass all data population
					intent.putExtra("price",resultp.get(HomeFragment.PRICE));
					// Pass all data flag
					intent.putExtra("image", resultp.get(HomeFragment.IMAGE));
					intent.putExtra("publisher", resultp.get(HomeFragment.PUBLISHER));
					intent.putExtra("isbn", resultp.get(HomeFragment.ISBN));
					intent.putExtra("pages", resultp.get(HomeFragment.PAGES));
					intent.putExtra("format", resultp.get(HomeFragment.FORMAT));
					intent.putExtra("description", resultp.get(HomeFragment.DESCRIPTION));
					intent.putExtra("nid", resultp.get(HomeFragment.NID));
					intent.putExtra("purchased", resultp.get(HomeFragment.PURCHASED));
					intent.putExtra("oid", resultp.get(HomeFragment.ORDERID));
					intent.putExtra("in_stock", resultp.get(HomeFragment.INSTOCK));
					// Start SingleItemView Class
					context.startActivity(intent);
	 
				}
			});
			return itemView;
		}

}
