package com.example.demoncleaner.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.demoncleaner.DayChangedBroadcastReceiver;
import com.example.demoncleaner.R;
import com.example.demoncleaner.fragments.main.ProgressFragment;
import com.example.demoncleaner.fragments.main.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private DayChangedBroadcastReceiver dayChangedReceiver;
    private final IntentFilter filter = new IntentFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dayChangedReceiver = new DayChangedBroadcastReceiver();

        filter.addAction(Intent.ACTION_TIME_CHANGED);
        filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        filter.addAction(Intent.ACTION_DATE_CHANGED);

        registerReceiver(dayChangedReceiver, filter);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ProgressFragment()).commit();

        bottomNavigationView = findViewById(R.id.bottom_nav_menu);

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

    private class DayChangedBroadcastReceiver extends com.example.demoncleaner.DayChangedBroadcastReceiver {

        @Override
        protected void onDayChanged() {
            Toast toast = Toast.makeText(getApplicationContext(), "CHEATER_PLACEHOLDER", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}