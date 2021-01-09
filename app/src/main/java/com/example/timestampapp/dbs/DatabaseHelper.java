package com.example.timestampapp.dbs;

import android.os.Handler;
import android.os.Looper;

import com.example.timestampapp.TimeStamp;

import java.util.concurrent.Executors;

public class DatabaseHelper {

    private BackgroundTask backgroundTask;
    private UserDao userDao;

    public DatabaseHelper(UserDao userDao){
        this.userDao = userDao;
    }

    public void insertTimeStamp(TimeStamp ts){
        backgroundTask = new BackgroundTask(userDao,ts);
        Executors.newSingleThreadExecutor().submit(backgroundTask);
    }

    private static class BackgroundTask implements Runnable{
        BackgroundTask(UserDao userDao, TimeStamp ts){
            this.userDao = userDao;
            timeStamp = ts;
        }

        private final Handler handler = new Handler(Looper.getMainLooper()){

        };

        private UserDao userDao;
        private TimeStamp timeStamp;

        @Override
        public void run() {
            TimeStampEntity tse = new TimeStampEntity();
            tse.id = userDao.getAll().size() + 1;
            tse.msTime = timeStamp.getDateTime().getTime();
            userDao.insertAll(tse);
        }
    }
}
