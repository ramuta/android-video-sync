package me.ramuta.videosynctest;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.util.Calendar;


public class MainActivity extends Activity {

    private Button topLeft;
    private Button bottomLeft;
    private Button topRight;
    private Button bottomRight;
    private Button forward;

    public static final String VIDEO_ID_TAG = "VID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Logga.i("Main Activity started");

        // UI
        topLeft = (Button) findViewById(R.id.main_left_top);
        bottomLeft = (Button) findViewById(R.id.main_left_bottom);
        topRight = (Button) findViewById(R.id.main_right_top);
        bottomRight = (Button) findViewById(R.id.main_right_bottom);
        forward = (Button) findViewById(R.id.main_forward);

        topLeft.setOnClickListener(listener);
        bottomLeft.setOnClickListener(listener);
        topRight.setOnClickListener(listener);
        bottomRight.setOnClickListener(listener);
        forward.setOnClickListener(listener);

        new GetNTPAsynctask().execute();
    }

    class GetNTPAsynctask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            SntpClient client = new SntpClient();
            if (client.requestTime("pool.ntp.org", 30000)) {
                long now = client.getNtpTime();
                Logga.i("Current NTP time: " + now);
            }
            return true;
        }
    }

    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            switch (view.getId()) {
                case R.id.main_left_top:
                    Logga.i("top left");
                    intent = new Intent(MainActivity.this, VideoActivity.class);
                    intent.putExtra(VIDEO_ID_TAG, R.raw.tl);
                    startActivity(intent);
                    break;
                case R.id.main_left_bottom:
                    intent = new Intent(MainActivity.this, VideoActivity.class);
                    intent.putExtra(VIDEO_ID_TAG, R.raw.bl);
                    startActivity(intent);
                    break;
                case R.id.main_right_bottom:
                    intent = new Intent(MainActivity.this, VideoActivity.class);
                    intent.putExtra(VIDEO_ID_TAG, R.raw.br);
                    startActivity(intent);
                    break;
                case R.id.main_right_top:
                    intent = new Intent(MainActivity.this, VideoActivity.class);
                    intent.putExtra(VIDEO_ID_TAG, R.raw.tr);
                    startActivity(intent);
                    break;
                case R.id.main_forward:
                    intent = new Intent(MainActivity.this, VideoForwardActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };
}
