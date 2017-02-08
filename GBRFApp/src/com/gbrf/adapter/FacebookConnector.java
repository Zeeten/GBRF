package com.gbrf.adapter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.gbrf.adapter.SessionEvents.AuthListener;
import com.gbrf.adapter.SessionEvents.LogoutListener;

public class FacebookConnector {

	private Facebook facebook = null;
	private Context context;
	private String[] permissions;
	private Handler mHandler;
	private Activity activity;
	private SessionListener mSessionListener = new SessionListener();;

	public FacebookConnector(String appId, Activity activity, Context context,
			String[] permissions) {
		this.facebook = new Facebook(appId);

		SessionStore.restore(facebook, context);
		SessionEvents.addAuthListener(mSessionListener);
		SessionEvents.addLogoutListener(mSessionListener);

		this.context = context;
		this.permissions = permissions;
		this.mHandler = new Handler();
		this.activity = activity;
	}

	public void login() {
		if (!facebook.isSessionValid()) {
			facebook.authorize(this.activity, this.permissions,
					new LoginDialogListener());
		}
	}
	public void logout() {
        SessionEvents.onLogoutBegin();
        AsyncFacebookRunner asyncRunner = new AsyncFacebookRunner(this.facebook);
        asyncRunner.logout(this.context, new LogoutRequestListener());
	}
	

	public void postMessageOnWall(String msg,String uid) {
		if (facebook.isSessionValid()) {
			 byte[] data = null;
			 /*   Bitmap bi = BitmapFactory.decodeFile("http://www.theacademicworld.com/invitation/farmaan?uid=13&nid=12");
             ByteArrayOutputStream baos = new ByteArrayOutputStream();
             bi.compress(Bitmap.CompressFormat.JPEG, 100, baos);
             data = baos.toByteArray();*/
             
           
			Bundle parameters = new Bundle();
			//parameters.putString("message", msg);
			//parameters.putString("description","message here" );
			//parameters.putString("link", "https://play.google.com/store/apps/details?id=com.academicworld");
			//parameters.putString("picture", "http://www.theacademicworld.com/invitation/farmaan?uid="+uid);
			try {
				  String url1 = "http://www.theacademicworld.com/invitation/farmaan?uid="+uid;
				  System.out.println("url1"+url1);
		             URL ulrn = new URL(url1);
		             Bitmap bmp = BitmapFactory.decodeStream(ulrn.openConnection().getInputStream());
		             ByteArrayOutputStream baos = new ByteArrayOutputStream();
		             bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		             data = baos.toByteArray();
		             parameters.putString("message", "picture caption");
		             parameters.putByteArray("picture", data);
				String response = facebook.request("me/photos", parameters,
						"POST");
				System.out.println("ress"+response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			login();
		}
	}

	private final class LoginDialogListener implements DialogListener {
		public void onComplete(Bundle values) {
			SessionEvents.onLoginSuccess();
		}

		public void onFacebookError(FacebookError error) {
			SessionEvents.onLoginError(error.getMessage());
		}

		public void onError(DialogError error) {
			SessionEvents.onLoginError(error.getMessage());
		}

		public void onCancel() {
			SessionEvents.onLoginError("Action Canceled");
		}
	}
    public class LogoutRequestListener extends BaseRequestListener {
        public void onComplete(String response, final Object state) {
            // callback should be run in the original thread, 
            // not the background thread
            mHandler.post(new Runnable() {
                public void run() {
                    SessionEvents.onLogoutFinish();
                }
            });
        }
    }
	private class SessionListener implements AuthListener, LogoutListener {

		public void onAuthSucceed() {
			SessionStore.save(facebook, context);
		}

		public void onAuthFail(String error) {
		}

		public void onLogoutBegin() {
		}

		public void onLogoutFinish() {
			SessionStore.clear(context);
		}
	}

	public Facebook getFacebook() {
		return this.facebook;
	}
}
