package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button buttonPractice;
    private Button buttonInClass01;

    private Button buttonInClass02;

    private Button buttonInClass03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("In Class Assignments");

      buttonPractice = findViewById(R.id.buttonPractice);

        buttonPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PracticeActivity.class);
                startActivity(intent);
            }
        });

        buttonInClass01 = findViewById(R.id.buttonInClass01);
        buttonInClass01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InClass01.class);
                startActivity(intent);
            }
        });

        buttonInClass02 = findViewById(R.id.buttonInClass02);
        buttonInClass02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InClass02.class);
                startActivity(intent);
            }
        });

        buttonInClass03 = findViewById(R.id.buttonInClass03);
        buttonInClass03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InClass03.class);
                startActivity(intent);
            }
        });
    }

}