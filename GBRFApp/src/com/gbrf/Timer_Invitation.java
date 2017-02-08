package com.gbrf;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class Timer_Invitation extends Fragment implements
		LoaderCallbacks<Cursor> {

	private TextView timerValue;

	private long startTime = 0L;
	private String muId,nId,iid,accepted_on,released_on;

	private Handler customHandler = new Handler();
	//private UserInvitationTask mInvitationTask = null;
	long timeInMilliseconds = 0L;
	long timeSwapBuff = 0L;
	long updatedTime = 0L;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (container == null) {
			return null;
		}
		View v = (RelativeLayout) inflater.inflate(
				R.layout.activity_timer__invitation, container, false);
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		//String releaseon = preferences.getString("releasedon", "Test");
		Intent i = getActivity().getIntent();
		String releaseon = i.getStringExtra("releasedon");
		nId = i.getStringExtra("nId");
		iid = i.getStringExtra("iid");
		accepted_on=i.getStringExtra("accepted_on");
		released_on=i.getStringExtra("released_on");
		String[] token = releaseon.split(" ");
		String datetoken = token[0];
		String timetoken = token[1];
		String[] token1 = datetoken.split("-");
		int yeartoken = Integer.parseInt(token1[0]);
		int monthtoken = Integer.parseInt(token1[1]);
		int daytoken = Integer.parseInt(token1[2]);
		
		String[] token2 = timetoken.split(":");
		int hourtoken = Integer.parseInt(token2[0]);
		int minutetoken = Integer.parseInt(token2[1]);
		int secondtoken = Integer.parseInt(token2[2]);
		final TextView txt1 = (TextView) v.findViewById(R.id.BeingReleased);
		Typeface font1 = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/Aller_Bd.ttf");
		txt1.setTypeface(font1);

		final TextView txt2 = (TextView) v.findViewById(R.id.ReleasedMonth);
		Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/Segoe_Script_light.ttf");
		txt2.setTypeface(font2);
		txt2.setText(new DateFormatSymbols().getMonths()[monthtoken - 1]);

		final TextView txt3 = (TextView) v.findViewById(R.id.ReleasedDate);
		// Typeface font3 = Typeface.createFromAsset(getAssets(),
		// "fonts/Aller_Bd.ttf");
		txt3.setTypeface(font1);
		txt3.setText(daytoken+"th 4:00 PM");

		final TextView txt4 = (TextView) v.findViewById(R.id.ReleasedMinutes);
		final TextView txt5 = (TextView) v.findViewById(R.id.ReleasedSeconds);
		final TextView txt6 = (TextView) v.findViewById(R.id.ReleasedDays);
		final TextView txt7 = (TextView) v.findViewById(R.id.ReleasedHours);
		String timeZone = preferences.getString("TimeZone", "Test");
		muId = preferences.getString("UID", "Test");

		
		
		TextView btn = (TextView) v.findViewById(R.id.KnowMore);
		btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), CertainActivity.class);
				startActivity(intent);
			}
		});
		Date date = new Date();
		DateFormat secode = new SimpleDateFormat("ss");
		DateFormat minute = new SimpleDateFormat("mm");
		DateFormat hour = new SimpleDateFormat("HH");

		DateFormat day = new SimpleDateFormat("dd");
		DateFormat month = new SimpleDateFormat("MM");
		DateFormat yyyy = new SimpleDateFormat("yyyy");

		// Use Madrid's time zone to format the date in
		secode.setTimeZone(TimeZone.getTimeZone(timeZone));
		minute.setTimeZone(TimeZone.getTimeZone(timeZone));
		hour.setTimeZone(TimeZone.getTimeZone(timeZone));

		day.setTimeZone(TimeZone.getTimeZone(timeZone));
		month.setTimeZone(TimeZone.getTimeZone(timeZone));
		yyyy.setTimeZone(TimeZone.getTimeZone(timeZone));

		String setSecode = secode.format(date);
		String setMinute = minute.format(date);
		String setHour = hour.format(date);

		String setDay = day.format(date);
		String setMonth = month.format(date);
		String setYear = yyyy.format(date);

		int setsecode = Integer.valueOf(setSecode);
		int setminute = Integer.valueOf(setMinute);
		int sethour = Integer.valueOf(setHour);

		int setday = Integer.valueOf(setDay);
		int setmonth = Integer.valueOf(setMonth);
		int setyear = Integer.valueOf(setYear);

		// final TextView tv = new TextView(this);
		final TextView tv = (TextView) v.findViewById(R.id.timerValue);

		Time TimerSet = new Time();
		TimerSet.set(secondtoken, minutetoken, hourtoken, daytoken, monthtoken, yeartoken);// day month year
		TimerSet.normalize(true);
		long millis = TimerSet.toMillis(true);

		Time TimeNow = new Time();
		TimeNow.set(setsecode, setminute, sethour, setday, setmonth, setyear); // set
																				// the
																				// date
																				// to
																				// Current
																				// Time
		TimeNow.normalize(true);
		long millis2 = TimeNow.toMillis(true);

		long millisset = millis - millis2; // subtract current from future to
											// set the time remaining

		final int smillis = (int) (millis); // convert long to integer to
											// display conversion results
		final int smillis2 = (int) (millis2);

		//populateAutoComplete();

		new CountDownTimer(millisset, 1000) {

			@Override
			public void onTick(long millis) {
				/*
				 * int seconds = (int) (millis / 1000) % 60 ; int minutes =
				 * (int) ((millis / (1000*60)) % 60); int hours = (int) ((millis
				 * / (1000*60*60)) % 24);
				 */

				int days = (int) ((millis / 86400000));
				int hours = (int) (((millis / 1000) - (days * 86400)) / 3600);
				int minutes = (int) (((millis / 1000) - ((days * 86400) + (hours * 3600))) / 60);
				int seconds = (int) ((millis / 1000) % 60);
				int millicn = (int) (millis / 1000);
				// String text =
				// String.format("%02d days,%02d hours, %02d minutes, %02d seconds",days,hours,minutes,seconds);
				// tv.setText(text);
				String dayTxt = String.format("%02d\n Days", days);
				txt6.setText(dayTxt);
				String hourTxt = String.format("%02d\n Hours", hours);
				txt7.setText(hourTxt);
				String minuteTxt = String.format("%02d\n Minutes", minutes);
				txt4.setText(minuteTxt);
				String secondTxt = String.format("%02d\n Seconds", seconds);
				txt5.setText(secondTxt);

			}

			@Override
			public void onFinish() {
				try{
					if (released_on.equals("null")) {
						Intent homeIntent = new Intent(getActivity(),
								CertainActivity.class);
						homeIntent.putExtra("nId", nId);
						homeIntent.putExtra("iid", iid);
						homeIntent.putExtra("released_on", released_on);
						homeIntent.putExtra("accepted_on", accepted_on);
						startActivity(homeIntent);
						getActivity().finish();
					} else {
						Intent homeIntent = new Intent(getActivity(),
								AfterReleaseActivity.class);
						homeIntent.putExtra("iid", iid);
						homeIntent.putExtra("nId", nId);
						homeIntent.putExtra("released_on", released_on);
						homeIntent.putExtra("accepted_on", accepted_on);
						startActivity(homeIntent);
						getActivity().finish();
					}
					
				}catch(Exception e){
					e.printStackTrace();
					
				}
				
				
				//mInvitationTask = new UserInvitationTask(muId);
			//	mInvitationTask.execute((Void) null);
			}
		}.start();

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

