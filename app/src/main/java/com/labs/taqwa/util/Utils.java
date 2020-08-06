package com.labs.taqwa.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utils {
    public static List<String> listImage;

    public static void setListImage(List<String> arrayList){
        listImage = new ArrayList<>();
        listImage = arrayList;
    }

    public static List<String> getListImage(){
        return listImage;
    }

    public static String addMinutes(String time, String minutes){

        String tm = "";
        long ONE_MINUTE_IN_MILLIS = 60000;

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            Date date = simpleDateFormat.parse(time);

            Date finalDate = new Date(date.getTime() + (Integer.valueOf(minutes) * ONE_MINUTE_IN_MILLIS));
            tm = simpleDateFormat.format(finalDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return tm;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            // Network is present and connected
            isAvailable = true;
        }
        return isAvailable;
    }
}
