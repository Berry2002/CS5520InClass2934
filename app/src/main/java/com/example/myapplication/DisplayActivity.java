package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {

    private ImageView imageViewDisplayAvatar;
    private TextView textViewDisplayName;

    private TextView textViewDisplayEmail;

    private TextView textViewDisplayIUse;

    private TextView textViewDisplayIAm;

    private ImageView imageViewDisplayMood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        setTitle("Display Activity");

        imageViewDisplayAvatar = findViewById(R.id.imageViewDisplayAvatar);
        textViewDisplayName = findViewById(R.id.textViewDisplayName);
        textViewDisplayEmail = findViewById(R.id.textViewDisplayEmail);
        textViewDisplayIUse = findViewById(R.id.textViewDisplayIUse);
        textViewDisplayIAm = findViewById(R.id.textViewDisplayIAm);
        imageViewDisplayMood = findViewById(R.id.imageViewDisplayMood);

        if (getIntent() != null && getIntent().getExtras() != null) {
            Profile profile = getIntent().getParcelableExtra(InClass02.Profile_Key);
            imageViewDisplayAvatar.setImageDrawable(getDrawable(profile.getAvatar()));
            textViewDisplayName.setText("Name: " + profile.getName());
            textViewDisplayEmail.setText("Email: " + profile.getEmail());
            textViewDisplayIUse.setText("I use " + profile.getDevice());
            textViewDisplayIAm.setText("I am " + profile.getMood());
            imageViewDisplayMood.setImageDrawable(getDrawable(profile.getMoodImage()));
        }
    }
}