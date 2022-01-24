package com.example.demoncleaner;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Gyroscope {

    public interface Listener {
        void onRotation(float rx, float ry, float rz);
    }

    private Listener listener;

    private SensorManager sensorManager;
    private Sensor gyroscope;
    private SensorEventListener sensorEventListener;

    public Gyroscope(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (listener != null) {
                    listener.onRotation(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
                //TODO: Rethink why should you use this
            }
        };
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void register() {
        sensorManager.registerListener(sensorEventListener, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregister() {
        sensorManager.unregisterListener(sensorEventListener);
    }
}
