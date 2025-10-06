package com.example.lab2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // --- UI widgets ---
    private TextView counterDisplay;
    private Button incrementButton;
    private Button decrementButton;
    private Button resetButton;
    private Button stepToggleButton;

    // --- state ---
    private int currentValue = 0;
    private int stepSize = 1;   // default step is 1, toggles to 2

    // --- keys for rotation state ---
    private static final String STATE_VALUE = "state_value";
    private static final String STATE_STEP = "state_step";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // restore saved state if available
        if (savedInstanceState != null) {
            currentValue = savedInstanceState.getInt(STATE_VALUE, 0);
            stepSize = savedInstanceState.getInt(STATE_STEP, 1);
        }

        // link UI elements
        counterDisplay = findViewById(R.id.outputLabel);
        incrementButton = findViewById(R.id.addBtn);
        decrementButton = findViewById(R.id.subtractBtn);
        resetButton = findViewById(R.id.resetBtn);
        stepToggleButton = findViewById(R.id.stepBtn);

        // initial UI setup
        showCurrentValue();
        updateStepLabel();

        // --- button actions ---
        incrementButton.setOnClickListener(v -> increaseCounter());
        decrementButton.setOnClickListener(v -> decreaseCounter());
        resetButton.setOnClickListener(v -> resetCounter());
        stepToggleButton.setOnClickListener(v -> toggleStep());
    }

    // --- logic methods ---

    private void increaseCounter() {
        currentValue += stepSize;
        showCurrentValue();
    }

    private void decreaseCounter() {
        currentValue -= stepSize;
        showCurrentValue();
    }

    private void resetCounter() {
        currentValue = 0;
        stepSize = 1;
        showCurrentValue();
        updateStepLabel();
    }

    private void toggleStep() {
        stepSize = (stepSize == 1) ? 2 : 1;
        updateStepLabel();
    }

    private void showCurrentValue() {
        counterDisplay.setText(String.valueOf(currentValue));
    }

    private void updateStepLabel() {
        stepToggleButton.setText("Step: ±" + stepSize);
    }

    // save state during rotation
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_VALUE, currentValue);
        outState.putInt(STATE_STEP, stepSize);
    }
}
