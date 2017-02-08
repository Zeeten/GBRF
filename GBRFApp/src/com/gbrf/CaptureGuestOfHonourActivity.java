package com.gbrf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gbrf.adapter.FacebookConnector;
import com.gbrf.adapter.SessionEvents;

public class CaptureGuestOfHonourActivity extends ActionBarActivity {

	ImageView imageViewCapture;
	Bitmap bitmap;
	private static final String FACEBOOK_APPID = "888751001205538";
	private static final String FACEBOOK_PERMISSION = "publish_actions";
	private static final String TAG = "FacebookSample";
	private static final String MSG = "Message from FacebookSample";
	
	private final Handler mFacebookHandler = new Handler();
	private FacebookConnector facebookConnector;
	TextView txtname;
	TextView txttime;
	String name;
	String time;
	String muId;
	
    final Runnable mUpdateFacebookNotification = new Runnable() {
        public void run() {
        	Toast.makeText(getBaseContext(), "Facebook updated !", Toast.LENGTH_LONG).show();
        }
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.capture_guest_of_honour);
		   this.facebookConnector = new FacebookConnector(FACEBOOK_APPID, this, getApplicationContext(), new String[] {FACEBOOK_PERMISSION});
			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(this);
			name = preferences.getString("Name", "Test");
			time = preferences.getString("release_on", "Test");
			time.replace("\\s+", " at ");
		   txtname = (TextView) findViewById(R.id.guestname);
		   txtname.setText(name);
		   txttime = (TextView) findViewById(R.id.guesttime);
		   txttime.setText("On "+time);
		    muId = preferences.getString("UID", "Test");
		imageViewCapture = (ImageView) findViewById(R.id.ImageViewCapture);
		 ImageView tweet = (ImageView) findViewById(R.id.FbshareImage);
		imageViewCapture.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				bitmap = getBitmapOfView(imageViewCapture);
				// imageViewPreview.setImageBitmap(bitmap);
				createImageFromBitmap(bitmap);
			}
		});
	     tweet.setOnClickListener(new View.OnClickListener() {
	        	/**
	        	 * Send a tweet. If the user hasn't authenticated to Tweeter yet, he'll be redirected via a browser
	        	 * to the twitter login page. Once the user authenticated, he'll authorize the Android application to send
	        	 * tweets on the users behalf.
	        	 */
	            public void onClick(View v) {
	        		postMessage();
	            }
	        });
		
	}

	public Bitmap getBitmapOfView(View v) {
		View rootview = v.getRootView();
		rootview.setDrawingCacheEnabled(true);
		Bitmap bmp = rootview.getDrawingCache();
		return bmp;
	}

	public void createImageFromBitmap(Bitmap bmp) {
		Date now = new Date();
		android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
		File file = new File(Environment.getExternalStorageDirectory() + "/"
				+"farmaan.jpg");
		try {
			file.createNewFile();
			FileOutputStream ostream = new FileOutputStream(file);
			ostream.write(bytes.toByteArray());
			ostream.close();
			Toast.makeText(getApplicationContext(), "Thank you",
					Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.capture_guest_of_honour, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private String getFacebookMsg() {
		return MSG + " at " + new Date().toLocaleString();
	}	
	public void postMessage() {
		
		if (facebookConnector.getFacebook().isSessionValid()) {
			postMessageInThread();
		} else {
			SessionEvents.AuthListener listener = new SessionEvents.AuthListener() {
				
				@Override
				public void onAuthSucceed() {
					postMessageInThread();
				}
				
				@Override
				public void onAuthFail(String error) {
					
				}
			};
			SessionEvents.addAuthListener(listener);
			facebookConnector.login();
		}
	}
	private void postMessageInThread() {
		Thread t = new Thread() {
			public void run() {
		    	
		    	try {
		    		facebookConnector.postMessageOnWall(getFacebookMsg(),muId);
					mFacebookHandler.post(mUpdateFacebookNotification);
				} catch (Exception ex) {
					Log.e(TAG, "Error sending msg",ex);
				}
		    }
		};
		t.start();
	}
}
