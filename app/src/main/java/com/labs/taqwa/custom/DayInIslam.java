package com.labs.taqwa.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextClock;

import net.time4j.SystemClock;
import net.time4j.android.ApplicationStarter;
import net.time4j.calendar.HijriCalendar;
import net.time4j.engine.StartOfDay;
import net.time4j.format.expert.ChronoFormatter;
import net.time4j.format.expert.PatternType;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class DayInIslam extends TextClock {

    public DayInIslam(Context context) {
        super(context);
        ApplicationStarter.initialize(context, true);
        setLocaleDateFormat();
    }

    public DayInIslam(Context context, AttributeSet attrs) {
        super(context, attrs);
        ApplicationStarter.initialize(context, true);
        setLocaleDateFormat();
    }

    public DayInIslam(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ApplicationStarter.initialize(context, true);
        setLocaleDateFormat();
    }

    public DayInIslam(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        ApplicationStarter.initialize(context, true);
        setLocaleDateFormat();
    }

    private void setLocaleDateFormat() {
        // You can change language from here
        Locale currentLocale = new Locale("id", "ID");
        Calendar cal = GregorianCalendar.getInstance(TimeZone.getDefault(), currentLocale);

        String dayName = cal.getDisplayName(cal.DAY_OF_WEEK, Calendar.LONG, currentLocale);
        String monthName = cal.getDisplayName(cal.MONTH, Calendar.LONG, currentLocale);

        ChronoFormatter<HijriCalendar> hijriFormat =
                ChronoFormatter.setUp(HijriCalendar.family(), currentLocale)
                        .addPattern("d MMMM yyyy", PatternType.CLDR)
                        .build()
                        .withCalendarVariant(HijriCalendar.VARIANT_UMALQURA);

        // conversion from gregorian to hijri-umalqura valid at noon
        // (not really valid in the evening when next islamic day starts)
        HijriCalendar today =
                SystemClock.inLocalView().today().transform(
                        HijriCalendar.class,
                        HijriCalendar.VARIANT_UMALQURA
                );
        Log.d("xxx",hijriFormat.format(today)); // 22nd Rajab 1438

        // taking into account the specific start of day for Hijri calendar
        HijriCalendar todayExact =
                SystemClock.inLocalView().now(
                        HijriCalendar.family(),
                        HijriCalendar.VARIANT_UMALQURA,
                        StartOfDay.EVENING // simple approximation => 18:00
                ).toDate();
        Log.d("xxx", hijriFormat.format(todayExact)); // 22nd Rajab 1438 (23rd after 18:00)

        this.setFormat12Hour("'" + hijriFormat.format(today));
        this.setFormat24Hour("'" + hijriFormat.format(today));
    }
}
