package com.gbrf;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ChapterListViewAdapter extends BaseAdapter {
	// Declare Variables
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	HashMap<String, String> resultp = new HashMap<String, String>();

	public ChapterListViewAdapter(Context context,
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
		TextView number;
		TextView title;
		RadioGroup group;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View itemView = inflater.inflate(R.layout.chapter_list_view_adapter, parent, false);
		// Get the position
		resultp = data.get(position);

		// Locate the TextViews in listview_item.xml
		number = (TextView) itemView.findViewById(R.id.number);
		title = (TextView) itemView.findViewById(R.id.title);
		//group = (RadioGroup) itemView.findViewById(R.id.radio_group1);
		

		// Capture position and set results to the TextViews
		number.setText(resultp.get(CapterListActivity.NUMBER));
		title.setText(resultp.get(CapterListActivity.TITLE));
/*		final RadioButton[] rb = new RadioButton[3];
        for(int i=0; i<3; i++){
            rb[i]  = new RadioButton(context);
            //rb[i].setButtonDrawable(R.drawable.single_radio_chice);
            rb[i].setId(i);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
            	    0, LayoutParams.WRAP_CONTENT);
            params.weight=1.0f;
            params.setMargins(3, 0, 3, 10);
            group.addView(rb[i],params); //the RadioButtons are added to the radioGroup instead of the layout
        }*/

	
		return itemView;
	}

}
