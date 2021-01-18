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

    public void insertTimeStamp(final TimeStamp timeStamp){
        Executors.newSingleThreadExecutor().submit(
            new Runnable() {

                private final Handler handler = new Handler(Looper.getMainLooper()){ };

                @Override
                public void run() {
                    TimeStamp ts = new TimeStamp();
                    ts.id = userDao.getAll().size() + 1;
                    ts.msTime = timeStamp.getDateTime().getTime();
                    userDao.insertAll(ts);
                }
            }
        );
    }

    public void getTimeStamps(ArrayAdapter<String> adapter){
        final ArrayAdapter adapter_ = adapter;
        Executors.newSingleThreadExecutor().submit(
            new Runnable(){

                private final Handler handler = new Handler(Looper.getMainLooper()){ };

                @Override
                public void run() {
                    List<TimeStamp> tss = userDao.getAll();
                    adapter_.clear();
                    for (TimeStamp ts:tss ) {
                        adapter_.add( new TimeStamp(new Date(ts.msTime)).getDateTimeString());
                    }

                    adapter_.notifyDataSetChanged();
                }
            }
        );
    }

    public void rewriteRecentTimeStamp(TimeStamp ts){
        final TimeStamp tStamp = ts;
        Executors.newSingleThreadExecutor().submit(
            new Runnable(){
                private final Handler handler = new Handler(Looper.getMainLooper()){ };

                @Override
                public void run() {
                    Date dt = new Date(userDao.getAll().get(0).msTime);
                    tStamp.setDate(dt);
                }
            }
        );
    }

}
