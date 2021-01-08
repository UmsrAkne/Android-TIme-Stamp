package com.example.timestampapp.dbs;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TimeStampEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
