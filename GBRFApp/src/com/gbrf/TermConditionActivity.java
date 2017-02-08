package com.gbrf;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.widget.TextView;

public class TermConditionActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_term_condition);
		ActionBar actionBar = getSupportActionBar();
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		actionBar.setTitle("Academic World");
		actionBar.setDisplayUseLogoEnabled(true);
		getSupportActionBar().setIcon(R.drawable.logo);
		  String htmlText = "<body><h2>Terms and Conditions</h2><p>Welcome to theacademicworld.com. To avail the services offered at www.theacademicworld.com or by any of its affiliates, you must agree to the following terms and conditions. If you visit or shop or browse at www.theacademicworld.com you accept these conditions.</p> " +
	            "<p>Please read the term and conditions carefully. While using any current or future services offered by theacademicworld.com or any of its affiliates, whether or not included in the theacademicworld.com website, you will abide by these Terms &amp; conditions the guidelines and conditions applicable to such service or business.</p>" +
	            "<h3>PRIVACY POLICY</h3><p>Please review our Privacy Policy, which also governs your visit to www.theacademicworld.com, to fully understand our practices.</p>" +
	            "<h3>TERMS AND CONDITIONS ARE BINDING FOR ALL PURCHASES</h3><p>All orders are deemed offers for you to purchase our products. We may accept your offer by issuing a confirmatory e-mail and/or mobile confirmation of the products specified in your order. Our acceptance of each such offer is expressly subject to and conditioned on your assent to these terms and conditions of sale. No other terms and conditions will apply.</p>" +
	            "<h3>ELECTRONIC COMMUNICATION</h3><p>When you visit www.theacademicworld.com or send e-mails to us, you are communicating with us electronically. By communicating with us, you consent to receive communication from us electronically. We will communicate with you by e-mail or by posting notices on our website. You agree that all agreements, notices, disclosures, and other communications that we provide to you electronically satisfy any legal requirement that such communication be in writing.</p>" +
	            "<h3>PRICES</h3><p>All prices posted on this website are subject to change without notice. Prices prevailing at commencement of placing the order will apply. Posted prices do includes all taxes and charges. In case there are any additional charges or taxes the same will be mentioned on the website.</p>"+
	            "<h3>PAYMENT</h3><p>We accept payment by net-banking, credit card, debit card.</p></body>";
		  TextView htmlTextView = (TextView)findViewById(R.id.textView1);
          htmlTextView.setText(Html.fromHtml(htmlText));
	}

}
