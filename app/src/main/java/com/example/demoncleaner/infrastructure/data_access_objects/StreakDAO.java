package com.example.demoncleaner.infrastructure.data_access_objects;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.demoncleaner.models.Streak;

import java.util.List;

@Dao
public interface StreakDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Streak streak);

    @Update
    void update(Streak streak);

    @Delete
    void delete(Streak streak);

    @Query("DELETE FROM streak")
    void deleteAll();

    @Query("SELECT * FROM streak ORDER BY startDate")
    LiveData<List<Streak>> findAll();
}
