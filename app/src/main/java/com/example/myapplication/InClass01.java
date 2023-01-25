package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class InClass01 extends AppCompatActivity  {
    private ConstraintLayout rootLayout;
    private Button buttonCalculate;
    private TextView textViewClick;

    private EditText editTextWeight;

    private EditText editTextFeet;

    private EditText editTextInches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class01);
        setTitle("BMI Calculator");

        rootLayout = findViewById(R.id.rootLayout);
        buttonCalculate = findViewById(R.id.buttonCalculateBMI);
        textViewClick = findViewById(R.id.textViewClick);
        editTextWeight = findViewById(R.id.editTextWeight);
        editTextFeet = findViewById(R.id.editTextFeet);
        editTextInches = findViewById(R.id.editTextInches);


        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check_inputs(editTextWeight.getText().toString(), editTextFeet.getText().toString(),
                        editTextInches.getText().toString()) == -1) {
                    return;
                }
                String bmi = calculate_bmi(editTextWeight.getText().toString(),
                        editTextFeet.getText().toString(), editTextInches.getText().toString());

                String status = check_status(bmi);
                String result = "Your BMI: " + bmi + "\nYou are " + status;
                textViewClick.setText(result);
                Toast.makeText(InClass01.this, "BMI calculated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Checks for validity of inputs.
     * @param weight
     * @param feet
     * @param inches
     */
    private int check_inputs(String weight, String feet, String inches) {
        if (weight.equals("") || feet.equals("") || inches.equals("")) {
            Toast.makeText(InClass01.this, "Invalid Inputs", Toast.LENGTH_SHORT).show();
            return -1;
        }
        else if (Integer.parseInt(weight) == 0) {
            Toast.makeText(InClass01.this, "Invalid Inputs", Toast.LENGTH_SHORT).show();
            return -1;

        }
        else if (Integer.parseInt(feet) == 0 && Integer.parseInt(inches) == 0) {
            Toast.makeText(InClass01.this, "Invalid Inputs", Toast.LENGTH_SHORT).show();
            return -1;
        }
        return 0;
    }

    /**
     * Returns the status of the BMI.
     * @param bmi
     * @return status of the BMI input
     */
    private String check_status(String bmi) {
        double bmi_num = Double.parseDouble(bmi);
        if (bmi_num <= 18.5) {
            return "Underweight";
        } else if (bmi_num <= 24.9) {
            return "Normal weight";
        } else if (bmi_num <= 29.9) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }

    /**
     * Returns the calculated BMI based on the inputs.
     * @param weight
     * @param feet
     * @param inches
     * @return the BMI
     */
    private String calculate_bmi(String weight, String feet, String inches) {
        double weight_num = Double.parseDouble(weight);
        int feet_int = Integer.parseInt(feet);
        int inches_int = Integer.parseInt(inches) + (feet_int * 12);
        double bmi = weight_num / (inches_int * inches_int) * 703;
        return String.valueOf(bmi);
    }
}