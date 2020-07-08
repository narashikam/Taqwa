package com.labs.taqwa.apihelper;

import android.content.Context;

public class UtilsApi {
    public static final String BASE_URL_API = "https://api.pray.zone";

    public static SetGetTime getWaktuSholat(Context context){
        return RetrofitClient
                .getClient(BASE_URL_API, context)
                .create(SetGetTime.class);
    }
}
