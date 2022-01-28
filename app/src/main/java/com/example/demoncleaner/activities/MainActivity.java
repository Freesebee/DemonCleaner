package com.example.demoncleaner.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.demoncleaner.R;
import com.example.demoncleaner.fragments.main.ProgressFragment;
import com.example.demoncleaner.fragments.main.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_nav_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentById(itemId);

                if(fragment == null) {
                    switch(itemId) {
                        case R.id.progress:
                            fragment = new ProgressFragment();
                            break;
                        case R.id.settings:
                            fragment = new SettingsFragment();
                            break;
                    }

                    fragmentManager.beginTransaction().add(itemId, fragment).commit();
                }

                return true;
            }
        });
    }
}