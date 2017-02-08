package com.gbrf;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gbrf.dto.UserDTO;

@SuppressLint("NewApi")
public class OrderListView extends BaseAdapter {

	// Declare Variables
	 Context context;
	//private Activity mContext;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	ImageLoader imageLoader;
	HashMap<String, String> resultp = new HashMap<String, String>();
	private UserOrderTask mOrderTask = null;
	private ProgressDialog prgDialog;
	String sid,ssid;
	public static final int progress_bar_type = 0;
	String	fileName;
	public OrderListView(Context context,
			ArrayList<HashMap<String, String>> arraylist) {
		 this.context = context;
		data = arraylist;
		imageLoader = new ImageLoader(context);
		inflater = LayoutInflater.from(this.context);
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

		TextView name;
		ImageView download;
		ImageView image;

		View itemView = inflater.inflate(R.layout.order_list_view, parent,
				false);
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		sid = preferences.getString("sid", "Test");
		ssid = preferences.getString("ssid", "Test");
		// Get the position
		resultp = data.get(position);

		name = (TextView) itemView.findViewById(R.id.name);

		name.setText(resultp.get(OrderHistoryActivity.NAME));
		image = (ImageView) itemView.findViewById(R.id.image);
		imageLoader.DisplayImage(resultp.get(HomeFragment.IMAGE), image);

		// Capture ListView item click
		itemView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Get the position
				resultp = data.get(position);
			String orderId=resultp.get(OrderHistoryActivity.ORDERID);
			String uId=resultp.get(OrderHistoryActivity.UID);
			String nId=	resultp.get(OrderHistoryActivity.NID);
				mOrderTask = new UserOrderTask(nId, uId,orderId,sid,ssid);
				mOrderTask.execute((Void) null);
			}
		});
		return itemView;
	}
	private void savePreferences(String key, String value) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor edit = sp.edit();
		edit.putString(key, value);
		edit.commit();
	}
	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserOrderTask extends AsyncTask<Void, Void, Boolean> {

		private final String mnId;
		private final String muId;
		private final String msId;
		private final String mssId;
		private final String morderId;

		UserOrderTask(String nId, String uId,String orderId,String sId,String ssId) {

			mnId = nId;
			muId = uId;
			morderId=orderId;
			msId=sId;
			mssId=ssId;

		}

		@Override
		protected Boolean doInBackground(Void... params) {
			DataSyncService service=new DataSyncService();
/*
			StringBuilder ORDER_URL = new StringBuilder("http://theacademicworld.com/book/read?nid="+mnId);
			ORDER_URL.append("&uid="+muId);
			ORDER_URL.append("&oid="+morderId);
			ORDER_URL.append("&device=android");
			String URL=ORDER_URL.toString();
			savePreferences("URL",URL);
*/
			try {
				UserDTO dto = service.BookRead(muId, mnId, morderId,msId,mssId);
				((PE) context.getApplicationContext()).setUserDTO(dto);
				if (dto.getStatus().equals("success")) {
					return true;
				}
				

			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}

	 @Override
		protected void onPostExecute(final Boolean success) {
			mOrderTask = null;
			if (success) {
				savePreferences("URL", ((PE) context.getApplicationContext())
						.getUserDTO().getUrl());
				Intent browserIntent = new Intent(context.getApplicationContext(), WebViewBookActivity.class);
				browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.getApplicationContext().startActivity(browserIntent);
			}else{
				Builder alert = new AlertDialog.Builder(context,
						R.style.AlertDialogCustom);
				alert.setMessage(((PE) context.getApplicationContext()).getUserDTO()
						.getMessage());
				alert.setPositiveButton("OK", null);
				alert.show();
			}
		}

		@Override
		protected void onCancelled() {
			mOrderTask = null;
		}
	}

	
}
