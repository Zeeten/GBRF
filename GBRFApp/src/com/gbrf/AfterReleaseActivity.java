package com.gbrf;

import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.PlayerStyle;
import com.google.android.youtube.player.YouTubePlayerView;

@SuppressLint("NewApi")
public class AfterReleaseActivity extends YouTubeBaseActivity implements
		YouTubePlayer.OnInitializedListener, LoaderCallbacks<Cursor> {

	private UserInvitationTask mInvitationTask = null;
	private static final int RECOVERY_DIALOG_REQUEST = 1;
	private String mnId;
	// YouTube player view
	private YouTubePlayerView youTubeView;
	private String bookName;
	private String authorName;
	private String price;
	private String image;
	private String publisher;
	private String isbn;
	private String pages;
	private String format;
	private String description,purchased,oid,name,countryName,released_on,miid;
	private View mProgressView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_after_release);

		youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);

		// Initializing video player with developer key
		youTubeView.initialize(Config.DEVELOPER_KEY, this);
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		//mnId = preferences.getString("nId", "Test");
		Intent i = getIntent();
		mnId = i.getStringExtra("nId");
		miid = i.getStringExtra("iid");
		released_on=i.getStringExtra("released_on");
		System.out.println("Aro"+released_on);
		bookName = preferences.getString("bookname", "Test");
		authorName = preferences.getString("authorname", "Test");
		price = preferences.getString("price", "Test");
		image = preferences.getString("image", "Test");
		publisher = preferences.getString("publisher", "Test");
		isbn = preferences.getString("isbn", "Test");
		pages = preferences.getString("pages", "Test");
		format = preferences.getString("format", "Test");
		description = preferences.getString("description", "Test");
		purchased = preferences.getString("purchased", "Test");
		oid = preferences.getString("oid", "Test");
		name=preferences.getString("Name", "Test");
		countryName=preferences.getString("CountryName", "Test");
		Button btn = (Button) findViewById(R.id.buy);
		btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(
						AfterReleaseActivity.this);
				alert.setMessage("Being released shortly");
				alert.setPositiveButton("OK", null);
				alert.show();

				/*Intent intent = new Intent(AfterReleaseActivity.this,
						SingleItemView.class);
				// Pass all data rank
				intent.putExtra("bookname", bookName);
				// Pass all data country
				intent.putExtra("authername", authorName);
				// Pass all data population
				intent.putExtra("price", price);
				// Pass all data flag
				intent.putExtra("image", image);
				intent.putExtra("publisher", publisher);
				intent.putExtra("isbn", isbn);
				intent.putExtra("pages", pages);
				intent.putExtra("format", format);
				intent.putExtra("description", description);
				intent.putExtra("purchased", purchased);
				intent.putExtra("oid", oid);
				// Start SingleItemView Class
				startActivity(intent);
*/
			}
		});
		//populateAutoComplete();

		Button guestbtn = (Button) findViewById(R.id.guestofkonour);
		guestbtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				/*Intent homeIntent = new Intent(AfterReleaseActivity.this,
						GuestOfHonourActivity.class);
				homeIntent.putExtra("nId", mnId);
				homeIntent.putExtra("iid", miid);
				homeIntent.putExtra("released_on",released_on);
				homeIntent.putExtra("name",name);
				homeIntent.putExtra("CountryName",countryName);
				startActivity(homeIntent);
				finish();*/
				/*mInvitationTask = new UserInvitationTask(mnId);
				mInvitationTask.execute((Void) null);*/
				attempRelease();
			}
		});
		mProgressView = findViewById(R.id.afterrelease_progress);

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

	public void attempRelease() {
		

			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			showProgress(true);
			mInvitationTask = new UserInvitationTask(mnId);
			mInvitationTask.execute((Void) null);
		
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
				AfterReleaseActivity.this,
				android.R.layout.simple_dropdown_item_1line,
				emailAddressCollection);

	}

	private void savePreferences(String key, String value) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor edit = sp.edit();
		edit.putString(key, value);
		edit.commit();
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserInvitationTask extends AsyncTask<Void, Void, Boolean> {

		private final String mnid;

		UserInvitationTask(String nid) {
			mnid = nid;

		}

		@Override
		protected Boolean doInBackground(Void... params) {

			DataSyncService service = new DataSyncService();
			ArrayList mylist = new ArrayList();
			try {
				mylist = (ArrayList) service.guest(mnid);
				((PE) getApplicationContext()).setList(mylist);
				return true;
			} catch (Exception e) {

				e.printStackTrace();

			}

			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mInvitationTask = null;

			System.out.println(success);

			if (success) {
				Intent homeIntent = new Intent(AfterReleaseActivity.this,
						GuestOfHonourActivity.class);
				homeIntent.putExtra("nId", mnId);
				homeIntent.putExtra("iid", miid);
				startActivity(homeIntent);
				finish();

			}
			showProgress(false);
		}

	}

	protected void onCancelled() {
		mInvitationTask = null;
		showProgress(false);

	}

	@Override
	public void onInitializationFailure(YouTubePlayer.Provider provider,
			YouTubeInitializationResult errorReason) {
		if (errorReason.isUserRecoverableError()) {
			errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
		} else {
			String errorMessage = String.format(
					getString(R.string.error_player), errorReason.toString());
			Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onInitializationSuccess(YouTubePlayer.Provider provider,
			YouTubePlayer player, boolean wasRestored) {
		if (!wasRestored) {

			// loadVideo() will auto play video
			// Use cueVideo() method, if you don't want to play it automatically
			player.loadVideo(Config.YOUTUBE_VIDEO_CODE);

			// Hiding player controls
			player.setPlayerStyle(PlayerStyle.CHROMELESS);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == RECOVERY_DIALOG_REQUEST) {
			// Retry initialization if user performed a recovery action
			getYouTubePlayerProvider().initialize(Config.DEVELOPER_KEY, this);
		}
	}

	private YouTubePlayer.Provider getYouTubePlayerProvider() {
		return (YouTubePlayerView) findViewById(R.id.youtube_view);
	}

}
