package com.example.timestampapp;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class TimeStamp {
    private Date dateTime = new Date();
    private String dateTimeString = "";

    public Date getDateTime(){
        return dateTime;
    }

    public String getDateTimeString(){
        if(dateTimeString == ""){
            SimpleDateFormat df = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
            dateTimeString = df.format(getDateTime());
        }

        return dateTimeString;
    }
}
