package com.example.demoncleaner.fragments.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.demoncleaner.R;
import com.example.demoncleaner.activities.BellActivity;
import com.example.demoncleaner.activities.StreaksDataActivity;
import com.example.demoncleaner.models.Streak;
import com.example.demoncleaner.viewmodels.StreakViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ProgressFragment extends Fragment{

    private StreakViewModel streakViewModel;
    private LineChart lineChart;
    private Button modifyProgressButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_progress, container, false);

        lineChart = viewGroup.findViewById(R.id.lineChart);

        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.getDescription().setEnabled(false);

        ArrayList<Entry> yValues = new ArrayList<>();

        modifyProgressButton = viewGroup.findViewById(R.id.modifyProgressButton);
        modifyProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add navigation to Streaks
            }
        });

        streakViewModel = new ViewModelProvider(this).get(StreakViewModel.class);
        streakViewModel.findAll().observe(getViewLifecycleOwner(), streaks -> {
            int xValue = 0;
            for (Streak streak : streaks) {
                long diff = streak.getEndDate().getTime() - streak.getStartDate().getTime();
                int days = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                for (int d = 0; d <= days; d++) {
                    yValues.add(new Entry(xValue++, d));
                }
            }

            LineDataSet set = new LineDataSet(yValues, "Data Set");
            set.setFillAlpha(110);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set);

            LineData data = new LineData(dataSets);

            lineChart.setData(data);
        });

        return viewGroup;

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }
}