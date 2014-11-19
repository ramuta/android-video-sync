package me.ramuta.videosynctest;

import android.app.ActionBar;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class VideoActivity extends Activity {
    private ActionBar actionBar;

    private int currentVideo;

    private VideoView videoView;

    //timer
    Timer timer;
    TimerTask doAsynchronousTask;


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

        new StartVideoAsynctask().execute();

        /*
        final Handler handler = new Handler();
        timer = new Timer();
        doAsynchronousTask = new TimerTask()
        {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try
                        {
                            new StartVideoAsynctask().execute();

                        }
                        catch (Exception e)
                        {

                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 5000); //execute in every 5s*
        */
    }

    class StartVideoAsynctask extends AsyncTask<String, Void, Integer> {
        int pause = 0;

        protected Integer doInBackground(String... params) {
            SntpClient client = new SntpClient();
            long timestamp = 0;
            if (client.requestTime("pool.ntp.org", 30000)) {
                timestamp = client.getNtpTime();
            }

            return startVideoTime(timestamp, pause);
        }

        protected void onPostExecute(Integer msec) {
            videoView.seekTo(msec);

            try {
                Thread.sleep(pause*1000);  // this second is added to the startVideoTime
                videoView.start();
            } catch (InterruptedException e) {
                Logga.e(e.toString());
            }

            // if still experiencing delay, implement onSeekCompleteListener.

            videoView.setOnPreparedListener(new OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                }
            });
        }
    }

    public int startVideoTime(long timestamp, int pause) {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(timestamp);
        int second = now.get(Calendar.SECOND);
        int millis = now.get(Calendar.MILLISECOND);
        Logga.i("Current second: " + second);

        int time = (second + pause)*1000 + millis;

        int limit = (30 + pause) * 1000;

        if (time < limit) {
            return time;
        } else {
            return time - limit;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        videoView.stopPlayback();
        timer.cancel();
    }

    @Override
    protected void onStop() {
        super.onStop();
        videoView.stopPlayback();
        timer.cancel();
    }
}
