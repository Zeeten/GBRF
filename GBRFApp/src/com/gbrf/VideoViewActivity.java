package com.gbrf;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import com.gbrf.dto.UserDTO;

public class VideoViewActivity extends ActionBarActivity {

	private VideoView myVideoView;
	private int position = 0;
	private ProgressDialog progressDialog;
	private MediaController mediaControls;
	DisplayMetrics dm;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Get the layout from video_main.xml
		setContentView(R.layout.activity_video_view);
		ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Literary Music");
        actionBar.setDisplayUseLogoEnabled(true);
        getSupportActionBar().setIcon(R.drawable.logo);
		PE pe = (PE) getApplicationContext();
		UserDTO dto = pe.getUserDTO();
		if (mediaControls == null) {
			mediaControls = new MediaController(VideoViewActivity.this);
		}

		// Find your VideoView in your video_main.xml layout
		myVideoView = (VideoView) findViewById(R.id.video_view);

		// Create a progressbar
		progressDialog = new ProgressDialog(VideoViewActivity.this);
		// Set progressbar title
		progressDialog.setTitle(dto.getTitle());
		// Set progressbar message
		progressDialog.setMessage("Loading...");

		progressDialog.setCancelable(false);
		// Show progressbar
		progressDialog.show();

		try {

			myVideoView.setMediaController(mediaControls);
			myVideoView.setVideoURI(Uri.parse(dto.getUrl()));
		
			  } catch (Exception e) {
			Log.e("Error", e.getMessage());
			e.printStackTrace();
		}

		myVideoView.requestFocus();
		myVideoView.setOnPreparedListener(new OnPreparedListener() {
			// Close the progress bar and play the video
			public void onPrepared(MediaPlayer mp) {
				progressDialog.dismiss();
				myVideoView.seekTo(position);
				if (position == 0) {
					myVideoView.start();
				} else {
					myVideoView.pause();
				}
			}
		});

	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putInt("Position", myVideoView.getCurrentPosition());
		myVideoView.pause();
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		position = savedInstanceState.getInt("Position");
		myVideoView.seekTo(position);
	}
}