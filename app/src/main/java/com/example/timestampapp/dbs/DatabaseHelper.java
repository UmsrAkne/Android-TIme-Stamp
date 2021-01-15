package com.example.timestampapp.dbs;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.timestampapp.TimeStamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    public void getTimeStamps(ArrayAdapter<String> adapter){
        BackgroundRead backgroundRead = new BackgroundRead(userDao, adapter);
        Executors.newSingleThreadExecutor().submit(backgroundRead);
    }

    public void rewriteRecentTimeStamp(TimeStamp ts){
        ReadRecentTimeStampTask r = new ReadRecentTimeStampTask(userDao, ts);
        Executors.newSingleThreadExecutor().submit(r);
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


    private static class BackgroundRead implements Runnable{
        private final ArrayAdapter adapter;
        private UserDao userDao;
        private List<TimeStampEntity> tss;

        BackgroundRead(UserDao userDao, ArrayAdapter adapter){
            this.userDao = userDao;
            this.adapter = adapter;
        }

        private final Handler handler = new Handler(Looper.getMainLooper()){
        };

        @Override
        public void run() {
            List<TimeStampEntity> tss = userDao.getAll();
            List<String> timeStampStrings = new ArrayList<String>();
            adapter.clear();
            for (TimeStampEntity te:tss ) {
                adapter.add( new TimeStamp(new Date(te.msTime)).getDateTimeString());
            }

            adapter.notifyDataSetChanged();
        }
    }

    private static class ReadRecentTimeStampTask implements Runnable{
        private final TimeStamp timeStamp;
        private UserDao userDao;

        ReadRecentTimeStampTask(UserDao userDao, TimeStamp ts){
            this.userDao = userDao;
            this.timeStamp = ts;
        }

        private final Handler handler = new Handler(Looper.getMainLooper()){
        };

        @Override
        public void run() {
            Date dt = new Date(userDao.getAll().get(0).msTime);
            timeStamp.setDate(dt);
        }

    }

}
