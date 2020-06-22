package com.labs.taqwa.database;

public class TableMain {
    private static final String TAG = "TABLE_MAIN";
    public static final String TABLE_MAIN = "MAIN";

    public static final String KEY_ID = "key_id";
    public static final String KEY_NAME_MESJID = "name";
    public static final String KEY_ADZAN_SHUBUH = "adzanshubuh";
    public static final String KEY_ADZAN_DHUHA = "adzandhuha";
    public static final String KEY_ADZAN_DZUHUR = "adzandzuhur";
    public static final String KEY_ADZAN_ASHR = "adzanashr";
    public static final String KEY_ADZAN_MAGRIB = "adzanmagrib";
    public static final String KEY_ADZAN_ISYA = "adzanisya";

    public static final String KEY_IQOMAH_SHUBUH = "iqomahshubuh";
    public static final String KEY_IQOMAH_DZUHUR = "iqomahdzuhur";
    public static final String KEY_IQOMAH_ASHR = "iqomahashr";
    public static final String KEY_IQOMAH_MAGRIB = "iqomahmagrib";
    public static final String KEY_IQOMAH_ISYA = "iqomahisya";

    public static final String KEY_TEXT_BERJALAN= "textberjalan";

    public static final String[] TABLE_FIELDS = new String[] {
            KEY_ID,
            KEY_NAME_MESJID,
            KEY_ADZAN_SHUBUH,
            KEY_ADZAN_DHUHA,
            KEY_ADZAN_DZUHUR,
            KEY_ADZAN_ASHR,
            KEY_ADZAN_MAGRIB,
            KEY_ADZAN_ISYA,
            KEY_IQOMAH_SHUBUH,
            KEY_IQOMAH_DZUHUR,
            KEY_IQOMAH_ASHR,
            KEY_IQOMAH_MAGRIB,
            KEY_IQOMAH_ISYA,
            KEY_TEXT_BERJALAN
    };

    public static final String CREATE_TABLE_MAIN = "CREATE TABLE IF NOT EXISTS "
            + TABLE_MAIN +"("
            + KEY_NAME_MESJID +" TEXT,"
            + KEY_ADZAN_SHUBUH +" TEXT,"
            + KEY_ADZAN_DHUHA +" TEXT,"
            + KEY_ADZAN_DZUHUR +" TEXT,"
            + KEY_ADZAN_ASHR +" TEXT,"
            + KEY_ADZAN_MAGRIB +" TEXT,"
            + KEY_ADZAN_ISYA +" TEXT,"
            + KEY_IQOMAH_SHUBUH +" TEXT,"
            + KEY_IQOMAH_DZUHUR +" TEXT,"
            + KEY_IQOMAH_ASHR +" TEXT,"
            + KEY_IQOMAH_MAGRIB +" TEXT,"
            + KEY_IQOMAH_ISYA +" TEXT,"
            + KEY_TEXT_BERJALAN +" TEXT"
            + ")"
            ;
}
