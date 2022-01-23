package com.example.demoncleaner.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.demoncleaner.infrastructure.repositories.StreakRepository;
import com.example.demoncleaner.models.Streak;

import java.util.List;

public class StreakViewModel extends AndroidViewModel {

    private StreakRepository streakRepository;
    private LiveData<List<Streak>> streaks;

    public StreakViewModel(@NonNull Application application) {

        super(application);

        streakRepository = new StreakRepository(application);
        streaks = streakRepository.findAllStreaks();
    }

    public LiveData<List<Streak>> findAll() {
        return streaks;
    }

    public void insert(Streak streak) {
        streakRepository.insert(streak);
    }

    public void update(Streak streak) {
        streakRepository.update(streak);
    }

    public void delete(Streak streak) {
        streakRepository.delete(streak);
    }
}
