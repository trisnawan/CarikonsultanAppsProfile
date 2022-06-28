package com.carikonsultan.apps.consultant.core;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AppHelper {

    public static String textFcu(int code){
        switch (code){
            case 1:
                return "GOOD";
            case 2:
                return "BEST";
            default:
                return "BAD";
        }
    }

    public static String getDuration(long timestamp){
        long timeNow = new Date().getTime()/1000L;
        long seconds = timestamp - timeNow;
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;
        return String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getTimeChat(long timestamp){
        long timeStampJava = timestamp*1000L;
        Date date = new Date(timeStampJava);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormat.format(date);
    }

    public static String getIdFromChat(String code){
        String[] arr = code.split(":");
        return arr[0];
    }

    public static String getTimeDur(String start, String end){
        String[] starts = start.split(":");
        String[] ends = end.split(":");
        return starts[0]+":"+starts[1]+" - "+ends[0]+":"+ends[1];
    }

    public static String getDayName(int code){
        String ret = "";
        switch (code){
            case 0:
                ret = "Minggu";
                break;
            case 1:
                ret = "Senin";
                break;
            case 2:
                ret = "Selasa";
                break;
            case 3:
                ret = "Rabu";
                break;
            case 4:
                ret = "Kamis";
                break;
            case 5:
                ret = "Jumat";
                break;
            case 6:
                ret = "Sabtu";
                break;
        }
        return ret;
    }

}
