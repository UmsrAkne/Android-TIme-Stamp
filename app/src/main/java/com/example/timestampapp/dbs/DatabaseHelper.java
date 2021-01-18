package com.example.timestampapp.dbs;

import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;

import com.example.timestampapp.TimeStamp;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

public class DatabaseHelper {

    private UserDao userDao;

    public DatabaseHelper(UserDao userDao){
        this.userDao = userDao;
    }

    public void insertTimeStamp(TimeStamp ts){
        Executors.newSingleThreadExecutor().submit(
            new Runnable() {

                private final Handler handler = new Handler(Looper.getMainLooper()){ };

                @Override
                public void run() {
                    TimeStamp ts = new TimeStamp();
                    ts.id = userDao.getAll().size() + 1;
                    ts.msTime = ts.getDateTime().getTime();
                    userDao.insertAll(ts);
                }
            }
        );
    }

    public void getTimeStamps(ArrayAdapter<String> adapter){
        BackgroundRead backgroundRead = new BackgroundRead(userDao, adapter);
        Executors.newSingleThreadExecutor().submit(backgroundRead);
    }

    public void rewriteRecentTimeStamp(TimeStamp ts){
        ReadRecentTimeStampTask r = new ReadRecentTimeStampTask(userDao, ts);
        Executors.newSingleThreadExecutor().submit(r);
    }

    private static class BackgroundRead implements Runnable{
        private final ArrayAdapter adapter;
        private UserDao userDao;

        BackgroundRead(UserDao userDao, ArrayAdapter adapter){
            this.userDao = userDao;
            this.adapter = adapter;
        }

        private final Handler handler = new Handler(Looper.getMainLooper()){
        };

        @Override
        public void run() {
            List<TimeStamp> tss = userDao.getAll();
            adapter.clear();
            for (TimeStamp ts:tss ) {
                adapter.add( new TimeStamp(new Date(ts.msTime)).getDateTimeString());
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
