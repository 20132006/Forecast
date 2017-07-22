package com.example.artlab.forecast.Common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by artlab on 17. 7. 22.
 */

public class Common {
    public static String API_KEY  = "c2509ca9baf3d0664481fc5a215633cb";
    public static String API_LINK = "http://api.openweathermap.org/data/2.5/weather";
    public static String API_LINK7 = "http://api.openweathermap.org/data/2.5/forecast/daily";

    public static String apiRequest (String cityName){
        StringBuilder sb = new StringBuilder (API_LINK);
        sb.append(String.format("?q=%s&APPID=%s&units=metric", cityName, API_KEY));
        return sb.toString();
    }
    public static String apiRequest (String cityName, String num){
        StringBuilder sb = new StringBuilder (API_LINK7);
        sb.append(String.format("?q=%s&cnt=%s&APPID=%s&units=metric", cityName, num, API_KEY));
        return sb.toString();
    }

    public static String unixTimeStampToDateTime(double unixTimeStamp)
    {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        date.setTime((long)unixTimeStamp*1000);
        return dateFormat.format(date);
    }

    public static String getImage(String icon){
        return String.format("http://openweathermap.org/img/w/%s.png",icon);
    }

    public static String  getDateNow(){
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }
    public static String  ConvertToDate(int N){

        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, N);
        dt = c.getTime();

        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        return dateFormat.format(dt);
    }

}
