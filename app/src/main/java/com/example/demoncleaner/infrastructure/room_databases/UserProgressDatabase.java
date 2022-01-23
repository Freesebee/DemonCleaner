package com.example.demoncleaner.infrastructure.room_databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.demoncleaner.infrastructure.converters.DateConverters;
import com.example.demoncleaner.infrastructure.data_access_objects.StreakDAO;
import com.example.demoncleaner.models.Streak;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Streak.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverters.class})
public abstract class UserProgressDatabase extends RoomDatabase {

    public abstract StreakDAO streakDAO();

    private static volatile UserProgressDatabase _instance;

    public static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static UserProgressDatabase getInstance(final Context context) {
        if (_instance == null) {
            synchronized (UserProgressDatabase.class) {
                if (_instance == null) {
                    _instance = Room.databaseBuilder(context.getApplicationContext(),
                            UserProgressDatabase.class, "streak_db").build();
                }
            }
        }

        return _instance;
    }
}
