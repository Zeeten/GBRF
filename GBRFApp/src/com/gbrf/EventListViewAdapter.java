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

public class EventListViewAdapter extends  BaseAdapter {
	// Declare Variables
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	ImageLoader imageLoader;
	HashMap<String, String> resultp = new HashMap<String, String>();
 
	public EventListViewAdapter(Context context,
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
		TextView releaseon;
		ImageView image;
 
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View itemView = inflater.inflate(R.layout.event_list_view_adapter, parent, false);
		// Get the position
		resultp = data.get(position);
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);

		// Locate the ImageView in listview_item.xml
		image = (ImageView) itemView.findViewById(R.id.image);
		bookname = (TextView) itemView.findViewById(R.id.bookname);
		releaseon = (TextView) itemView.findViewById(R.id.releasedate);
		// Capture position and set results to the TextViews
		bookname.setText(resultp.get(EventFragment.BOOKNAME));
		releaseon.setText(resultp.get(EventFragment.RELEASEON));
		// Capture position and set results to the ImageView
		// Passes flag images URL into ImageLoader.class
		imageLoader.DisplayImage(resultp.get(EventFragment.THUMBNAILIMAGE), image);
		// Capture ListView item click
       itemView.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				// Get the position
				resultp = data.get(position);
				if(resultp.get(EventFragment.INVITATIONSTATUS).equalsIgnoreCase("accepted")){
					Intent intent = new Intent(context, AwardDetailActivity.class);
					intent.putExtra("thumbnailimage", resultp.get(EventFragment.THUMBNAILIMAGE));
					intent.putExtra("releasedon", resultp.get(EventFragment.RELEASEON));
					intent.putExtra("nId", resultp.get(EventFragment.NID));
					intent.putExtra("iid", resultp.get(EventFragment.IID));
					intent.putExtra("released_on", resultp.get(EventFragment.RELEASEDON));
					intent.putExtra("accepted_on", resultp.get(EventFragment.ACCEPTEDON));
					context.startActivity(intent);
				}else{
					Intent intent = new Intent(context, InviteActivity.class);
					intent.putExtra("thumbnailimage", resultp.get(EventFragment.THUMBNAILIMAGE));
					intent.putExtra("releasedon", resultp.get(EventFragment.RELEASEON));
					intent.putExtra("released_on", resultp.get(EventFragment.RELEASEDON));
					intent.putExtra("accepted_on", resultp.get(EventFragment.ACCEPTEDON));
					intent.putExtra("nId", resultp.get(EventFragment.NID));
					context.startActivity(intent);
				}
				
 
			}
		});
		return itemView;
	}

}
