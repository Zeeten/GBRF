package com.gbrf;

import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gbrf.dto.UserDTO;

/**
 * Created by Admin on 14-08-2015.
 */
@SuppressLint("NewApi")
public class Fragment3 extends Fragment implements LoaderCallbacks<Cursor> {

	// private UserRegistrationTask mRegistrationTask = null;

	private UserInvitationTask mInvitationTask = null;
	private View mProgressView;
	private String muId,iid;
	private String mpId,released_on,accepted_on,releasedon,thumbnailimage;
	private String mnId;
	GPSTracker gps;
	double latitude = 0.0;
	double longitude = 0.0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		View v = (RelativeLayout) inflater.inflate(R.layout.fragment3_layout,
				container, false);
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		TextView txt1 = (TextView) v.findViewById(R.id.AcceptInvitation);
		Typeface font1 = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/Aller_Bd.ttf");
		txt1.setTypeface(font1);

		TextView txt2 = (TextView) v.findViewById(R.id.and);
		Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/Aller_Bd.ttf");
		txt2.setTypeface(font2);

		TextView txt3 = (TextView) v.findViewById(R.id.create);
		Typeface font3 = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/Segoe_Script_light.ttf");
		txt3.setTypeface(font3);

		TextView txt4 = (TextView) v.findViewById(R.id.history);
		Typeface font4 = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/Aller_Bd.ttf");
		txt4.setTypeface(font4);
		muId = preferences.getString("UID", "Test");
		//mnId = preferences.getString("nId", "Test");
		Intent i = getActivity().getIntent();
		 mnId = i.getStringExtra("nId");
		System.out.println("uid" + muId);
		gps = new GPSTracker(getActivity());

		// check if GPS enabled
		if (gps.canGetLocation()) {

			latitude = gps.getLatitude();
			longitude = gps.getLongitude();

			// \n is for new line
			// Toast.makeText(getApplicationContext(),
			// "Your Location is - \nLat: " + latitude + "\nLong: " + longitude,
			// Toast.LENGTH_LONG).show();
		} else {
			// can't get location
			// GPS or Network is not enabled
			// Ask user to enable GPS/network in settings
			gps.showSettingsAlert();
		}

		//populateAutoComplete();
		accepted_on=i.getStringExtra("accepted_on");
		released_on=i.getStringExtra("released_on");
		System.out.println("ffrr"+released_on);
		releasedon=i.getStringExtra("releasedon");
		thumbnailimage=i.getStringExtra("thumbnailimage");
		Button accept_button = (Button) v.findViewById(R.id.Invitation);

		accept_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				attemptLogin();
			}
		});
		mProgressView = v.findViewById(R.id.invitation_progress);

		return v;
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

	public void attemptLogin() {
		if (mInvitationTask != null) {
			return;
		}

		String uid = muId;
		String nid = mnId;
		double lat = latitude;
		double longe = longitude;

		boolean cancel = false;
		View focusView = null;

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			showProgress(true);
			mInvitationTask = new UserInvitationTask(uid,nid, lat, longe);
			mInvitationTask.execute((Void) null);
		}
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

	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		return new CursorLoader(getActivity(),
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

	@Override
	public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
		List<String> emails = new ArrayList<String>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			emails.add(cursor.getString(ProfileQuery.ADDRESS));
			cursor.moveToNext();
		}

		addEmailsToAutoComplete(emails);
	}

	@Override
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
			ContentResolver cr = getActivity().getContentResolver();
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
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_dropdown_item_1line,
				emailAddressCollection);
	}

	private void savePreferences(String key, String value) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		Editor edit = sp.edit();
		edit.putString(key, value);
		edit.commit();
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserInvitationTask extends AsyncTask<Void, Void, Boolean> {

		private final String muid;
		private final String mnid;
		private final double mlatitude;
		private final double mlongitude;

		UserInvitationTask(String uid,String nid, double latitude, double longitude) {
			muid = uid;
			mnid = nid;
			mlatitude = latitude;
			mlongitude = longitude;
		}

		@Override
		protected Boolean doInBackground(Void... params) {

			DataSyncService service = new DataSyncService();
			try {
				UserDTO dto = service.invitation(muid,mnid, mlatitude, mlongitude);
				((PE) getActivity().getApplicationContext()).setUserDTO(dto);
				System.out.println("data" + dto.getStatus());
				if (dto.getStatus().equals("success")) {
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
			 iid	=((PE) getActivity().getApplicationContext())
				.getUserDTO().getIid();
		/*	 released_on	=((PE) getActivity().getApplicationContext())
						.getUserDTO().getReleaseOn();
			 accepted_on	=((PE) getActivity().getApplicationContext())
						.getUserDTO().getReleaseOn();*/
				AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
				alert.setMessage(((PE) getActivity().getApplicationContext())
						.getUserDTO().getMessage());
				alert.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent homeIntent = new Intent(getActivity()
										.getApplicationContext(),
										EventActivity.class);
								homeIntent.putExtra("iid", iid);
								homeIntent.putExtra("nId", mnId);
								homeIntent.putExtra("released_on", released_on);
								homeIntent.putExtra("accepted_on",accepted_on);
								homeIntent.putExtra("releasedon",releasedon);
								homeIntent.putExtra("thumbnailimage",thumbnailimage);
								startActivity(homeIntent);

								getActivity().finish();

							}
						});
				alert.show();

			}

			showProgress(false);
		}

		@Override
		protected void onCancelled() {
			mInvitationTask = null;
			showProgress(false);
		}
	}

	/*
	 * public void clickInvitation(View v){ Intent intent = new
	 * Intent(getActivity(), Timer_Invitation.class); startActivity(intent); }
	 */
}
