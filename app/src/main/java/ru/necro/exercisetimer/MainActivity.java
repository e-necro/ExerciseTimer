package ru.necro.exercisetimer;

import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timerTextView;
    Button b;
    long workTime = 30;
    long relaxTime = 10;
    boolean toWork = true;

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
///            long millis = System.currentTimeMillis() - workTime;
            ///int seconds = (int) ();
            ///seconds = seconds % 60;

            if (toWork) {
                timerTextView.setTextColor(Color.BLACK);
                timerTextView.setText(String.format("%02d",workTime--));
                timerTextView.postDelayed(this, 1000);
                if (workTime <= 0) {
                    workTime = 30;
                    toWork = false;
                    timerTextView.setTextColor(Color.parseColor("#00FF00"));
                    try {
                        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                        r.play();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                timerTextView.setText(String.format("%02d",relaxTime--));
                timerTextView.postDelayed(this,1000);
                if (relaxTime<=0) {
                    relaxTime = 10;
                    toWork = true;
                    timerTextView.setText(String.format("%02d", workTime--));
                    try {
                        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                        r.play();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = (TextView) findViewById(R.id.timerTextView);

         b = (Button) findViewById(R.id.button);
        b.setText("start");
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                if (b.getText().equals("stop")) {
                    timerHandler.removeCallbacks(timerRunnable);

                    b.setText("start.");
                } else {
                  ///  workTime = System.currentTimeMillis();
                    workTime = 30;
                    timerHandler.postDelayed(timerRunnable, 2000);
                    b.setText("stop");
                }
            }
        });


    }

    @Override
    public void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
        Button b = (Button)findViewById(R.id.button);
        b.setText("start");
    }
}
