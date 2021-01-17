package com.example.timestampapp.dbs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.timestampapp.TimeStamp;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM TimeStamp ORDER BY msTime DESC")
    List<TimeStamp> getAll();

    @Query("SELECT * FROM TimeStamp WHERE id IN (:ids)")
    List<TimeStamp> loadAllByIds(int[] ids);

    @Insert
    void insertAll(TimeStamp... stamps);

    @Query("SELECT COUNT(*) FROM TimeStamp")
    long count();

    @Delete
    void delete(TimeStamp stamps);
}
