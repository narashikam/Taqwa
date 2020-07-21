package com.labs.taqwa;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesUtil {

    public static final String TIME_AUTO = "time_auto";

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
}
