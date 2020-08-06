package com.labs.taqwa.database;

public class TableSecond {

    public static final String TABLE_SECOND = "SECOND";

    public static final String KEY_ID = "key_id";
    public static final String KEY_NAME_MESJID = "name";

    public static final String KEY_IQOMAH_SHUBUH = "iqomahshubuh";
    public static final String KEY_IQOMAH_DZUHUR = "iqomahdzuhur";
    public static final String KEY_IQOMAH_ASHR = "iqomahashr";
    public static final String KEY_IQOMAH_MAGRIB = "iqomahmagrib";
    public static final String KEY_IQOMAH_ISYA = "iqomahisya";

    public static final String KEY_TEXT_BERJALAN= "textberjalan";

    public static final String[] TABLE_FIELDS = new String[] {
            KEY_ID,
            KEY_NAME_MESJID,
            KEY_IQOMAH_SHUBUH,
            KEY_IQOMAH_DZUHUR,
            KEY_IQOMAH_ASHR,
            KEY_IQOMAH_MAGRIB,
            KEY_IQOMAH_ISYA,
            KEY_TEXT_BERJALAN
    };

    public static final String CREATE_TABLE_SECOND = "CREATE TABLE IF NOT EXISTS "
            + TABLE_SECOND +"("
            + KEY_ID +" TEXT UNIQUE,"
            + KEY_NAME_MESJID +" TEXT,"
            + KEY_IQOMAH_SHUBUH +" TEXT,"
            + KEY_IQOMAH_DZUHUR +" TEXT,"
            + KEY_IQOMAH_ASHR +" TEXT,"
            + KEY_IQOMAH_MAGRIB +" TEXT,"
            + KEY_IQOMAH_ISYA +" TEXT,"
            + KEY_TEXT_BERJALAN +" TEXT"
            + ")"
            ;
}
