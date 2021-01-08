package com.example.timestampapp.dbs;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TimeStampEntity {

    @PrimaryKey
    public int id;

    public long msTime;
}
