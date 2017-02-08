package com.gbrf;

import java.util.List;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.gbrf.dto.CartDTO;
import com.gbrf.dto.UserDTO;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class PE extends Application {
	
	public static boolean DEBUG=false;

	
	private UserDTO userDTO;
	private CartDTO cartDTO;

	private List list;

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}
	public CartDTO getCartDTO() {
		return cartDTO;
	}

	public void setCartDTO(CartDTO cartDTO) {
		this.cartDTO = cartDTO;
	}
	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
	public static final String TAG = PE.class.getSimpleName();

	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;

	private static PE mInstance;



	public static synchronized PE getInstance() {
		return mInstance;
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}

	public ImageLoader getImageLoader() {
		getRequestQueue();
		if (mImageLoader == null) {
			mImageLoader = new ImageLoader(this.mRequestQueue,
					new LruBitmapCache());
		}
		return this.mImageLoader;
	}

	public <T> void addToRequestQueue(com.android.volley.Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(com.android.volley.Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}
	public static final boolean DEVELOPER_MODE = false;
	View footerView;

	@SuppressWarnings("unused")
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		if (DEVELOPER_MODE
				&& Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectAll().penaltyDialog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
					.detectAll().penaltyDeath().build());
		}

		initImageLoader(getApplicationContext());
	}

	public static void initImageLoader(Context context) {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_empty).resetViewBeforeLoading()
				.cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new FadeInBitmapDisplayer(300)).build();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.defaultDisplayImageOptions(options)
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();

		com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(config);
	}

}
