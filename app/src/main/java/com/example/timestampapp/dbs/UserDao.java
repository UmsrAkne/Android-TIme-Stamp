package com.example.timestampapp.dbs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM TimeStampEntity ORDER BY msTime DESC")
    List<TimeStampEntity> getAll();

    @Query("SELECT * FROM TimeStampEntity WHERE id IN (:ids)")
    List<TimeStampEntity> loadAllByIds(int[] ids);

    @Insert
    void insertAll(TimeStampEntity... stamps);

    @Delete
    void delete(TimeStampEntity stamps);
}
