package com.labs.taqwa.database;

public class TableMain {
    private static final String TAG = "TABLE_MAIN";
    public static final String TABLE_MAIN = "MAIN";

    public static final String KEY_ID = "key_id";
    public static final String KEY_ADZAN_SHUBUH = "adzanshubuh";
    public static final String KEY_ADZAN_DHUHA = "adzandhuha";
    public static final String KEY_ADZAN_DZUHUR = "adzandzuhur";
    public static final String KEY_ADZAN_ASHR = "adzanashr";
    public static final String KEY_ADZAN_MAGRIB = "adzanmagrib";
    public static final String KEY_ADZAN_ISYA = "adzanisya";

    public static final String[] TABLE_FIELDS = new String[] {
            KEY_ID,
            KEY_ADZAN_SHUBUH,
            KEY_ADZAN_DHUHA,
            KEY_ADZAN_DZUHUR,
            KEY_ADZAN_ASHR,
            KEY_ADZAN_MAGRIB,
            KEY_ADZAN_ISYA
    };

    public static final String CREATE_TABLE_MAIN = "CREATE TABLE IF NOT EXISTS "
            + TABLE_MAIN +"("
            + KEY_ID +" TEXT UNIQUE,"
            + KEY_ADZAN_SHUBUH +" TEXT,"
            + KEY_ADZAN_DHUHA +" TEXT,"
            + KEY_ADZAN_DZUHUR +" TEXT,"
            + KEY_ADZAN_ASHR +" TEXT,"
            + KEY_ADZAN_MAGRIB +" TEXT,"
            + KEY_ADZAN_ISYA +" TEXT"
            + ")"
            ;
}
