package com.gbrf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

public class WebViewBookActivity extends ActionBarActivity {

	private WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_view_book);
		ActionBar actionBar = getSupportActionBar();
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		actionBar.setTitle("Book Store");
		actionBar.setDisplayUseLogoEnabled(true);
		getSupportActionBar().setIcon(R.drawable.logo);
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		String url = preferences.getString("URL", "Test");
		System.out.println("url"+url);
		webView = (WebView) findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl(url);
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
		// noinspection SimplifiableIfStatement
		if (id == R.id.action_mybook) {
			Intent homeIntent = new Intent(this.getApplicationContext(),
					OrderHistoryActivity.class);
			startActivity(homeIntent);

		}
		// noinspection SimplifiableIfStatement
		if (id == R.id.action_logout) {
			savePreferences("Login", false);
			Intent homeIntent = new Intent(this.getApplicationContext(),
					LoginActivity.class);
			startActivity(homeIntent);

			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
