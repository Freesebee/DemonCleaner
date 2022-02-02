package com.example.demoncleaner.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;

import com.example.demoncleaner.models.Streak;
import com.example.demoncleaner.viewmodels.StreakViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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

import org.w3c.dom.Text;

import java.util.List;

public class StreaksDataActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private StreakViewModel streakViewModel;
    public static final int NEW_STREAK_ACTIVITY_REQUEST_CODE = 1;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    public StreaksDataActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streaks_data);

        recyclerView = (RecyclerView) findViewById(R.id.streaksRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new StreakAdapter();
        recyclerView.setAdapter(mAdapter);

        streakViewModel = new ViewModelProvider(this).get(StreakViewModel.class);
        streakViewModel.findAll().observe(this, streaks -> ((StreakAdapter)mAdapter).setStreaks(streaks));
    }

    private class StreakHolder extends RecyclerView.ViewHolder {

        private TextView StreakNote;
        private TextView startDateTextView;
        private TextView endDateTextView;

        public StreakHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.streak_list_item, parent, false));
            StreakNote = (TextView) itemView.findViewById(R.id.streakNote);
            startDateTextView = (TextView) itemView.findViewById(R.id.startDateTextView);
            endDateTextView = (TextView) itemView.findViewById(R.id.endDateTextView);
        }

        public StreakHolder(@NonNull View itemView) {
            super(itemView);
            StreakNote = (TextView) itemView.findViewById(R.id.streakNote);
            startDateTextView = (TextView) itemView.findViewById(R.id.startDateTextView);
            endDateTextView = (TextView) itemView.findViewById(R.id.endDateTextView);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        public void bind(Streak streak) {

            @SuppressLint("SimpleDateFormat") SimpleDateFormat sm = new SimpleDateFormat("MM-dd-yyyy");

            String strStartDate = sm.format(streak.getStartDate());
            startDateTextView.setText(strStartDate);

            String strEndDate = sm.format(streak.getStartDate());
            endDateTextView.setText(strEndDate);

            String comment = "No comment";
            if (streak.getComment() != null) comment = streak.getComment();
            StreakNote.setText(comment);
        }
    }

    private class StreakAdapter extends  RecyclerView.Adapter<StreakHolder> {

        private List<Streak> streaks;

        @NonNull
        @Override
        public StreakHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.streak_list_item, parent, false);
            return new StreakHolder(view);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onBindViewHolder(@NonNull StreakHolder holder, int position) {
            if(streaks != null) {
                holder.bind(streaks.get(position));
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
}