package com.gbrf;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AlbumListItem extends BaseAdapter {
	// Declare Variables
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	ImageLoader imageLoader;
	HashMap<String, String> resultp = new HashMap<String, String>();
 
	public AlbumListItem(Context context,
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
		TextView id;
		TextView name;
		TextView description;
		ImageView image;
 
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View itemView = inflater.inflate(R.layout.list_item_albums, parent, false);
		// Get the position
		resultp = data.get(position);
 
		// Locate the TextViews in listview_item.xml
		id = (TextView) itemView.findViewById(R.id.album_id);
		name = (TextView) itemView.findViewById(R.id.album_name);
		description = (TextView) itemView.findViewById(R.id.description);
 
		// Locate the ImageView in listview_item.xml
		image = (ImageView) itemView.findViewById(R.id.imageview);
 
		// Capture position and set results to the TextViews
		id.setText(resultp.get(AlbumsActivity.TAG_ID));
		name.setText(resultp.get(AlbumsActivity.TITLE));
		description.setText(resultp.get(AlbumsActivity.DESCRIPTION));
		// Capture position and set results to the ImageView
		// Passes flag images URL into ImageLoader.class
		imageLoader.DisplayImage(resultp.get(AlbumsActivity.IMAGE), image);
		// Capture ListView item click
		itemView.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View arg0) {
				// Get the position
				resultp = data.get(position);
				Intent intent = new Intent(context, TrackListActivity.class);
				// Pass all data rank
				intent.putExtra("album_id", resultp.get(AlbumsActivity.TAG_ID));
				// Pass all data country
				intent.putExtra("title", resultp.get(AlbumsActivity.TITLE));
				// Pass all data flag
				intent.putExtra("image", resultp.get(AlbumsActivity.IMAGE));
				
				context.startActivity(intent);
 
			}
		});
		return itemView;
	}

}
