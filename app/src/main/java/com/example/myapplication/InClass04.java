package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InClass04 extends AppCompatActivity {

    private TextView textViewTimes;
    private SeekBar seekBarComp;
    private TextView textViewMinimum;
    private TextView textViewMaximum;
    private TextView textViewAverage;
    private Button buttonGenerate;

    private ExecutorService threadPool;

    private Handler messageQueue;
    private int num;
    public static List<Double> nums;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class04);

        setTitle("Number Generator");
        textViewTimes = findViewById(R.id.textViewTimes);
        seekBarComp = findViewById(R.id.seekBarComp);
        textViewMinimum = findViewById(R.id.textViewMinimum);
        textViewMaximum = findViewById(R.id.textViewMaximum);
        textViewAverage = findViewById(R.id.textViewAverage);
        buttonGenerate = findViewById(R.id.buttonGenerate);
        progressBar = findViewById(R.id.progressBar);
        threadPool = Executors.newFixedThreadPool(4);

        seekBarComp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                num = seekBar.getProgress();
                textViewTimes.setText(num + " times");
                nums = HeavyWork.getArrayNumbers(num);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


        messageQueue = new Handler(new Handler.Callback() {

            @Override
            public boolean handleMessage(@NonNull Message message) {
                switch (message.what) {
                    case HeavyWork.STATUS_START:
                        break;
                    case HeavyWork.STATUS_STOP:
                        break;
                    case HeavyWork.STATUS_PROGRESS:
                        Bundle bundle = message.getData();

                        double min = bundle.getDouble(HeavyWork.MIN_PROGRESS);
                        textViewMinimum.setText("" + min);

                        double max = bundle.getDouble(HeavyWork.MAX_PROGRESS);
                        textViewMaximum.setText("" + max);

                        double avg = bundle.getDouble(HeavyWork.AVG_PROGRESS);
                        textViewAverage.setText("" + avg);

                        double donePercent = bundle.getDouble(HeavyWork.AVG_PROGRESS);
                        progressBar.setProgress((int)donePercent);
                        break;
                }
                return false;
            }
        });

        buttonGenerate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                threadPool.execute(new HeavyWork("Do this carefully", messageQueue));
            }
        });
    }
}
