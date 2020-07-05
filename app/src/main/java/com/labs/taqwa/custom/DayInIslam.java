package com.labs.taqwa.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextClock;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class DayInIslam extends TextClock {

    public DayInIslam(Context context) {
        super(context);
        setLocaleDateFormat();
    }

    public DayInIslam(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLocaleDateFormat();
    }

    public DayInIslam(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLocaleDateFormat();
    }

    public DayInIslam(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setLocaleDateFormat();
    }

    private void setLocaleDateFormat() {
        // You can change language from here
        Locale currentLocale = new Locale("id", "ID");
        Calendar cal = GregorianCalendar.getInstance(TimeZone.getDefault(), currentLocale);

        String dayName = cal.getDisplayName(cal.DAY_OF_WEEK, Calendar.LONG, currentLocale);
        String monthName = cal.getDisplayName(cal.MONTH, Calendar.LONG, currentLocale);

        this.setFormat12Hour("'" + dayName + "'\n'" + monthName + "' dd yyyy");
        this.setFormat24Hour("'" + dayName + "'\n'" + monthName + "' dd yyyy");
    }
}
