package com.raccoonsquare.dating;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;

import com.raccoonsquare.dating.common.ActivityBase;


public class VideoViewActivity extends ActivityBase {

    Toolbar toolbar;

    VideoView mVideoView;

    String videoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_view);

        mVideoView = findViewById(R.id.videoView);

        Intent i = getIntent();

        videoUrl = i.getStringExtra("videoUrl");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setElevation(0);

        getSupportActionBar().setTitle("");

        showpDialog();

        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(VideoViewActivity.this);
            mediacontroller.setAnchorView(mVideoView);
            // Get the URL from String VideoURL

            mVideoView.setMediaController(mediacontroller);
            mVideoView.setVideoPath(videoUrl);

        } catch (Exception e) {

            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        mVideoView.requestFocus();
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {

                hidepDialog();
                mVideoView.start();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {

            case android.R.id.home: {

                finish();
                return true;
            }

            default: {

                return super.onOptionsItemSelected(item);
            }
        }
    }
}
