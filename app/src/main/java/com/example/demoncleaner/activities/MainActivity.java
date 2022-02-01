package com.example.demoncleaner.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.example.demoncleaner.R;
import com.example.demoncleaner.fragments.main.ProgressFragment;
import com.example.demoncleaner.fragments.main.SettingsFragment;
import com.example.demoncleaner.models.Streak;
import com.example.demoncleaner.viewmodels.StreakViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {

    private StreakViewModel streakViewModel;
    private DayChangedBroadcastReceiver dayChangedReceiver;
    private final IntentFilter filter = new IntentFilter();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        streakViewModel = new ViewModelProvider(this).get(StreakViewModel.class);

        //TODO: Fix
//        if(checkDayPassed()) {
//            switchToBellActivity();
//        }

        dayChangedReceiver = new DayChangedBroadcastReceiver();

        filter.addAction(Intent.ACTION_TIME_CHANGED);
        filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        filter.addAction(Intent.ACTION_DATE_CHANGED);

        registerReceiver(dayChangedReceiver, filter);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ProgressFragment()).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_menu);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            Fragment fragment = null;

            if (item.getItemId() == R.id.progressFragment) {

                fragment = new ProgressFragment();
            }
            else if (item.getItemId() == R.id.settingsFragment) {

                fragment = new SettingsFragment();
            }

            if(fragment != null) {

                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
                return true;
            }

            return false;
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(dayChangedReceiver);
    }

    private class DayChangedBroadcastReceiver extends com.example.demoncleaner.broadcastReceivers.DayChangedBroadcastReceiver {

        @Override
        protected void onDayChanged() {
            Toast toast = Toast.makeText(getApplicationContext(), R.string.user_cheating, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean checkDayPassed() {

        AtomicBoolean result = new AtomicBoolean(false);

        streakViewModel.findAll().observe(this, streaks -> {

            Streak last = streaks.get(0);

            //TODO: Find why it's not working
            java.util.Date currentDate = java.util.Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

            for (Streak streak : streaks) {
                if(last == null || betweenDates(last.getEndDate(), streak.getEndDate()) > 0) {
                    last = streak;
                }
            }

            result.set(betweenDates(last.getEndDate(), currentDate) > 0);
        });

        return result.get();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private long betweenDates(java.util.Date firstDate, java.util.Date secondDate) {
        return ChronoUnit.DAYS.between(firstDate.toInstant(), secondDate.toInstant());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private long betweenDates(Date firstDate, Date secondDate)
    {
        return ChronoUnit.DAYS.between(firstDate.toInstant(), secondDate.toInstant());
    }

    private void switchToBellActivity() {
        Intent switchActivityIntent = new Intent(this, BellActivity.class);
        startActivity(switchActivityIntent);
    }
}