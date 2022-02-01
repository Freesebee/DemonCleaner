package com.example.demoncleaner.infrastructure.room_databases;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.demoncleaner.infrastructure.converters.DateConverters;
import com.example.demoncleaner.infrastructure.data_access_objects.StreakDAO;
import com.example.demoncleaner.models.Streak;

import java.sql.Date;
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
                            UserProgressDatabase.class, "streak_db")
                            .addCallback(callback) // TODO: Remove after tests
                            .build();
                }
            }
        }

        return _instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            databaseWriteExecutor.execute(() -> {
                StreakDAO dao = _instance.streakDAO();
                dao.deleteAll();

                Streak streak = new Streak();
                streak.setStartDate(new Date(2022,1,23));
                streak.setEndDate(new Date(2022,1,26));
                dao.insert(streak);

                streak = new Streak();
                streak.setStartDate(new Date(2022,1,26));
                streak.setEndDate(new Date(2022,1,29));
                dao.insert(streak);

                streak = new Streak();
                streak.setStartDate(new Date(2022,1,29));
                streak.setEndDate(new Date(2022,1, 31));
                dao.insert(streak);
            });
        }
    };
}
