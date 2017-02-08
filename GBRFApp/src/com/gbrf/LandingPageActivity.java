package com.gbrf;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LandingPageActivity extends Activity {


	
	private TextView timerValue;
	
	private long startTime = 0L;
	
	private Handler customHandler = new Handler();
	
	long timeInMilliseconds = 0L;
	long timeSwapBuff = 0L;
	long updatedTime = 0L;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_landing_page);
		SharedPreferences preferences = PreferenceManager
	            .getDefaultSharedPreferences(this);
		String timeZone = preferences.getString("TimeZone","Test");
		System.out.println("timeZone"+timeZone);
	      Button btn = (Button)findViewById(R.id.release);
	      btn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(LandingPageActivity.this,
							CertainActivity.class);
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
	      
String setSecode=secode.format(date);
String setMinute=minute.format(date);
String setHour=hour.format(date);

String setDay=day.format(date);
String setMonth=month.format(date);
String setYear=yyyy.format(date);

int setsecode = Integer.valueOf(setSecode);
int setminute = Integer.valueOf(setMinute);
int sethour = Integer.valueOf(setHour);

int setday = Integer.valueOf(setDay);
int setmonth = Integer.valueOf(setMonth);
int setyear = Integer.valueOf(setYear);


		// final TextView tv = new TextView(this);
	    final  TextView tv = (TextView)findViewById(R.id.timerValue);

		    Time TimerSet = new Time();
		    TimerSet.set(0,0,20,15,9,2015);//day month year
	        TimerSet.normalize(true);
	        long millis = TimerSet.toMillis(true);

	        Time TimeNow = new Time();
	        TimeNow.set(setsecode, setminute, sethour, setday, setmonth, setyear); // set the date to Current Time
	        TimeNow.normalize(true);
	        long millis2 = TimeNow.toMillis(true);

	        long millisset = millis - millis2; //subtract current from future to set the time remaining

	        final int smillis = (int) (millis); //convert long to integer to display conversion results
	        final int smillis2 = (int) (millis2);



		    new CountDownTimer(millisset,1000) {

		        @Override
		        public void onTick(long millis) {
		           /* int seconds = (int) (millis / 1000) % 60 ;
		            int minutes = (int) ((millis / (1000*60)) % 60);
		            int hours   = (int) ((millis / (1000*60*60)) % 24);*/
		         
		            
		            int days = (int) ((millis /86400000));
	                int hours = (int) (((millis / 1000) - (days
	                        * 86400)) / 3600);
	                int minutes = (int) (((millis / 1000) - ((days
	                        * 86400) + (hours * 3600))) / 60);
	                int seconds = (int) ((millis / 1000) % 60);
	                int millicn = (int) (millis / 1000);
	                String text = String.format("%02d days,%02d hours, %02d minutes, %02d seconds",days,hours,minutes,seconds);
		            tv.setText(text);
		        }

		        @Override
		        public void onFinish() {
		            tv.setText("Kabooom");              
		        }
		    }.start();

		}
}
