package com.avenues.lib.activity;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.avenues.lib.utility.AvenuesParams;
import com.avenues.lib.utility.Constants;
import com.avenues.lib.utility.RSAUtility;
import com.avenues.lib.utility.ServiceUtility;
import com.gbrf.BookDetailActivity;
import com.gbrf.DataSyncService;
import com.gbrf.OrderHistoryActivity;
import com.gbrf.R;
import com.gbrf.dto.UserDTO;

public class WebViewActivity extends Activity {
	private ProgressDialog dialog;
	Intent mainIntent;
	String html, encVal;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_webview);
		mainIntent = getIntent();

		// Calling async task to get display content
		new RenderView().execute();
	}

	/**
	 * Async task class to get json by making HTTP call
	 * */
	private class RenderView extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			dialog = new ProgressDialog(WebViewActivity.this);
			dialog.setMessage("Please wait...");
			dialog.setCancelable(false);
			dialog.show();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			DataSyncService sh = new DataSyncService();

			// Making a request to url and getting response
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(AvenuesParams.ACCESS_CODE,
					mainIntent.getStringExtra(AvenuesParams.ACCESS_CODE)));
			params.add(new BasicNameValuePair(AvenuesParams.ORDER_ID,
					mainIntent.getStringExtra(AvenuesParams.ORDER_ID)));

			UserDTO dto = sh.getrsakey(
					mainIntent.getStringExtra(AvenuesParams.RSA_KEY_URL),
					mainIntent.getStringExtra(AvenuesParams.ORDER_ID));
			String vResponse = dto.getRsaKey();
			System.out.println(vResponse);
			if (!ServiceUtility.chkNull(vResponse).equals("")
					&& ServiceUtility.chkNull(vResponse).toString()
							.indexOf("ERROR") == -1) {
				StringBuffer vEncVal = new StringBuffer("");
				vEncVal.append(ServiceUtility.addToPostParams(
						AvenuesParams.AMOUNT,
						mainIntent.getStringExtra(AvenuesParams.AMOUNT)));
				vEncVal.append(ServiceUtility.addToPostParams(
						AvenuesParams.CURRENCY,
						mainIntent.getStringExtra(AvenuesParams.CURRENCY)));
				encVal = RSAUtility.encrypt(
						vEncVal.substring(0, vEncVal.length() - 1), vResponse);
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (dialog.isShowing())
				dialog.dismiss();

			@SuppressWarnings("unused")
			class MyJavaScriptInterface {
				@JavascriptInterface
				public void processHTML(String html) {
					// process the html as needed by the app
					String status = null;
					if (html.indexOf("Failure") != -1) {
						status = "Transaction Declined!";
					} else if (html.indexOf("Success") != -1) {
						status = "Transaction Successful!";
					} else if (html.indexOf("Aborted") != -1) {
						status = "Transaction Cancelled!";
					} else {
						status = "Transaction Cancelled!";
					}
					// Toast.makeText(getApplicationContext(), status,
					// Toast.LENGTH_SHORT).show();
					if(status.equalsIgnoreCase("Transaction Successful!")){
						AlertDialog.Builder alert = new AlertDialog.Builder(WebViewActivity.this);
						alert.setMessage(status);
						alert.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {
										Intent homeIntent = new Intent(
												getApplicationContext(),
												OrderHistoryActivity.class);
										startActivity(homeIntent);

										finish();
									}
								});
						alert.show();
					}else{
						AlertDialog.Builder alert = new AlertDialog.Builder(WebViewActivity.this);
						alert.setMessage(status);
						alert.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {
										Intent homeIntent = new Intent(
												getApplicationContext(),
												BookDetailActivity.class);
										startActivity(homeIntent);

										finish();
									}
								});
						alert.show();
					}

				}
			}

			final WebView webview = (WebView) findViewById(R.id.webview);
			webview.getSettings().setJavaScriptEnabled(true);
			webview.addJavascriptInterface(new MyJavaScriptInterface(),
					"HTMLOUT");
			webview.setWebViewClient(new WebViewClient() {
				@Override
				public void onPageFinished(WebView view, String url) {
					super.onPageFinished(webview, url);
					if (url.indexOf("/response?device=android") != -1) {
						webview.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
					}
				}

				@Override
				public void onReceivedError(WebView view, int errorCode,
						String description, String failingUrl) {
					Toast.makeText(getApplicationContext(),
							"Oh no! " + description, Toast.LENGTH_SHORT).show();
				}
			});

			/*
			 * An instance of this class will be registered as a JavaScript
			 * interface
			 */
			StringBuffer params = new StringBuffer();
			params.append(ServiceUtility.addToPostParams(
					AvenuesParams.ACCESS_CODE,
					mainIntent.getStringExtra(AvenuesParams.ACCESS_CODE)));
			params.append(ServiceUtility.addToPostParams(
					AvenuesParams.MERCHANT_ID,
					mainIntent.getStringExtra(AvenuesParams.MERCHANT_ID)));
			params.append(ServiceUtility.addToPostParams(
					AvenuesParams.ORDER_ID,
					mainIntent.getStringExtra(AvenuesParams.ORDER_ID)));
			params.append(ServiceUtility.addToPostParams(
					AvenuesParams.REDIRECT_URL,
					mainIntent.getStringExtra(AvenuesParams.REDIRECT_URL)));
			params.append(ServiceUtility.addToPostParams(
					AvenuesParams.CANCEL_URL,
					mainIntent.getStringExtra(AvenuesParams.CANCEL_URL)));
			params.append(ServiceUtility.addToPostParams(AvenuesParams.ENC_VAL,
					URLEncoder.encode(encVal)));

			String vPostParams = params.substring(0, params.length() - 1);
			try {
				webview.postUrl(Constants.TRANS_URL,
						EncodingUtils.getBytes(vPostParams, "UTF-8"));
			} catch (Exception e) {
				showToast("Exception occured while opening webview.");
			}
		}
	}

	public void showToast(String msg) {
		Toast.makeText(this, "Toast: " + msg, Toast.LENGTH_LONG).show();
	}
}