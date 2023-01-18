package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PracticeActivity extends AppCompatActivity {

    private Button button_log;
    public static String TAG = "demo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        setTitle("Practice Activity");
        button_log = findViewById(R.id.button_log);

        button_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        }
    }
}