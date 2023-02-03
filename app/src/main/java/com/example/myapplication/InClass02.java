package com.example.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class InClass02 extends AppCompatActivity {

    public static String Profile_Key = "profile";
    private EditText editTextName;
    private EditText editTextEmail;
    private ImageView imageViewAvatar;

    private TextView textViewIUse;

    private RadioButton radioButtonAndriod;

    private RadioButton radioButtoniOS;

    private RadioGroup radioGroup;

    private SeekBar seekBarMood;

    private ImageView imageViewMood;

    private Button buttonSubmit;


    private TextView textViewCurrentMood2;

    private Profile profile = new Profile(0, "", "", "", "", 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class02);

        setTitle("Edit Profile Activity");

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        imageViewAvatar = findViewById(R.id.imageViewAvatar);
        textViewIUse = findViewById(R.id.textViewIUse);
        radioButtonAndriod = findViewById(R.id.radioButtonAndriod);
        radioButtoniOS = findViewById(R.id.radioButtoniOS);
        seekBarMood = findViewById(R.id.seekBarMood);
        imageViewMood = findViewById(R.id.imageViewMood);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        radioGroup = findViewById(R.id.radioGroup);
        textViewCurrentMood2 = findViewById(R.id.textViewCurrentMood2);

        imageViewMood.setImageDrawable(getDrawable(R.drawable.happy));
        textViewCurrentMood2.setText("Happy");
        profile.setMood("Happy");
        profile.setMoodImage(R.drawable.happy);

        // contract for intent to selecting avatar
        ActivityResultLauncher<Intent> selectAvatarForResult
                = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            int id = result.getData().getIntExtra("avatar", -1);
                            imageViewAvatar.setImageDrawable(getDrawable(id));
                            profile.setAvatar(id);
                        }
                    }
                });

        // navigate to avatar page
        imageViewAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToAvatar = new Intent(InClass02.this, SelectAvatar.class);
                selectAvatarForResult.launch(intentToAvatar);
            }
        });

        // get progress of seekbar
        seekBarMood.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int progress = seekBarMood.getProgress();
                if (progress == 0) {
                    imageViewMood.setImageDrawable(getDrawable(R.drawable.angry));
                    textViewCurrentMood2.setText("Angry");
                    profile.setMood("Angry");
                    profile.setMoodImage(R.drawable.angry);
                } else if (progress == 1) {
                    imageViewMood.setImageDrawable(getDrawable(R.drawable.sad));
                    textViewCurrentMood2.setText("Sad");
                    profile.setMood("Sad");
                    profile.setMoodImage(R.drawable.sad);
                } else if (progress == 2) {
                    imageViewMood.setImageDrawable(getDrawable(R.drawable.happy));
                    textViewCurrentMood2.setText("Happy");
                    profile.setMood("Happy");
                    profile.setMoodImage(R.drawable.happy);
                } else {
                    imageViewMood.setImageDrawable(getDrawable(R.drawable.awesome));
                    textViewCurrentMood2.setText("Awesome");
                    profile.setMood("Awesome");
                    profile.setMoodImage(R.drawable.awesome);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checked = findViewById(i);
                profile.setDevice(checked.getText().toString());
            }
        });
        // submit button to display activity
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check name & email
                if (checkInputs(editTextName.getText().toString(), editTextEmail.getText().toString()) != 0) {
                    Toast.makeText(InClass02.this, "Invalid name or email",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                // check if avatar selected
                if (Objects.equals(imageViewAvatar.getDrawable(), R.drawable.select_avatar)) {
                    Toast.makeText(InClass02.this, "Please select a avatar",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                // check if device type selected
                if (!radioButtoniOS.isChecked() && !radioButtonAndriod.isChecked()) {
                    Toast.makeText(InClass02.this, "Please select a device type",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(InClass02.this, DisplayActivity.class);
                profile.setName(editTextName.getText().toString());
                profile.setEmail(editTextEmail.getText().toString());
                intent.putExtra(Profile_Key, profile);

                startActivity(intent);
            }

            /**
             * Checks the validity of user inputs.
             * @param name
             * @param email
             * @return -1 if invalid
             */
            private int checkInputs(String name, String email) {
                if (name.equals("") || !email.matches("^(.+)@(\\S+)$") ) {
                    return -1;
                }
                return 0;
            }
        });

    }
}