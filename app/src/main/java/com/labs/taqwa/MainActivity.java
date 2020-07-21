/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.labs.taqwa;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.labs.taqwa.adapter.SlideImageAdapter;
import com.labs.taqwa.apihelper.SetGetTime;
import com.labs.taqwa.apihelper.UtilsApi;
import com.labs.taqwa.database.DBManager;
import com.labs.taqwa.database.TableMain;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * Main Activity class that loads {@link MainFragment}.
 */
public class MainActivity extends Activity {
    private final String TAG = "MainActivity";
    private TextView text_marquee;
    private TextClock text_clock, text_clock_day;
    private int[] slide_image;
    private SlideImageAdapter adapter;
    private ViewPager view_pager;
    private LinearLayout lyt_setting;
    private int indexImage = 0;

    private DBManager dbManager;

    private TextView txt_shubuh, txt_dhuha, txt_dzuhur, txt_ashr, txt_magrib, txt_isya;
    private String iqomah_shubuh = "";
    private String iqomah_dzuhur = "";
    private String iqomah_ashr = "";
    private String iqomah_magrib = "";
    private String iqomah_isya = "";

    private SetGetTime setGetTime;

    private Thread thread;
    private Dialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("xxx", "crateeeee");
        setContentView(R.layout.activity_main);

        view_pager = findViewById(R.id.view_pager);
        lyt_setting = findViewById(R.id.lyt_setting);

        txt_shubuh = findViewById(R.id.txt_shubuh);
        txt_dhuha = findViewById(R.id.txt_dhuha);
        txt_dzuhur = findViewById(R.id.txt_dzuhur);
        txt_ashr = findViewById(R.id.txt_ashr);
        txt_magrib = findViewById(R.id.txt_magrib);
        txt_isya = findViewById(R.id.txt_isya);

        text_marquee = findViewById(R.id.text_marquee);
        text_marquee.setSelected(true);

        text_clock = findViewById(R.id.text_clock);
        text_clock_day = findViewById(R.id.text_clock_day);
        text_clock.setFormat12Hour("k:mm:ss");

        slide_image = new int[]{R.drawable.kaaba, R.drawable.kaaba2, R.drawable.kaaba3};

        dbManager = new DBManager(getApplicationContext());

        adapter = new SlideImageAdapter(getApplicationContext(), slide_image);
        view_pager.setAdapter(adapter);

        createTopSlideShow();

//        if (PreferencesUtil.getAutoTime(getApplicationContext())){
//            setTimeByApi();
//        }

        Cursor cursor =  dbManager.fetch(TableMain.TABLE_MAIN, TableMain.TABLE_FIELDS, null, null, null, null);

        if (cursor != null) {
            if (cursor.getCount() > 0){
                while (cursor.moveToNext()){
                    txt_shubuh.setText(cursor.getString(2));
                    txt_dhuha.setText(cursor.getString(3));
                    txt_dzuhur.setText(cursor.getString(4));
                    txt_ashr.setText(cursor.getString(5));
                    txt_magrib.setText(cursor.getString(6));
                    txt_isya.setText(cursor.getString(7));

                    iqomah_shubuh = cursor.isNull(8) ? "" : cursor.getString(8);
                    iqomah_dzuhur = cursor.isNull(9) ? "" : cursor.getString(9);
                    iqomah_ashr = cursor.isNull(10) ? "" : cursor.getString(10);
                    iqomah_magrib = cursor.isNull(11) ? "" : cursor.getString(11);
                    iqomah_isya = cursor.isNull(12) ? "" : cursor.getString(12);

                    text_marquee.setText(cursor.getString(13));
                }
            }
        }

