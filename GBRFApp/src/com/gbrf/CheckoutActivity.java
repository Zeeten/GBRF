package com.gbrf;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avenues.lib.activity.WebViewActivity;
import com.avenues.lib.utility.AvenuesParams;
import com.avenues.lib.utility.ServiceUtility;
import com.facebook.Session;
import com.gbrf.dto.UserDTO;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

@SuppressLint("NewApi")
public class CheckoutActivity extends ActionBarActivity implements
		LoaderCallbacks<Cursor> {

	String bookname;
	String price;
	String image;
	Button pdtaddCartbtn;
	private String muId,msid,mssid;
	private String nid, currencycode, currencySymbol;
	private String orderId;
	private String status = "success";
	private String gateway = "internal";
	private String response = "success";
	private int instock;
	ImageLoader imageLoader = new ImageLoader(this);
	private EditText accessCode, merchantId, currency, rsaKeyUrl, redirectUrl,
			cancelUrl;
	private AutoCompleteTextView name, email, phone, address, country, state,
			city, zipcode;
	private UserCheckoutTask mCheckoutTask = null;
	private UserPayPalTask mPayPalTask = null;
	private UserPayPalResponse mPayPalResponse = null;
	private UserFreeBookTask mFreeBookTask = null;
	private static final int REQUEST_CODE_PAYMENT = 1;
	private static final String TAG = "paymentExample";
	private String account;
	private UserLogoutTask mLogoutTask = null;
	private View mProgressView;

	// PayPal configuration
	private static PayPalConfiguration paypalConfig = new PayPalConfiguration()
			.environment(Config.PAYPAL_ENVIRONMENT).clientId(
					Config.PAYPAL_CLIENT_ID);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkout);
		ActionBar actionBar = getSupportActionBar();
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		actionBar.setTitle("Global Book Store");
		actionBar.setDisplayUseLogoEnabled(true);
		getSupportActionBar().setIcon(R.drawable.logo);
		Intent i = getIntent();
		// Get the result of rank
		bookname = i.getStringExtra("bookname");
		// Get the result of population
		price = i.getStringExtra("price");
		// Get the result of flag
		image = i.getStringExtra("image");
		instock = Integer.parseInt(i.getStringExtra("in_stock"));
		System.out.println("instock" + instock);

		// Locate the TextViews in singleitemview.xml
		TextView txtbookname = (TextView) findViewById(R.id.bookinfo);
		TextView txtprice = (TextView) findViewById(R.id.priceinfo);

		// Locate the ImageView in singleitemview.xml
		ImageView txtimage = (ImageView) findViewById(R.id.product_detail_img);

		// Set results to the TextViews
		txtbookname.setText(bookname);
		txtprice.setText(price);
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		account = preferences.getString("account", "Test");
		String mname = preferences.getString("Name", "Test");
		String memail = preferences.getString("Email", "Test");
		muId = preferences.getString("UID", "Test");
		msid = preferences.getString("sid", "Test");
		mssid = preferences.getString("ssid", "Test");
		currencycode = preferences.getString("CurrencyCode", "Test");
		currencySymbol = preferences.getString("CurrencySymbol", "Test");
		accessCode = (EditText) findViewById(R.id.accessCode);
		merchantId = (EditText) findViewById(R.id.merchantId);
		// orderId = (EditText)findViewById(R.id.orderId);
		currency = (EditText) findViewById(R.id.currency);
		currency.setText(currencycode);
		TextView txtpricelable = (TextView) findViewById(R.id.pricelabel);
		txtpricelable.setText("Price:" + currencySymbol);
		// amount = (EditText) findViewById(R.id.amount);
		rsaKeyUrl = (EditText) findViewById(R.id.rsaUrl);
		redirectUrl = (EditText) findViewById(R.id.redirectUrl);
		cancelUrl = (EditText) findViewById(R.id.cancelUrl);
		name = (AutoCompleteTextView) findViewById(R.id.EnterName);
		name.setText(mname);
		email = (AutoCompleteTextView) findViewById(R.id.EnterEmail);
		email.setText(memail);
		phone = (AutoCompleteTextView) findViewById(R.id.EnterMobile);
		address = (AutoCompleteTextView) findViewById(R.id.EnterAddress);
		country = (AutoCompleteTextView) findViewById(R.id.EnterCountry);
		state = (AutoCompleteTextView) findViewById(R.id.EnterState);
		city = (AutoCompleteTextView) findViewById(R.id.EnterCity);
		zipcode = (AutoCompleteTextView) findViewById(R.id.EnterZipcode);
		nid = preferences.getString("nid", "Test");
		// generating order number
		/*
		 * Integer randomNum = ServiceUtility.randInt(0, 9999999);
		 * orderId.setText(randomNum.toString());
		 */
		//populateAutoComplete();
		// Starting PayPal service
		Intent intent = new Intent(this, PayPalService.class);
		intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig);
		startService(intent);
		pdtaddCartbtn = (Button) findViewById(R.id.checkout);
		pdtaddCartbtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (instock < 0) {
					AlertDialog.Builder alert = new AlertDialog.Builder(
							CheckoutActivity.this);
					alert.setMessage("Out Of Stock");
					alert.setPositiveButton("OK", null);
					alert.show();
				} else if (price.equalsIgnoreCase("0.00")) {
					String uid = muId;
					mFreeBookTask = new UserFreeBookTask(uid);
					mFreeBookTask.execute((Void) null);
				} else {
					if (currencycode.equalsIgnoreCase("INR")) {
						attemptPay();
					} else {
						mPayPalTask = new UserPayPalTask(muId);
						mPayPalTask.execute((Void) null);
					}

					/*
					 * final CharSequence[] paymentGatway = { "ccavenue",
					 * "paypal" }; AlertDialog.Builder alert = new
					 * AlertDialog.Builder( CheckoutActivity.this);
					 * alert.setTitle("Select Payment Method");
					 * alert.setSingleChoiceItems(paymentGatway, -1, new
					 * DialogInterface.OnClickListener() { public void
					 * onClick(DialogInterface dialog, int id) { if
					 * (paymentGatway[id] == "ccavenue")
					 * 
					 * { attemptPay();
					 * 
					 * }
					 * 
					 * else if (paymentGatway[id] == "paypal")
					 * 
					 * { launchPayPalPayment(); } } }); alert.show();
					 */
				}

			}
		});
		// Capture position and set results to the ImageView
		// Passes flag images URL into ImageLoader.class
		imageLoader.DisplayImage(image, txtimage);
		mProgressView = findViewById(R.id.logout_progress);

	}

	private void populateAutoComplete() {
		if (VERSION.SDK_INT >= 14) {
			// Use ContactsContract.Profile (API 14+)
			getLoaderManager().initLoader(0, null, this);
		} else if (VERSION.SDK_INT >= 8) {
			// Use AccountManager (API 8+)
			new SetupEmailAutoCompleteTask().execute(null, null);
		}
	}

	public void attemptPay() {
		String uid = muId;
		mCheckoutTask = new UserCheckoutTask(uid);
		mCheckoutTask.execute((Void) null);

	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserPayPalTask extends AsyncTask<Void, Void, Boolean> {

		private final String muid;

		UserPayPalTask(String uid) {
			muid = uid;

		}

		@Override
		protected Boolean doInBackground(Void... params) {

			DataSyncService service = new DataSyncService();
			try {
				UserDTO dto = service.orderinitaite(muid, price);
				((PE) getApplicationContext()).setUserDTO(dto);
				if (dto.getStatus().equals("success")) {
					orderId = dto.getOrderId();
					dto = service.orderupdate(dto.getOrderId(), nid, muid);
					((PE) getApplicationContext()).setUserDTO(dto);
					if (dto.getStatus().equals("success")) {

						return true;
					}
				}
			} catch (Exception e) {

				e.printStackTrace();

			}

			return false;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mPayPalTask = null;

			System.out.println(success);

			if (success) {
				launchPayPalPayment();

			} else {
				showToast("All parameters are mandatory.");
			}

		}

	}

	/**
	 * Launching PalPay payment activity to complete the payment
	 * */
	private void launchPayPalPayment() {

		PayPalPayment thingsToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE);

		/*
		 * See getStuffToBuy(..) for examples of some available payment options.
		 */

		Intent intent = new Intent(CheckoutActivity.this, PaymentActivity.class);

		// send the same configuration for restart resiliency
		intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig);

		intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingsToBuy);

		startActivityForResult(intent, REQUEST_CODE_PAYMENT);
	}

	private PayPalPayment getThingToBuy(String paymentIntent) {
		return new PayPalPayment(new BigDecimal(price), currencycode,
				"sample item", paymentIntent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_PAYMENT) {
			if (resultCode == Activity.RESULT_OK) {
				PaymentConfirmation confirm = data
						.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
				if (confirm != null) {
					try {

						System.out.println("muId" + muId);
						System.out.println("oiii"
								+ ((PE) getApplicationContext()).getUserDTO()
										.getOrderId());
						mPayPalResponse = new UserPayPalResponse(muId,
								((PE) getApplicationContext()).getUserDTO()
										.getOrderId(), "Success", "PayPal",
								response);
						mPayPalResponse.execute((Void) null);

					} catch (Exception e) {
						Log.e(TAG, "an extremely unlikely failure occurred: ",
								e);
					}
				}
			} else if (resultCode == Activity.RESULT_CANCELED) {
				mPayPalResponse = new UserPayPalResponse(muId,
						((PE) getApplicationContext()).getUserDTO()
								.getOrderId().toString(), "Aborted", "PayPal",
						"Aborted");
				mPayPalResponse.execute((Void) null);

			} else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
				mPayPalResponse = new UserPayPalResponse(muId,
						((PE) getApplicationContext()).getUserDTO()
								.getOrderId().toString(), "Invalid", "PayPal",
						"Invalid");
				mPayPalResponse.execute((Void) null);

			}
		}
	}

	public class UserPayPalResponse extends AsyncTask<Void, Void, Boolean> {

		private final String moid;
		private final String muid;
		private final String mstatus;
		private final String mgateway;
		private final String mresponse;

		UserPayPalResponse(String uid, String oid, String status,
				String gateway, String response) {
			muid = uid;
			moid = oid;
			mstatus = status;
			mgateway = gateway;
			mresponse = response;
		}

		@Override
		protected Boolean doInBackground(Void... params) {

			DataSyncService service = new DataSyncService();
			try {
				UserDTO userdto = service.orderupdatestatus(moid, muid,
						mstatus, mgateway, mresponse);

				if (userdto.getStatus().equals("success")) {

					return true;
				}

			} catch (Exception e) {

				e.printStackTrace();

			}

			return false;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mPayPalResponse = null;

			System.out.println(success);

			if (success) {

				AlertDialog.Builder alert = new AlertDialog.Builder(
						CheckoutActivity.this);
				alert.setMessage("successfully translation");
				alert.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent homeIntent = new Intent(
										CheckoutActivity.this,
										OrderHistoryActivity.class);
								startActivity(homeIntent);

							}
						});
				alert.show();

			}

		}

	}
	
	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mProgressView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mProgressView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);

		}
	}

	@SuppressLint("NewApi")
	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		return new CursorLoader(this,
				// Retrieve data rows for the device user's 'profile' contact.
				Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
						ContactsContract.Contacts.Data.CONTENT_DIRECTORY),
				ProfileQuery.PROJECTION,

				// Select only email addresses.
				ContactsContract.Contacts.Data.MIMETYPE + " = ?",
				new String[] { ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE },

				// Show primary email addresses first. Note that there won't be
				// a primary email address if the user hasn't specified one.
				ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
	}

	public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
		List<String> emails = new ArrayList<String>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			emails.add(cursor.getString(ProfileQuery.ADDRESS));
			cursor.moveToNext();
		}

		addEmailsToAutoComplete(emails);
	}

	public void onLoaderReset(Loader<Cursor> cursorLoader) {

	}

	private interface ProfileQuery {
		String[] PROJECTION = { ContactsContract.CommonDataKinds.Email.ADDRESS,
				ContactsContract.CommonDataKinds.Email.IS_PRIMARY, };

		int ADDRESS = 0;
		int IS_PRIMARY = 1;
	}

	/**
	 * Use an AsyncTask to fetch the user's email addresses on a background
	 * thread, and update the email text field with results on the main UI
	 * thread.
	 */
	class SetupEmailAutoCompleteTask extends
			AsyncTask<Void, Void, List<String>> {

		@Override
		protected List<String> doInBackground(Void... voids) {
			ArrayList<String> emailAddressCollection = new ArrayList<String>();

			// Get all emails from the user's contacts and copy them to a list.
			ContentResolver cr = getContentResolver();
			Cursor emailCur = cr.query(
					ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
					null, null, null);
			while (emailCur.moveToNext()) {
				String email = emailCur
						.getString(emailCur
								.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
				emailAddressCollection.add(email);
			}
			emailCur.close();
			return emailAddressCollection;
		}

		@Override
		protected void onPostExecute(List<String> emailAddressCollection) {
			addEmailsToAutoComplete(emailAddressCollection);
		}
	}

	private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
		// Create adapter to tell the AutoCompleteTextView what to show in its
		// dropdown list.
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				CheckoutActivity.this,
				android.R.layout.simple_dropdown_item_1line,
				emailAddressCollection);

	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserCheckoutTask extends AsyncTask<Void, Void, Boolean> {

		private final String muid;

		UserCheckoutTask(String uid) {
			muid = uid;

		}

		@Override
		protected Boolean doInBackground(Void... params) {

			DataSyncService service = new DataSyncService();
			try {
				UserDTO dto = service.orderinitaite(muid, price);
				((PE) getApplicationContext()).setUserDTO(dto);
				if (dto.getStatus().equals("success")) {
					orderId = dto.getOrderId();
					dto = service.orderupdate(dto.getOrderId(), nid, muid);
					((PE) getApplicationContext()).setUserDTO(dto);
					if (dto.getStatus().equals("success")) {

						return true;
					}
				}
			} catch (Exception e) {

				e.printStackTrace();

			}

			return false;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mCheckoutTask = null;

			System.out.println(success);

			if (success) {

				// Mandatory parameters. Other parameters can be added if
				// required.
				String vAccessCode = ServiceUtility
						.chkNull(accessCode.getText()).toString().trim();
				String vMerchantId = ServiceUtility
						.chkNull(merchantId.getText()).toString().trim();
				String vCurrency = ServiceUtility.chkNull(currency.getText())
						.toString().trim();
				String vAmount = ServiceUtility.chkNull(price).toString()
						.trim();
				if (!vAccessCode.equals("") && !vMerchantId.equals("")
						&& !vCurrency.equals("") && !vAmount.equals("")) {
					Intent intent = new Intent(CheckoutActivity.this,
							WebViewActivity.class);
					intent.putExtra(AvenuesParams.ACCESS_CODE, ServiceUtility
							.chkNull(accessCode.getText()).toString().trim());
					intent.putExtra(AvenuesParams.MERCHANT_ID, ServiceUtility
							.chkNull(merchantId.getText()).toString().trim());
					intent.putExtra(AvenuesParams.ORDER_ID, ServiceUtility
							.chkNull(orderId).toString().trim());
					intent.putExtra(AvenuesParams.CURRENCY, ServiceUtility
							.chkNull(currency.getText()).toString().trim());
					intent.putExtra(AvenuesParams.AMOUNT, ServiceUtility
							.chkNull(price).toString().trim());

					intent.putExtra(AvenuesParams.REDIRECT_URL, ServiceUtility
							.chkNull(redirectUrl.getText()).toString().trim());
					intent.putExtra(AvenuesParams.CANCEL_URL, ServiceUtility
							.chkNull(cancelUrl.getText()).toString().trim());
					intent.putExtra(AvenuesParams.RSA_KEY_URL, ServiceUtility
							.chkNull(rsaKeyUrl.getText()).toString().trim());

					intent.putExtra(AvenuesParams.BILLING_NAME, ServiceUtility
							.chkNull(name.getText()).toString().trim());
					intent.putExtra(AvenuesParams.BILLING_ADDRESS,
							ServiceUtility.chkNull(address.getText())
									.toString().trim());
					intent.putExtra(AvenuesParams.BILLING_COUNTRY,
							ServiceUtility.chkNull(country.getText())
									.toString().trim());
					intent.putExtra(AvenuesParams.BILLING_STATE, ServiceUtility
							.chkNull(state.getText()).toString().trim());
					intent.putExtra(AvenuesParams.BILLING_CITY, ServiceUtility
							.chkNull(city.getText()).toString().trim());
					intent.putExtra(AvenuesParams.BILLING_ZIP, ServiceUtility
							.chkNull(zipcode.getText()).toString().trim());
					intent.putExtra(AvenuesParams.BILLING_TEL, ServiceUtility
							.chkNull(phone.getText()).toString().trim());
					intent.putExtra(AvenuesParams.BILLING_EMAIL, ServiceUtility
							.chkNull(email.getText()).toString().trim());

					startActivity(intent);
				} else {
					showToast("All parameters are mandatory.");
				}

			}

		}

	}

	protected void onCancelled() {
		mCheckoutTask = null;

	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserFreeBookTask extends AsyncTask<Void, Void, Boolean> {

		private final String muid;

		UserFreeBookTask(String uid) {
			muid = uid;

		}

		@Override
		protected Boolean doInBackground(Void... params) {

			DataSyncService service = new DataSyncService();
			try {
				UserDTO dto = service.orderinitaite(muid, price);
				((PE) getApplicationContext()).setUserDTO(dto);
				if (dto.getStatus().equals("success")) {
					orderId = dto.getOrderId();
					dto = service.orderupdate(orderId, nid, muid);
					((PE) getApplicationContext()).setUserDTO(dto);
					if (dto.getStatus().equals("success")) {
						UserDTO userdto = service.orderupdatestatus(orderId,
								muid, status, gateway, response);
						((PE) getApplicationContext()).setUserDTO(userdto);

						if (userdto.getStatus().equals("success")) {

							return true;
						}

					}
				}
			} catch (Exception e) {

				e.printStackTrace();

			}

			return false;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mFreeBookTask = null;

			System.out.println(success);

			if (success) {

				AlertDialog.Builder alert = new AlertDialog.Builder(
						CheckoutActivity.this);
				alert.setMessage("Successfull");
				alert.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent homeIntent = new Intent(
										CheckoutActivity.this,
										OrderHistoryActivity.class);
								startActivity(homeIntent);

							}
						});
				alert.show();

			} else {
				AlertDialog.Builder alert = new AlertDialog.Builder(
						CheckoutActivity.this);
				alert.setMessage(((PE) getApplicationContext()).getUserDTO()
						.getMessage().toString());
				alert.setPositiveButton("OK", null);
				alert.show();
			}

		}

	}
	
	public class UserLogoutTask extends AsyncTask<Void, Void, Boolean> {

		private final String muid;
		private final String msid;
		private final String mssid;

		UserLogoutTask(String uid, String sid, String ssid) {
			muid = uid;
			msid = sid;
			mssid = ssid;

		}

		@Override
		protected Boolean doInBackground(Void... params) {

			DataSyncService service = new DataSyncService();
			try {
				UserDTO dto = service.logout(muid, msid, mssid);
				((PE) getApplicationContext()).setUserDTO(dto);
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
			mLogoutTask = null;

			if (success) {
				SharedPreferences SM = getSharedPreferences("userrecord", 0);
				Editor edit = SM.edit();
				edit.putBoolean("userlogin", false);
				edit.commit();
				Intent homeIntent = new Intent(CheckoutActivity.this,
						LoginActivity.class);
				startActivity(homeIntent);
				finish();

			} else {

				Intent homeIntent = new Intent(CheckoutActivity.this,
						CheckoutActivity.class);
				startActivity(homeIntent);

			}
			showProgress(false);

		}

		protected void onCancelled() {
			mLogoutTask = null;
			showProgress(false);

		}
	}

	public void showToast(String msg) {
		Toast.makeText(CheckoutActivity.this, "Toast: " + msg,
				Toast.LENGTH_LONG).show();
	}

	private void savePreferences(String key, boolean value) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor edit = sp.edit();
		edit.putBoolean(key, value);
		edit.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_mybook) {
			Intent homeIntent = new Intent(this.getApplicationContext(),
					OrderHistoryActivity.class);
			startActivity(homeIntent);
			return true;

		}
		// noinspection SimplifiableIfStatement
		if (id == R.id.action_logout) {
			System.out.println("logout");
			if (account.equalsIgnoreCase("taw")) {
				attemptLogout();
				// return true;
			} else if (account.equalsIgnoreCase("facebook")) {
				System.out.println("if facebook condition");
				logoutFromFacebook(getApplicationContext());
				// return true;
			}

		}

		return super.onOptionsItemSelected(item);
	}
	
	public void attemptLogout() {
		if (mLogoutTask != null) {
			return;
		}

		String uid = muId;
		String sid = msid;
		String ssid = mssid;

		boolean cancel = false;
		View focusView = null;

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			showProgress(true);
			mLogoutTask = new UserLogoutTask(uid, sid, ssid);
			mLogoutTask.execute((Void) null);
		}
	}

	/**
	 * Function to Logout user from Facebook
	 * */
	public void logoutFromFacebook(Context context) {

		Session session = Session.getActiveSession();
		if (session != null) {

			if (!session.isClosed()) {
				session.closeAndClearTokenInformation();
				attemptLogout();
			}
		} else {
			session = new Session(context);
			Session.setActiveSession(session);

			session.closeAndClearTokenInformation();
			// clear your preferences if saved

		}
	}



}
