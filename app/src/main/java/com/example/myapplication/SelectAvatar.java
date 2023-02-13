package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SelectAvatar extends AppCompatActivity {

    private ImageView[] ivs;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView imageView5;
    private ImageView imageView6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_avatar);

        setTitle("Select Avatar");

        imageView1 = findViewById(R.id.imageView1Fragment);
        imageView2 = findViewById(R.id.imageView2Fragment);
        imageView3 = findViewById(R.id.imageView3Fragment);
        imageView4 = findViewById(R.id.imageView4Fragment);
        imageView5 = findViewById(R.id.imageView5Fragment);
        imageView6 = findViewById(R.id.imageView6Fragment);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMain = new Intent();
                toMain.putExtra("avatar", R.drawable.avatar_f_1);
                setResult(RESULT_OK, toMain);
                finish();
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMain = new Intent();
                toMain.putExtra("avatar", R.drawable.avatar_m_3);
                setResult(RESULT_OK, toMain);
                finish();
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMain = new Intent();
                toMain.putExtra("avatar", R.drawable.avatar_f_2);
                setResult(RESULT_OK, toMain);
                finish();
            }
        });
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMain = new Intent();
                toMain.putExtra("avatar", R.drawable.avatar_m_2);
                setResult(RESULT_OK, toMain);
                finish();
            }
        });
        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMain = new Intent();
                toMain.putExtra("avatar", R.drawable.avatar_f_3);
                setResult(RESULT_OK, toMain);
                finish();
            }
        });
        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMain = new Intent();
                toMain.putExtra("avatar", R.drawable.avatar_m_1);
                setResult(RESULT_OK, toMain);
                finish();
            }
        });
    }
}