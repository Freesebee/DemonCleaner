package com.example.demoncleaner.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.demoncleaner.sensors.Accelerometer;
import com.example.demoncleaner.sensors.Gyroscope;
import com.example.demoncleaner.R;

public class BellActivity extends AppCompatActivity {

    private Accelerometer accelerometer;
    private Gyroscope gyroscope;
    private double accelerationCurrentValue;
    private double accelerationPreviousValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bell);

        accelerometer = new Accelerometer(this);
        gyroscope = new Gyroscope(this);

        accelerometer.setListener((tx, ty, tz) -> {
            accelerationCurrentValue = Math.sqrt(tx*tx + ty*ty + tz*tz);
            double changeInAcceleration = Math.abs(accelerationCurrentValue - accelerationPreviousValue);
            accelerationPreviousValue = accelerationCurrentValue;

            if(changeInAcceleration > 4.5f) {

                Intent intent = new Intent(this, MainActivity.class);

                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);


                NotificationCompat.Builder builder = new NotificationCompat
                        .Builder(BellActivity.this, getString(R.string.channel_id))
                        .setContentTitle(getString(R.string.notification_title))
                        .setContentText(getString(R.string.notification_content))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

                notificationManager.notify(1, builder.build());

                switchToMainActivity();
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

    private void switchToMainActivity() {
        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        startActivity(switchActivityIntent);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String channelId = getString(R.string.channel_id);

            CharSequence name = getString(R.string.channel_name);

            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(channelId, name, importance);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);

            notificationManager.createNotificationChannel(channel);
        }
    }
}