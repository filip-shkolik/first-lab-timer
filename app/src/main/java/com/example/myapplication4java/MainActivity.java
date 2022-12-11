package com.example.myapplication4java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnTimer;
    Button btnSettings;
    TextClock textdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textdate = findViewById(R.id.textdateid);
        btnTimer = (Button) findViewById(R.id.btnTimer);
        btnTimer.setOnClickListener(this);
        btnSettings = (Button) findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnTimer:
                Intent intent = new Intent(this, Timer.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        switch (view.getId()) {
            case R.id.btnSettings:
                Intent intent = new Intent(this, Settings.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}


