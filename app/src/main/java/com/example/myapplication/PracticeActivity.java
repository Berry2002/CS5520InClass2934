package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PracticeActivity extends AppCompatActivity {
    private Button buttonLog;
    private Button buttonToast;
    public static String TAG = "demo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        setTitle("Practice Activity");

        buttonLog = findViewById(R.id.buttonLog);
        buttonToast = findViewById(R.id.buttonToast);

        buttonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Practice!Practice!!Practice!!!");
            }
        });

        buttonToast = findViewById(R.id.buttonToast);
        buttonToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PracticeActivity.this, "Now push to GitHub and Submit!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}