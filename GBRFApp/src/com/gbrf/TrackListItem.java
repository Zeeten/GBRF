package com.gbrf;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TrackListItem extends BaseAdapter {
	// Declare Variables
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	ImageLoader imageLoader;
	HashMap<String, String> resultp = new HashMap<String, String>();
 
	public TrackListItem(Context context,
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
		TextView albumId;
		TextView songId;
		TextView albumName;
		Button download;
 
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View itemView = inflater.inflate(R.layout.list_item_tracks, parent, false);
		// Get the position
		resultp = data.get(position);
 
		// Locate the TextViews in listview_item.xml
		albumId = (TextView) itemView.findViewById(R.id.album_id);
		songId = (TextView) itemView.findViewById(R.id.song_id);
		albumName = (TextView) itemView.findViewById(R.id.album_name);
 
		// Locate the ImageView in listview_item.xml
		download = (Button) itemView.findViewById(R.id.downloadsong);
 
		// Capture position and set results to the TextViews
		albumId.setText(resultp.get(TrackListActivity.TAG_ALBUM_ID));
		songId.setText(resultp.get(TrackListActivity.TAG_ID));
		albumName.setText(resultp.get(TrackListActivity.FILENAME));
		// Capture position and set results to the ImageView
		// Passes flag images URL into ImageLoader.class
		download.setText(resultp.get(TrackListActivity.PATH));
		// Capture ListView item click
		download.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View arg0) {
				Toast.makeText(context, "Successful",
						Toast.LENGTH_SHORT).show();
			}
		});
		return itemView;
	}

}

