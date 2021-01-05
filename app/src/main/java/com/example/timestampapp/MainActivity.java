package com.example.timestampapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mainListView;
    private ArrayAdapter mainListAdapter;
    private TextView recentTimeStampTextView;
    private TimeStamp recentTimeStamp;
    private List<String> timeStampStrings = new ArrayList<>();
    private List<TimeStamp> timeStamps = new ArrayList<>();

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
                    timeStamps.add(0,recentTimeStamp);
                    timeStampStrings.add(0,recentTimeStamp.getDateTimeString());
                    arrayAdapter.notifyDataSetChanged();
                }

                setRecentTimeStamp(new TimeStamp());
            }
        });
    }

    private void setRecentTimeStamp(TimeStamp ts){
        if(recentTimeStamp != ts){
            recentTimeStamp = ts;
            recentTimeStampTextView.setText("Recent time stamp >> " + ts.getDateTimeString());
        }
    }
}
