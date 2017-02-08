package com.gbrf;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.PlayerStyle;
import com.google.android.youtube.player.YouTubePlayerView;

public class ReleaseBookActivity extends YouTubeBaseActivity implements
YouTubePlayer.OnInitializedListener, LoaderCallbacks<Cursor> {

	private UserInvitationTask mInvitationTask = null;
	private static final int RECOVERY_DIALOG_REQUEST = 1;
	private String mnId;

// YouTube player view
private YouTubePlayerView youTubeView;
private TextView tv;
@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
requestWindowFeature(Window.FEATURE_NO_TITLE);
getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		WindowManager.LayoutParams.FLAG_FULLSCREEN);

setContentView(R.layout.activity_release_book);

youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);

// Initializing video player with developer key
youTubeView.initialize(Config.DEVELOPER_KEY, this);
//populateAutoComplete();

Intent i = getIntent();
mnId = i.getStringExtra("nId");
mInvitationTask = new UserInvitationTask(mnId);
mInvitationTask.execute((Void) null);

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
			ReleaseBookActivity.this,
			android.R.layout.simple_dropdown_item_1line,
			emailAddressCollection);

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
	player.loadVideo(Config.YOUTUBE_RELEASE_VIDEO_CODE);

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
@Override
protected void onResume() {
    super.onResume();

new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent = new Intent(ReleaseBookActivity.this, GuestOfHonourActivity.class);
                ReleaseBookActivity.this.startActivity(mainIntent);
                ReleaseBookActivity.this.finish();
            }
        }, 7000);


}


}
