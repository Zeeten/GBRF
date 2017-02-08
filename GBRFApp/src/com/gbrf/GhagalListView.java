package com.gbrf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GhagalListView extends BaseAdapter {
	// Declare Variables
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	HashMap<String, String> resultp = new HashMap<String, String>();

	public GhagalListView(Context context,
			ArrayList<HashMap<String, String>> arraylist) {
		this.context = context;
		data = arraylist;
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
		TextView title;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View itemView = inflater.inflate(R.layout.ghagal_list_view, parent,
				false);
		// Get the position
		resultp = data.get(position);

		// Locate the TextViews in listview_item.xml
		title = (TextView) itemView.findViewById(R.id.music1);

		// Capture position and set results to the TextViews
	//	title.setText(resultp.get(MusicHomeActivity.TITLE));

		itemView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Get the position
				resultp = data.get(position);
				Intent intent = new Intent(context, LiteraryMusicActivity.class);
				// Pass all data rank
				ArrayList mylist = new ArrayList();
				PE pe = (PE) context.getApplicationContext();
				mylist = (ArrayList) pe.getList();
				System.out.println("list"+mylist);
				Iterator it = mylist.iterator();

				while (it.hasNext()) {
					Map<String, String> map = (Map) it.next();
					String name = map.get("songs");
					System.out.println("nameff"+name);
				}
			//	intent.putExtra("songs", resultp.get(MusicHomeActivity.SONGS));
				// Pass all data country
			//	intent.putExtra("path", resultp.get(MusicHomeActivity.PATH));
			//	intent.putExtra("title", resultp.get(MusicHomeActivity.TITLE));
			//	intent.putExtra("image", resultp.get(MusicHomeActivity.IMAGE));

				// Start SingleItemView Class
				context.startActivity(intent);

			}
		});
		return itemView;
	}

}
