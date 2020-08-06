package com.labs.taqwa.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";

    private static final String DATABASE_NAME = "taqwa.db";
    private static final int DATABASE_VERSION = 5;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableMain.CREATE_TABLE_MAIN);
        db.execSQL(TableSecond.CREATE_TABLE_SECOND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        for (int version = oldVersion + 1; version <= newVersion; version++){
            switch (version){
                case 5 :
                    db.execSQL("drop table if exists "+ TableMain.TABLE_MAIN +"");
                    db.execSQL(TableMain.CREATE_TABLE_MAIN);
                    db.execSQL(TableSecond.CREATE_TABLE_SECOND);
                    break;
            }
        }
    }
}
