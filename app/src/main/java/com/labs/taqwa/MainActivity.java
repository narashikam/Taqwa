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
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.labs.taqwa.adapter.SlideImageAdapter;

import java.util.Timer;
import java.util.TimerTask;

/*
 * Main Activity class that loads {@link MainFragment}.
 */
public class MainActivity extends Activity {
    private TextView text_marquee;
    private TextClock text_clock;
    private int[] slide_image;
    private SlideImageAdapter adapter;
    private ViewPager view_pager;
    private LinearLayout lyt_setting;
    private int indexImage = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view_pager = findViewById(R.id.view_pager);
        lyt_setting = findViewById(R.id.lyt_setting);

        text_marquee = findViewById(R.id.text_marquee);
        text_marquee.setSelected(true);

        text_clock = findViewById(R.id.text_clock);
        text_clock.setFormat12Hour("k:mm:ss");

        slide_image = new int[]{R.drawable.kaaba, R.drawable.kaaba2, R.drawable.kaaba3};

        adapter = new SlideImageAdapter(getApplicationContext(), slide_image);
        view_pager.setAdapter(adapter);

        createTopSlideShow();

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
