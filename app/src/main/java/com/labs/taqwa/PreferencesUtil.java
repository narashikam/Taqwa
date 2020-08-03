package com.labs.taqwa;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesUtil {

    public static final String TIME_AUTO = "time_auto";
    public static final String IMAGE_LIST = "image_list";

    public static SharedPreferences getPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setAutoTime(Context context, boolean bLoggedIn){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(TIME_AUTO, bLoggedIn);
        editor.apply();
    }

    public static boolean getAutoTime(Context context){
        return getPreferences(context).getBoolean(TIME_AUTO, false);
    }

    public static void setImageList(Context context, String list){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(IMAGE_LIST, list);
        editor.apply();
    }

    public static String getImageList(Context context){
        return getPreferences(context).getString(IMAGE_LIST, "");
    }
}
