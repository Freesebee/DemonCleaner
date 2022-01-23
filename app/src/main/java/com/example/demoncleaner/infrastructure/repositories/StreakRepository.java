package com.example.demoncleaner.infrastructure.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.demoncleaner.infrastructure.data_access_objects.StreakDAO;
import com.example.demoncleaner.models.Streak;
import com.example.demoncleaner.infrastructure.room_databases.UserProgressDatabase;

import java.util.List;

public class StreakRepository {

    private StreakDAO streakDAO;
    private LiveData<List<Streak>> streaks;

    public StreakRepository(Application application) {
        UserProgressDatabase database = UserProgressDatabase.getInstance(application);
        streakDAO = database.streakDAO();
        streaks = streakDAO.findAll();
    }

    public LiveData<List<Streak>> findAllStreaks() {
        return streaks;
    }

    public void insert(Streak streak) {
        UserProgressDatabase.databaseWriteExecutor.execute(() -> streakDAO.insert(streak));
    }

    public void update(Streak streak) {
        UserProgressDatabase.databaseWriteExecutor.execute(() -> streakDAO.update(streak));
    }

    public void delete(Streak streak) {
        UserProgressDatabase.databaseWriteExecutor.execute(() -> streakDAO.delete(streak));
    }
}
