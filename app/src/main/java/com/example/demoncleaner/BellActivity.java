package com.example.demoncleaner;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

public class BellActivity extends AppCompatActivity {

    private Accelerometer accelerometer;
    private Gyroscope gyroscope;
    private double accelerationCurrentValue;
    private double accelerationPreviousValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accelerometer = new Accelerometer(this);
        gyroscope = new Gyroscope(this);

        accelerometer.setListener((tx, ty, tz) -> {
            accelerationCurrentValue = Math.sqrt(tx*tx + ty*ty + tz*tz);
            double changeInAcceleration = Math.abs(accelerationCurrentValue - accelerationPreviousValue);
            accelerationPreviousValue = accelerationCurrentValue;

            if(changeInAcceleration > 8.0f) {
                getWindow().getDecorView().setBackgroundColor(Color.RED);
            }
            else if(changeInAcceleration > 4.5f) {
                getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
            }
            else {
                getWindow().getDecorView().setBackgroundColor(Color.GREEN);
            }
        });

        gyroscope.setListener((rx, ry, rz) -> {
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        accelerometer.register();
        gyroscope.register();
    }

    @Override
    protected void onPause() {
        super.onPause();

        accelerometer.unregister();
        gyroscope.unregister();
    }
}