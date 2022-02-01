package com.example.demoncleaner.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.demoncleaner.models.Streak;
import com.example.demoncleaner.viewmodels.StreakViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoncleaner.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class StreaksDataActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private StreakViewModel streakViewModel;
    public static final int NEW_STREAK_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streaks_data);

        RecyclerView recyclerView = findViewById(R.id.streaksRecyclerView);
        final StreakAdapter adapter = new StreakAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        streakViewModel = new ViewModelProvider(this).get(StreakViewModel.class);
        streakViewModel.findAll().observe(this, new Observer<List<Streak>>() {
            @Override
            public void onChanged(List<Streak> streaks) {
                adapter.setStreaks(streaks);
            }
        });

        FloatingActionButton addStreakButton = findViewById(R.id.add_button);
        addStreakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StreaksDataActivity.this, EditStreakActivity.class);
                startActivityForResult(intent, NEW_STREAK_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_streaks_data);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private class StreakHolder extends RecyclerView.ViewHolder {

        private TextView StreakNote;

        public StreakHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.streak_list_item, parent, false));
            StreakNote = itemView.findViewById(R.id.streak_text_view);
        }

        public void bind(Streak streak) {
            StreakNote.setText(streak.getComment());
        }
    }

    private class StreakAdapter extends  RecyclerView.Adapter<StreakHolder> {

        private List<Streak> streaks;

        @NonNull
        @Override
        public StreakHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new StreakHolder(getLayoutInflater(), parent);
        }

        @Override
        public void onBindViewHolder(@NonNull StreakHolder holder, int position) {
            if(streaks != null) {
                Streak streak = streaks.get(position);
                holder.bind(streak);
            } else {
                Log.d("StreaksDataActivity", getString(R.string.empty_streak_list));
            }
        }

        @Override
        public int getItemCount() {
            if(streaks != null) return streaks.size();
            else return 0;
        }

        public void setStreaks(List<Streak> streaks) {
            this.streaks = streaks;
            notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NEW_STREAK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            Streak streak = new Streak();
            streak.setComment(data.getStringExtra(EditStreakActivity.EXTRA_EDIT_STREAK_NOTE));
            streakViewModel.insert(streak);

            Snackbar.make(findViewById(R.id.main_layout), getString(R.string.streak_added), Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(findViewById(R.id.main_layout),
                    getString(R.string.empty_not_saved),
                    Snackbar.LENGTH_LONG)
                    .show();
        }
    }
}