package com.avenues.lib.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gbrf.BookDetailActivity;
import com.gbrf.R;

public class StatusActivity extends Activity {
	
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_status);
		
		Intent mainIntent = getIntent();
		TextView tv4 = (TextView) findViewById(R.id.textView1);
		tv4.setText(mainIntent.getStringExtra("transStatus"));
		Button store = (Button) findViewById(R.id.download);
		store.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(StatusActivity.this,
						BookDetailActivity.class);
				startActivity(intent);
				finish();
			}
		});

	}
	
	public void showToast(String msg) {
		Toast.makeText(this, "Toast: " + msg, Toast.LENGTH_LONG).show();
	}
} 