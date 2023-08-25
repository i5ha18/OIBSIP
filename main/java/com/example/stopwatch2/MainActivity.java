package com.example.stopwatch2;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvTime;
    private Button btnStart, btnPause, btnPlay, btnReset;
    private long startTime = 0;
    private Handler handler = new Handler();
    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTime = findViewById(R.id.tvTime);
        btnStart = findViewById(R.id.btnStart);
        btnPause = findViewById(R.id.btnPause);
        btnPlay = findViewById(R.id.btnPlay);
        btnReset = findViewById(R.id.btnReset);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStopwatch();
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseStopwatch();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resumeStopwatch();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetStopwatch();
            }
        });
    }

    private Runnable updateTime = new Runnable() {
        public void run() {
            long elapsedTime = System.currentTimeMillis() - startTime;
            int seconds = (int) (elapsedTime / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            int milliseconds = (int) (elapsedTime % 1000);
            tvTime.setText(String.format("%02d:%02d:%03d", minutes, seconds, milliseconds));
            handler.postDelayed(this, 10);
        }
    };

    private void startStopwatch() {
        if (!isRunning) {
            isRunning = true;
            startTime = System.currentTimeMillis();
            handler.post(updateTime);
        }
    }

    private void pauseStopwatch() {
        if (isRunning) {
            isRunning = false;
            handler.removeCallbacks(updateTime);
        }
    }

    private void resumeStopwatch() {
        if (!isRunning) {
            isRunning = true;
            startTime = System.currentTimeMillis() - (System.currentTimeMillis() - startTime);
            handler.post(updateTime);
        }
    }

    private void resetStopwatch() {
        isRunning = false;
        handler.removeCallbacks(updateTime);
        startTime = 0;
        tvTime.setText("00:00:000");
    }
}
