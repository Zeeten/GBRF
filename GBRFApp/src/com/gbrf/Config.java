package com.gbrf;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;

public class Config {
	// Google Console APIs developer key
	// Replace this key with your's
	public static final String DEVELOPER_KEY = "AIzaSyCj3Y1AyTF7x899UE0FsWoj8PcgKLNwIxo";
	
	// YouTube video id
	public static final String YOUTUBE_VIDEO_CODE = "1llq2IH5fW4";
	public static final String YOUTUBE_RELEASE_VIDEO_CODE = "6XXThSphb3A";
	
	 // PayPal app configuration
    public static final String PAYPAL_CLIENT_ID = "AXqZqmosGCnBJqMt1WvVHik1LpBsHj7mBbkOLoIlRGdkU2nVXA8bOs6PK0hmC8Pb__XlnAcH50irMYep";
    public static final String PAYPAL_CLIENT_SECRET = "EKF3WUWGJA80byO-NFAChk2KzMHRYr5sDwhXfLfgKjk51se8VqwRUuqxJt6sYWe-vL37ps48EypNUB5a";
 
    public static final String PAYPAL_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
    public static final String PAYMENT_INTENT = PayPalPayment.PAYMENT_INTENT_SALE;
    public static final String DEFAULT_CURRENCY = "USD";
 
    // PayPal server urls
    public static final String URL_PRODUCTS = "http://192.168.0.103/PayPalServer/v1/products";
    public static final String URL_VERIFY_PAYMENT = "http://192.168.0.103/PayPalServer/v1/verifyPayment";
}
