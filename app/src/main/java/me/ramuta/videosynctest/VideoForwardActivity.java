package me.ramuta.videosynctest;

import android.app.ActionBar;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import java.util.Calendar;


public class VideoForwardActivity extends Activity {
    private ActionBar actionBar;

    private int currentVideo;

    private VideoView videoView;
    private Button forward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_forward);

        actionBar = getActionBar();
        actionBar.hide();

        videoView = (VideoView) findViewById(R.id.videoForwardView);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.tl;
        videoView.setVideoURI(Uri.parse(path));
        videoView.start();

        videoView.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        forward = (Button) findViewById(R.id.video_forward_button);
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logga.i("forward button");
                videoView.seekTo(5000);
                videoView.start();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        videoView.stopPlayback();
    }

    @Override
    protected void onStop() {
        super.onStop();
        videoView.stopPlayback();
    }
}
