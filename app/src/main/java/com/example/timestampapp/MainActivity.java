package com.example.timestampapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.timestampapp.dbs.AppDatabase;
import com.example.timestampapp.dbs.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mainListView;
    private ArrayAdapter mainListAdapter;
    private TextView recentTimeStampTextView;
    private TimeStamp recentTimeStamp;
    private List<String> timeStampStrings = new ArrayList<>();
    private List<TimeStamp> timeStamps = new ArrayList<>();
    private DatabaseHelper databaseHelper;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** ビューが多くなってきた場合はデータバインディングとかを検討すべきだが、
         * 今のところは数が少ないので findViewByIdで取得する
        */
        recentTimeStampTextView = findViewById(R.id.recentTimeStampTextView);
        mainListView = findViewById(R.id.mainListView);

        final ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, timeStampStrings);

        mainListView.setAdapter(arrayAdapter);
        mainListAdapter = arrayAdapter;

        // ボタンにイベントリスナーをセットする

        findViewById(R.id.timeStampButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(recentTimeStamp != null){
                    timeStamps.add(0,new TimeStamp());
                    timeStampStrings.add(0,timeStamps.get(0).getDateTimeString());
                    arrayAdapter.notifyDataSetChanged();
                }

                TimeStamp timeStamp = new TimeStamp();
                setRecentTimeStamp(timeStamp);
                databaseHelper.insertTimeStamp(timeStamp);
            }
        });

        handler.post(runnable);
        db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class, "user-databaseB").build();

        databaseHelper = new DatabaseHelper(db.userDao());
        databaseHelper.getTimeStamps(arrayAdapter);
        recentTimeStamp = new TimeStamp();
        databaseHelper.rewriteRecentTimeStamp(recentTimeStamp);
    }

    private void setRecentTimeStamp(TimeStamp ts){
        if(recentTimeStamp != ts){
            recentTimeStamp = ts;
            recentTimeStampTextView.setText("Recent time stamp >> " + ts.getDateTimeString());
        }
    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(recentTimeStamp != null && recentTimeStampTextView != null){
                recentTimeStampTextView.setText("Recent time stamp >> " + recentTimeStamp.getDateTimeString() + " ( " + recentTimeStamp.getElapsedTimeString() +"経過 )");
            }

            handler.postDelayed(this,1000);
        }
    };

}
