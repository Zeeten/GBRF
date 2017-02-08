package com.gbrf;

import android.app.Activity;
import android.os.Bundle;

import com.paypal.android.sdk.payments.PayPalConfiguration;

/**
 * Basic sample using the SDK to make a payment or consent to future payments.
 * 
 * For sample mobile backend interactions, see
 * https://github.com/paypal/rest-api-sdk-python/tree/master/samples/mobile_backend
 */
public class PayPalActivity extends Activity {
	private static final int REQUEST_CODE_PAYMENT = 1;
	
	// PayPal configuration
    private static PayPalConfiguration paypalConfig = new PayPalConfiguration()
            .environment(Config.PAYPAL_ENVIRONMENT).clientId(
                    Config.PAYPAL_CLIENT_ID);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal);
    }
}
