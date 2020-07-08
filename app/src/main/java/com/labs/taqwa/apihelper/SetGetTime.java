package com.labs.taqwa.apihelper;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface SetGetTime {

    @GET("/v2/times/today.json?city=jakarta")
    Call<ResponseBody> getTime();
}
