package com.example.myapplication4java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Timer extends AppCompatActivity {

    Button startbtn, pausebtn, resetbtn, lapbtn;
    TextView laps_show;
    Chronometer chronometer;
    Handler customHandler = new Handler();
    LinearLayout container;

    long stopTime = 0;
    long startTime=0L, timeInMilliseconds=0L, timeSwapBuff=0L, updateTime=0L;

    Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis()-startTime;
            updateTime = timeSwapBuff+timeInMilliseconds;
            int seconds=(int)(updateTime/1000);
            int minutes=seconds/60;
            seconds%=60;
            int miliseconds=(int)(updateTime%1000);

            chronometer.setText(""+minutes+":"+String.format("%02d",seconds)+":"
                                                +String.format("%03d", miliseconds));


            customHandler.postDelayed(updateTimerThread,0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        startbtn = (Button) findViewById(R.id.stratBtn);
        pausebtn = (Button) findViewById(R.id.pauseBtn);
        resetbtn = (Button) findViewById(R.id.btnClock);
        lapbtn = (Button) findViewById(R.id.lap_btn);
        chronometer = (Chronometer) findViewById(R.id.Chronometer);
        container = (LinearLayout) findViewById(R.id.container);

        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.setBase(SystemClock.elapsedRealtime()+stopTime);
                chronometer.start();
                startbtn.setVisibility(View.GONE);
                pausebtn.setVisibility(View.VISIBLE);
            }
        });

        pausebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopTime=chronometer.getBase()-SystemClock.elapsedRealtime();
                chronometer.stop();
                startbtn.setVisibility(View.VISIBLE);
                pausebtn.setVisibility(View.GONE);
            }
        });

        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                stopTime=0;
                chronometer.stop();
                startbtn.setVisibility(View.VISIBLE);
                pausebtn.setVisibility(View.GONE);
                container.removeAllViews();
            }
        });

        lapbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View addView = inflater.inflate(R.layout.row,null);
                TextView txtValue = (TextView) addView.findViewById(R.id.txtContent);
                txtValue.setText(chronometer.getText());
                container.addView(addView);
            }
        });

//        lapbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String strlap=chronometer.getText().toString();
//                laps_show.setText(strlap);
//            }
//        });
    }
}