/*	*//**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 *//*
	public class UserInvitationTask extends AsyncTask<Void, Void, Boolean> {

		private final String muid;

		UserInvitationTask(String uid) {
			muid = uid;
		}

		@Override
		protected Boolean doInBackground(Void... params) {

			DataSyncService service = new DataSyncService();
			try {
				UserDTO dto = service.invitationcheck(muid);
				((PE) getActivity().getApplicationContext()).setUserDTO(dto);
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
				if (((PE) getActivity().getApplicationContext()).getUserDTO()
						.getReleaseOn().equals("null")) {
					savePreferences("iid", ((PE) getActivity()
							.getApplicationContext()).getUserDTO().getIid());
					Intent homeIntent = new Intent(getActivity(),
							CertainActivity.class);
					homeIntent.putExtra("nId", nId);
					homeIntent.putExtra("iid", iid);
					startActivity(homeIntent);
					getActivity().finish();
				} else {
					savePreferences("iid", ((PE) getActivity()
							.getApplicationContext()).getUserDTO().getIid());
					savePreferences("release_on", ((PE) getActivity().getApplicationContext())
							.getUserDTO().getReleaseOn());
					Intent homeIntent = new Intent(getActivity(),
							AfterReleaseActivity.class);
					homeIntent.putExtra("nId", nId);
					startActivity(homeIntent);
					getActivity().finish();
				}
			}

		}

		@Override
		protected void onCancelled() {
			mInvitationTask = null;
		}
	}*/

}
