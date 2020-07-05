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
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.labs.taqwa.adapter.SlideImageAdapter;
import com.labs.taqwa.database.DBManager;
import com.labs.taqwa.database.TableMain;

import java.util.Timer;
import java.util.TimerTask;

/*
 * Main Activity class that loads {@link MainFragment}.
 */
public class MainActivity extends Activity {
    private TextView text_marquee;
    private TextClock text_clock, text_clock_day;
    private int[] slide_image;
    private SlideImageAdapter adapter;
    private ViewPager view_pager;
    private LinearLayout lyt_setting;
    private int indexImage = 0;

    private DBManager dbManager;

    private TextView txt_shubuh, txt_dhuha, txt_dzuhur, txt_ashr, txt_magrib, txt_isya;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        Cursor cursor =  dbManager.fetch(TableMain.TABLE_MAIN, TableMain.TABLE_FIELDS, null, null, null, null);

        if (cursor != null) {
            if (cursor.getCount() > 0){
                while (cursor.moveToNext()){
                    txt_shubuh.setText(cursor.getString(1));
                    txt_dhuha.setText(cursor.getString(2));
                    txt_dzuhur.setText(cursor.getString(3));
                    txt_ashr.setText(cursor.getString(4));
                    txt_magrib.setText(cursor.getString(5));
                    txt_isya.setText(cursor.getString(6));

                    text_marquee.setText(cursor.getString(12));
                }
            }
        }

        lyt_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Setting.class));
            }
        });
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
}
