package com.example.timestampapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ListView mainListView;
    TextView recentTimeStampTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** ビューが多くなってきた場合はデータバインディングとかを検討すべきだが、
         * 今のところは数が少ないので findViewByIdで取得する
        */
        recentTimeStampTextView = findViewById(R.id.recentTimeStampTextView);
        mainListView = findViewById(R.id.mainListView);

        String[] texts = {"test"};

        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,texts);

        mainListView.setAdapter(arrayAdapter);
    }
}
