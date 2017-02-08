package com.gbrf;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
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
import android.os.Build.VERSION;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.gbrf.dto.UserDTO;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.PlayerStyle;
import com.google.android.youtube.player.YouTubePlayerView;

@SuppressLint("NewApi")
public class CertainActivity extends YouTubeBaseActivity implements
YouTubePlayer.OnInitializedListener, LoaderCallbacks<Cursor> {

	private UserInvitationTask mInvitationTask = null;
	private static final int RECOVERY_DIALOG_REQUEST = 1;
	private String miId;
	private String muId;
	private String mnId,name,countryName;
	DisplayMetrics dm;
	private YouTubePlayerView youTubeView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_certain);
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);

		// Initializing video player with developer key
		youTubeView.initialize(Config.DEVELOPER_KEY, this);
		//populateAutoComplete();
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		//miId = preferences.getString("iid", "Test");
		muId = preferences.getString("UID", "Test");
		//mnId = preferences.getString("nId", "Test");
		Intent i = getIntent();
		mnId = i.getStringExtra("nId");
		miId = i.getStringExtra("iid");
		name=preferences.getString("Name", "Test");
		countryName=preferences.getString("CountryName", "Test");
		
		Button releasebtn = (Button) findViewById(R.id.releasebook);
		releasebtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mInvitationTask = new UserInvitationTask(muId,miId,mnId);
				mInvitationTask.execute((Void) null);
			}
		});

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
				CertainActivity.this, android.R.layout.simple_dropdown_item_1line,
				emailAddressCollection);

	}
	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	
	private void savePreferences(String key, String value) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor edit = sp.edit();
		edit.putString(key, value);
		edit.commit();
	}
	public class UserInvitationTask extends AsyncTask<Void, Void, Boolean> {

		private final String muid;
		private final String miid;
		private final String mnid;

		UserInvitationTask(String uid,String iid,String nid) {
			muid = uid;
			miid = iid;
			mnid = nid;

		}

		@Override
		protected Boolean doInBackground(Void... params) {

			DataSyncService service = new DataSyncService();
			try {
				UserDTO dto = service.release(muid, miid, mnid);
				((PE) getApplicationContext()).setUserDTO(dto);
				System.out.println("data" + dto.getStatus());
				if (dto.getStatus().equals("success")) {
					ArrayList mylist = new ArrayList();
					mylist = (ArrayList) service.guest(mnid);
					((PE) getApplicationContext()).setList(mylist);
					return true;
				}
			} catch (Exception e) {

				e.printStackTrace();

			}

			return false;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mInvitationTask = null;

			System.out.println(success);

			if (success) {
				Toast.makeText(
						getApplicationContext(),
						((PE) getApplicationContext()).getUserDTO()
								.getMessage(), Toast.LENGTH_SHORT).show();
				Intent homeIntent = new Intent(CertainActivity.this,
						GuestOfHonourActivity.class);
				homeIntent.putExtra("nId", mnId);
				homeIntent.putExtra("iid", miid);
			/*	homeIntent.putExtra("nId", mnId);
				homeIntent.putExtra("iid", miid);
				homeIntent.putExtra("released_on",released_on);
				homeIntent.putExtra("name",name);
				homeIntent.putExtra("CountryName",countryName);*/
				
				startActivity(homeIntent);
				finish();

			} 

		}

	}

	protected void onCancelled() {
		mInvitationTask = null;

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
