package com.gbrf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class BookStoreActivity extends ActionBarActivity {

	private Fragment contentFragment;
	BookFragment homeFragment;
	ProductDetailFragment pdtDetailFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_store);
		ActionBar actionBar = getSupportActionBar();
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		actionBar.setTitle("Global Book Store");
		actionBar.setDisplayUseLogoEnabled(true);
		getSupportActionBar().setIcon(R.drawable.logo);
		FragmentManager fragmentManager = getSupportFragmentManager();

		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey("content")) {
				String content = savedInstanceState.getString("content");
				if (content.equals(ProductDetailFragment.ARG_ITEM_ID)) {
					if (fragmentManager
							.findFragmentByTag(ProductDetailFragment.ARG_ITEM_ID) != null) {
						contentFragment = fragmentManager
								.findFragmentByTag(ProductDetailFragment.ARG_ITEM_ID);
					}
				}
			}
			if (fragmentManager.findFragmentByTag(BookFragment.ARG_ITEM_ID) != null) {
				homeFragment = (BookFragment) fragmentManager
						.findFragmentByTag(BookFragment.ARG_ITEM_ID);
				contentFragment = homeFragment;
			}
		} else {
			homeFragment = new BookFragment();
			switchContent(homeFragment, BookFragment.ARG_ITEM_ID);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		if (contentFragment instanceof BookFragment) {
			outState.putString("content", BookFragment.ARG_ITEM_ID);
		} else {
			outState.putString("content", ProductDetailFragment.ARG_ITEM_ID);
		}
		super.onSaveInstanceState(outState);
	}

	public void switchContent(Fragment fragment, String tag) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		while (fragmentManager.popBackStackImmediate())
			;

		if (fragment != null) {
			FragmentTransaction transaction = fragmentManager
					.beginTransaction();
			transaction.replace(R.id.content_frame, fragment, tag);
			// Only ProductDetailFragment is added to the back stack.
			if (!(fragment instanceof BookFragment)) {
				transaction.addToBackStack(tag);
			}
			transaction.commit();
			contentFragment = fragment;
		}
	}

	@Override
	public void onBackPressed() {
		FragmentManager fm = getSupportFragmentManager();
		if (fm.getBackStackEntryCount() > 0) {
			super.onBackPressed();
		} else if (contentFragment instanceof BookFragment
				|| fm.getBackStackEntryCount() == 0) {
			finish();
		}
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
