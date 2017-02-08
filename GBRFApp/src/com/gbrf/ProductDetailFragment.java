package com.gbrf;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avenues.lib.activity.WebViewActivity;
import com.avenues.lib.utility.AvenuesParams;
import com.avenues.lib.utility.ServiceUtility;
import com.gbrf.dto.Product;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class ProductDetailFragment extends Fragment {

	TextView pdtIdTxt;
	TextView pdtNameTxt;
	ImageView pdtImg;
	TextView pdtAuthor;
	TextView pdtPublisher;
	TextView pdtBookName;
	Button pdtaddCartbtn;
	TextView pdtPrice;
	Activity activity;

	ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	private ImageLoadingListener imageListener;

	Product product;
	private EditText accessCode, merchantId, currency, amount, orderId, rsaKeyUrl, redirectUrl, cancelUrl;
	private AutoCompleteTextView name,email,phone,address,country,state,city,zipcode;

	public static final String ARG_ITEM_ID = "pdt_detail_fragment";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
		options = new DisplayImageOptions.Builder()
				.showImageOnFail(R.drawable.ic_launcher)
				.showStubImage(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher).cacheInMemory()
				.cacheOnDisc().build();

		imageListener = new ImageDisplayListener();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_pdt_detail, container,
				false);
		findViewById(view);
		 SharedPreferences preferences = PreferenceManager
		            .getDefaultSharedPreferences(getActivity());
		   String mname = preferences.getString("Name","Test");
		    String memail = preferences.getString("Email","Test");
			accessCode = (EditText)view. findViewById(R.id.accessCode);
			merchantId = (EditText)view. findViewById(R.id.merchantId);
			orderId  = (EditText)view. findViewById(R.id.orderId);
			currency = (EditText)view. findViewById(R.id.currency);
			amount = (EditText)view. findViewById(R.id.amount);
			rsaKeyUrl = (EditText)view. findViewById(R.id.rsaUrl);
			redirectUrl = (EditText)view. findViewById(R.id.redirectUrl);
			cancelUrl = (EditText)view. findViewById(R.id.cancelUrl);
			name = (AutoCompleteTextView) view.findViewById(R.id.EnterName);
			name.setText(mname);
			email = (AutoCompleteTextView)view. findViewById(R.id.EnterEmail);
			email.setText(memail);
			phone = (AutoCompleteTextView)view. findViewById(R.id.EnterMobile);
			address = (AutoCompleteTextView)view. findViewById(R.id.EnterAddress);
			country = (AutoCompleteTextView)view. findViewById(R.id.EnterCountry);
			state = (AutoCompleteTextView)view. findViewById(R.id.EnterState);
			city = (AutoCompleteTextView)view. findViewById(R.id.EnterCity);
			zipcode = (AutoCompleteTextView)view. findViewById(R.id.EnterZipcode);
			
			//generating order number
			Integer randomNum = ServiceUtility.randInt(0, 9999999);
			orderId.setText(randomNum.toString());
		pdtaddCartbtn=(Button) view.findViewById(R.id.textView9);

		pdtaddCartbtn.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View v) {
    				attemptPay();
    				
    			}
    		});
		Bundle bundle = this.getArguments();
		if (bundle != null) {
			product = bundle.getParcelable("singleProduct");
			setProductItem(product);
		}

		return view;
	}

	private void findViewById(View view) {

		//pdtNameTxt = (TextView) view.findViewById(R.id.pdt_name);
		//pdtIdTxt = (TextView) view.findViewById(R.id.product_id_text);

		pdtImg = (ImageView) view.findViewById(R.id.product_detail_img);
		pdtBookName = (TextView) view.findViewById(R.id.bookname);
		pdtAuthor = (TextView) view.findViewById(R.id.authorinfo);
		pdtPublisher = (TextView) view.findViewById(R.id.publisherinfo);
		pdtPrice = (TextView) view.findViewById(R.id.textView8);
		  
		
	}
	public void attemptPay() {
		//Mandatory parameters. Other parameters can be added if required.
		String vAccessCode = ServiceUtility.chkNull(accessCode.getText()).toString().trim();
		String vMerchantId = ServiceUtility.chkNull(merchantId.getText()).toString().trim();
		String vCurrency = ServiceUtility.chkNull(currency.getText()).toString().trim();
		String vAmount = ServiceUtility.chkNull(pdtPrice.getText()).toString().trim();
		if(!vAccessCode.equals("") && !vMerchantId.equals("") && !vCurrency.equals("") && !vAmount.equals("")){
			Intent intent = new Intent(getActivity(),WebViewActivity.class);
			intent.putExtra(AvenuesParams.ACCESS_CODE, ServiceUtility.chkNull(accessCode.getText()).toString().trim());
			intent.putExtra(AvenuesParams.MERCHANT_ID, ServiceUtility.chkNull(merchantId.getText()).toString().trim());
			intent.putExtra(AvenuesParams.ORDER_ID, ServiceUtility.chkNull(orderId.getText()).toString().trim());
			intent.putExtra(AvenuesParams.CURRENCY, ServiceUtility.chkNull(currency.getText()).toString().trim());
			intent.putExtra(AvenuesParams.AMOUNT, ServiceUtility.chkNull(pdtPrice.getText()).toString().trim());
			
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
		Toast.makeText(getActivity(), "Toast: " + msg, Toast.LENGTH_LONG).show();
	}

	private static class ImageDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);

				}
			}
		}
	}

	private void setProductItem(Product resultProduct) {
		//pdtNameTxt.setText("" + resultProduct.getName());
		//pdtIdTxt.setText("Product Id: " + resultProduct.getId());

		imageLoader.displayImage(resultProduct.getImageUrl(), pdtImg, options,
				imageListener);
		pdtBookName.setText("" + resultProduct.getName());
		pdtPrice.setText("" + resultProduct.getPrice());
	//	pdtAuthor.setText("" + resultProduct.getName());
		//pdtPublisher.setText("" + resultProduct.getName());
	}
}