        lyt_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Setting.class));
            }
        });

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        String[] sTime = text_clock.getText().toString().split(":");
                        String realTime = sTime[0] + ":" + sTime[1];

                        if (realTime.equals(txt_shubuh.getText().toString())){
                            showCustomDialog("Sudah memasuki Adzan Shubuh");
                        }
                        else if (!iqomah_shubuh.isEmpty() && realTime.equals(iqomah_shubuh)){
                            showCustomDialog("Sudah memasuki Iqomah Shubuh");
                        }
                        else if (realTime.equals(txt_dzuhur.getText().toString())){
                            showCustomDialog("Sudah memasuki Adzan Dzhuhur");
                        }
                        else if (!iqomah_dzuhur.isEmpty() && realTime.equals(iqomah_dzuhur)){
                            showCustomDialog("Sudah memasuki Iqomah Dzuhur");
                        }
                        else if (realTime.equals(txt_ashr.getText().toString())){
                            showCustomDialog("Sudah memasuki Adzan Ashr");
                        }
                        else if (!iqomah_ashr.isEmpty() && realTime.equals(iqomah_ashr)){
                            showCustomDialog("Sudah memasuki Iqomah Ashr");
                        }
                        else if (realTime.equals(txt_magrib.getText().toString())){
                            showCustomDialog("Sudah memasuki Adzan Magrib");
                        }
                        else if (!iqomah_magrib.isEmpty() && realTime.equals(iqomah_magrib)){
                            showCustomDialog("Sudah memasuki Iqomah Ashr");
                        }
                        else if (realTime.equals(txt_isya.getText().toString())){
                            showCustomDialog("Sudah memasuki Adzan Isya");
                        }
                        else if (!iqomah_isya.isEmpty() && realTime.equals(iqomah_isya)){
                            showCustomDialog("Sudah memasuki Iqomah Ashr");
                        }
                        else {
                            if (dialog != null){
                                if (dialog.isShowing()){
                                    dialog.dismiss();
                                }
                            }
                        }

                        Thread.sleep(1000*60);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    private String timePlus(String s){
        String[] a = s.split(":");
        int b = Integer.valueOf(a[1]) + 1;
        String c = a[0] + ":" + b;

        return c;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (thread != null && !thread.isAlive()){
            thread.start();
        }

        if (PreferencesUtil.getAutoTime(getApplicationContext())){
            setTimeByApi();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        thread.interrupt();
    }

    private void createTopSlideShow(){
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (indexImage > slide_image.length){
                    indexImage = 0;
                }

                view_pager.setCurrentItem(indexImage, true);

                indexImage++;
            }
        };

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 1000, 5000);
    }

    private void setTimeByApi(){
        setGetTime = UtilsApi.getWaktuSholat(getApplicationContext());

        setGetTime.getTime().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        JSONObject rslt = new JSONObject(jsonRESULTS.getString("results"));
                        JSONArray jaDateTime = new JSONArray(rslt.getString("datetime"));
                        JSONObject joTimes = new JSONObject(jaDateTime.getJSONObject(0).getString("times"));

                        ContentValues contentValues = new ContentValues();
                        contentValues.put(TableMain.KEY_ID, 123);
                        contentValues.put(TableMain.KEY_ADZAN_SHUBUH, joTimes.getString("Fajr"));
                        contentValues.put(TableMain.KEY_ADZAN_DHUHA, joTimes.getString("Sunrise"));
                        contentValues.put(TableMain.KEY_ADZAN_DZUHUR, joTimes.getString("Dhuhr"));
                        contentValues.put(TableMain.KEY_ADZAN_ASHR, joTimes.getString("Asr"));
                        contentValues.put(TableMain.KEY_ADZAN_MAGRIB, joTimes.getString("Maghrib"));
                        contentValues.put(TableMain.KEY_ADZAN_ISYA, joTimes.getString("Isha"));

                        long result = dbManager.insert(TableMain.TABLE_MAIN, contentValues, true);

                        if (result > 0){
                            txt_shubuh.setText(joTimes.getString("Fajr"));
                            txt_dhuha.setText(joTimes.getString("Sunrise"));
                            txt_dzuhur.setText(joTimes.getString("Dhuhr"));
                            txt_ashr.setText(joTimes.getString("Asr"));
                            txt_magrib.setText(joTimes.getString("Maghrib"));
                            txt_isya.setText(joTimes.getString("Isha")) ;
                        }

                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void showCustomDialog(String title) {
        TextView textView;

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_notif);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        textView = dialog.findViewById(R.id.content);
        textView.setText(title);

        if (!dialog.isShowing()){
            dialog.show();
            dialog.getWindow().setAttributes(lp);
        }
    }
}
