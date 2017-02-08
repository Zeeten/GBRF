package com.gbrf;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gbrf.adapter.ImageSlideAdapter;
import com.gbrf.dto.Product;
import com.gbrf.util.CheckNetworkConnection;
import com.gbrf.util.CirclePageIndicator;
import com.gbrf.util.PageIndicator;

public class BookFragment extends Fragment {

	public static final String ARG_ITEM_ID = "home_fragment";

	private static final long ANIM_VIEWPAGER_DELAY = 5000;
	private static final long ANIM_VIEWPAGER_DELAY_USER_VIEW = 10000;

	// UI References
	private ViewPager mViewPager;
	TextView imgNameTxt;
	TextView priceTxt;
	PageIndicator mIndicator;

	AlertDialog alertDialog;

	List<Product> products;
	RequestImgTask task;
	boolean stopSliding = false;
	String message;

	private Runnable animateViewPager;
	private Handler handler;

	String url = "http://theacademicworld.com/store/api/rest/products";
	FragmentActivity activity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_book_fragment, container, false);
		findViewById(view);

		mIndicator.setOnPageChangeListener(new PageChangeListener());
		mViewPager.setOnPageChangeListener(new PageChangeListener());
		mViewPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				v.getParent().requestDisallowInterceptTouchEvent(true);
				switch (event.getAction()) {

				case MotionEvent.ACTION_CANCEL:
					break;

				case MotionEvent.ACTION_UP:
					// calls when touch release on ViewPager
					if (products != null && products.size() != 0) {
						stopSliding = false;
						runnable(products.size());
						handler.postDelayed(animateViewPager,
								ANIM_VIEWPAGER_DELAY_USER_VIEW);
					}
					break;

				case MotionEvent.ACTION_MOVE:
					// calls when ViewPager touch
					if (handler != null && stopSliding == false) {
						stopSliding = true;
						handler.removeCallbacks(animateViewPager);
					}
					break;
				}
				return false;
			}
		});

		return view;
	}

	private void findViewById(View view) {
		mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
		mIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
		imgNameTxt = (TextView) view.findViewById(R.id.img_name);
		priceTxt = (TextView) view.findViewById(R.id.price);
	}

	public void runnable(final int size) {
		handler = new Handler();
		animateViewPager = new Runnable() {
			public void run() {
				if (!stopSliding) {
					if (mViewPager.getCurrentItem() == size - 1) {
						mViewPager.setCurrentItem(0);
					} else {
						mViewPager.setCurrentItem(
								mViewPager.getCurrentItem() + 1, true);
					}
					handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
				}
			}
		};
	}


	@Override
	public void onResume() {
		if (products == null) {
			sendRequest();
		} else {
			mViewPager.setAdapter(new ImageSlideAdapter(activity, products,
					BookFragment.this));

			mIndicator.setViewPager(mViewPager);
			imgNameTxt.setText(""
					+ ((Product) products.get(mViewPager.getCurrentItem()))
							.getName());
			priceTxt.setText(""
									+ ((Product) products.get(mViewPager.getCurrentItem()))
											.getPrice());
			runnable(products.size());
			//Re-run callback
			handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
		}
		super.onResume();
	}

	
	@Override
	public void onPause() {
		if (task != null)
			task.cancel(true);
		if (handler != null) {
			//Remove callback
			handler.removeCallbacks(animateViewPager);
		}
		super.onPause();
	}

	private void sendRequest() {
		if (CheckNetworkConnection.isConnectionAvailable(activity)) {
			task = new RequestImgTask(activity);
			task.execute(url);
		} else {
			message = getResources().getString(R.string.no_internet_connection);
			showAlertDialog(message, true);
		}
	}

	public void showAlertDialog(String message, final boolean finish) {
		alertDialog = new AlertDialog.Builder(activity).create();
		alertDialog.setMessage(message);
		alertDialog.setCancelable(false);

		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						if (finish)
							activity.finish();
					}
				});
		alertDialog.show();
	}

	private class PageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int state) {
			if (state == ViewPager.SCROLL_STATE_IDLE) {
				if (products != null) {
					imgNameTxt.setText(""
							+ ((Product) products.get(mViewPager
									.getCurrentItem())).getName());
					priceTxt.setText(""
							+ ((Product) products.get(mViewPager
									.getCurrentItem())).getPrice());
				}
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int arg0) {
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	private class RequestImgTask extends AsyncTask<String, Void, List<Product>> {
		private final WeakReference<Activity> activityWeakRef;
		Throwable error;

		public RequestImgTask(Activity context) {
			this.activityWeakRef = new WeakReference<Activity>(context);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected List<Product> doInBackground(String... urls) {
			try {
				JSONObject jsonObject = JSONfunctions.getJSONfromURL(url);
				System.out.println("hh"+jsonObject);
				if (jsonObject != null) {
					
							products = com.gbrf.JsonReader.getHome(jsonObject);

					
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return products;
		}

		/**
		 * It returns jsonObject for the specified url.
		 * 
		 * @param url
		 * @return JSONObject
		 */
		public JSONObject getJsonObject(String url) {
			JSONObject jsonObject = null;
			try {
				jsonObject = GetJSONObject.getJSONObject(url);
			} catch (Exception e) {
			}
			return jsonObject;
		}

		@Override
		protected void onPostExecute(List<Product> result) {
			super.onPostExecute(result);

			if (activityWeakRef != null && !activityWeakRef.get().isFinishing()) {
				if (error != null && error instanceof IOException) {
					message = getResources().getString(R.string.time_out);
					showAlertDialog(message, true);
				} else if (error != null) {
					message = getResources().getString(R.string.error_occured);
					showAlertDialog(message, true);
				} else {
					products = result;
					if (result != null) {
						if (products != null && products.size() != 0) {

							mViewPager.setAdapter(new ImageSlideAdapter(
									activity, products, BookFragment.this));

							mIndicator.setViewPager(mViewPager);
							imgNameTxt.setText(""
									+ ((Product) products.get(mViewPager
											.getCurrentItem())).getName());
							priceTxt.setText(""
									+ ((Product) products.get(mViewPager
											.getCurrentItem())).getPrice());
							runnable(products.size());
							handler.postDelayed(animateViewPager,
									ANIM_VIEWPAGER_DELAY);
						} else {
							imgNameTxt.setText("No Products");
						}
					} else {
					}
				}
			}
		}
	}
}
