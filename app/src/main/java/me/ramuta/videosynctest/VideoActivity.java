package me.ramuta.videosynctest;

import android.app.ActionBar;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import java.util.Calendar;


public class VideoActivity extends Activity {
    private ActionBar actionBar;

    private int currentVideo;

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        actionBar = getActionBar();
        actionBar.hide();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentVideo = extras.getInt(MainActivity.VIDEO_ID_TAG);
            Logga.i("Current video id: " + currentVideo);
        } else {
            Logga.e("Bundle doesnt worka");
            currentVideo = R.raw.tl;
        }



        videoView = (VideoView) findViewById(R.id.videoView);
        String path = "android.resource://" + getPackageName() + "/" + currentVideo;
        videoView.setVideoURI(Uri.parse(path));
        videoView.seekTo(startVideoTime());
        // if delay, implement onSeekCompleteListener. Might need to add a second long pause (include it into startVideoTime)

        videoView.start();

        videoView.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
    }

    public int startVideoTime() {
        Calendar now = Calendar.getInstance();
        int second = now.get(Calendar.SECOND);
        int millis = now.get(Calendar.MILLISECOND);
        Logga.i("Current second: " + second);

        int time = second*1000 + millis;

        if (time < 31000) {
            return time;
        } else {
            return time - 30000;
        }
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
