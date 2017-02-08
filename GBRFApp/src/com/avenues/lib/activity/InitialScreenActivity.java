package com.avenues.lib.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.avenues.lib.utility.AvenuesParams;
import com.avenues.lib.utility.ServiceUtility;
import com.gbrf.R;

public class InitialScreenActivity extends Activity {
	
	private EditText accessCode, merchantId, currency, amount, orderId, rsaKeyUrl, redirectUrl, cancelUrl;
	private AutoCompleteTextView name,email,phone,address,country,state,city,zipcode;
	private void init(){
	    SharedPreferences preferences = PreferenceManager
	            .getDefaultSharedPreferences(InitialScreenActivity.this);
	    String mname = preferences.getString("Name","Test");
	    String memail = preferences.getString("Email","Test");
		accessCode = (EditText) findViewById(R.id.accessCode);
		merchantId = (EditText) findViewById(R.id.merchantId);
		orderId  = (EditText) findViewById(R.id.orderId);
		currency = (EditText) findViewById(R.id.currency);
		amount = (EditText) findViewById(R.id.amount);
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
		
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_initial_screen);
		init();
		
		//generating order number
		Integer randomNum = ServiceUtility.randInt(0, 9999999);
		orderId.setText(randomNum.toString());
	}

	public void onClick(View view) {
		//Mandatory parameters. Other parameters can be added if required.
		String vAccessCode = ServiceUtility.chkNull(accessCode.getText()).toString().trim();
		String vMerchantId = ServiceUtility.chkNull(merchantId.getText()).toString().trim();
		String vCurrency = ServiceUtility.chkNull(currency.getText()).toString().trim();
		String vAmount = ServiceUtility.chkNull(amount.getText()).toString().trim();
		if(!vAccessCode.equals("") && !vMerchantId.equals("") && !vCurrency.equals("") && !vAmount.equals("")){
			Intent intent = new Intent(this,WebViewActivity.class);
			intent.putExtra(AvenuesParams.ACCESS_CODE, ServiceUtility.chkNull(accessCode.getText()).toString().trim());
			intent.putExtra(AvenuesParams.MERCHANT_ID, ServiceUtility.chkNull(merchantId.getText()).toString().trim());
			intent.putExtra(AvenuesParams.ORDER_ID, ServiceUtility.chkNull(orderId.getText()).toString().trim());
			intent.putExtra(AvenuesParams.CURRENCY, ServiceUtility.chkNull(currency.getText()).toString().trim());
			intent.putExtra(AvenuesParams.AMOUNT, ServiceUtility.chkNull(amount.getText()).toString().trim());
			
			intent.putExtra(AvenuesParams.REDIRECT_URL, ServiceUtility.chkNull(redirectUrl.getText()).toString().trim());
			intent.putExtra(AvenuesParams.CANCEL_URL, ServiceUtility.chkNull(cancelUrl.getText()).toString().trim());
			intent.putExtra(AvenuesParams.RSA_KEY_URL, ServiceUtility.chkNull(rsaKeyUrl.getText()).toString().trim());
			
			intent.putExtra(AvenuesParams.BILLING_NAME, ServiceUtility.chkNull(name.getText()).toString().trim());
			intent.putExtra(AvenuesParams.BILLING_ADDRESS, ServiceUtility.chkNull(address.getText()).toString().trim());
			intent.putExtra(AvenuesParams.BILLING_COUNTRY, ServiceUtility.chkNull(country.getText()).toString().trim());
			intent.putExtra(AvenuesParams.BILLING_STATE, ServiceUtility.chkNull(state.getText()).toString().trim());
			intent.putExtra(AvenuesParams.BILLING_CITY, ServiceUtility.chkNull(city.getText()).toString().trim());
			intent.putExtra(AvenuesParams.BILLING_ZIP, ServiceUtility.chkNull(zipcode.getText()).toString().trim());
			intent.putExtra(AvenuesParams.BILLING_TEL, ServiceUtility.chkNull(phone.getText()).toString().trim());
			intent.putExtra(AvenuesParams.BILLING_EMAIL, ServiceUtility.chkNull(email.getText()).toString().trim());
			
			startActivity(intent);
		}else{
			showToast("All parameters are mandatory.");
		}
	}
	
	public void showToast(String msg) {
		Toast.makeText(this, "Toast: " + msg, Toast.LENGTH_LONG).show();
	}
} 