package com.labs.taqwa.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBManager {
    private static final String TAG = "DBManager";

    private DatabaseHelper dbHelper;

    private Context context;

    private static SQLiteDatabase db;

    public DBManager(Context c) {
        context = c;
        dbHelper = new DatabaseHelper(context);
    }

    public void close() {
        dbHelper.close();
    }

    public final Cursor fetch(String p_table, String[] p_fields, String p_where, String p_groupBy, String p_orderBy, String p_limit) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            if (db != null) {
                cursor = db.query(p_table, p_fields, p_where, null, p_groupBy, null, p_orderBy, p_limit);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return cursor;
    }

    public final Cursor rawQuery(String query) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            if (db != null) {
                cursor = db.rawQuery(query, null);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return cursor;
    }

    public long insert (String table, ContentValues contentValues, boolean bReplace){
        db = dbHelper.getWritableDatabase();
        long insert = -1;
        if (contentValues != null){
            if (db == null) return insert;

            insert = db.insertWithOnConflict(table, null, contentValues, bReplace ? SQLiteDatabase.CONFLICT_REPLACE :
                    SQLiteDatabase.CONFLICT_ABORT);
        }

        return insert;
    }

    public long insert (String table, ContentValues contentValues){
        db = dbHelper.getWritableDatabase();
        long insert = -1;
        if (contentValues != null){
            if (db == null) return insert;

            insert = db.insert(table, null, contentValues);
        }

        return insert;
    }

    public long update (String table, ContentValues values, String where){
        db = dbHelper.getWritableDatabase();
        long up = -1;

        if (db == null) return up;

        up = db.update(table, values, where, null);

        return up;
    }

    public long delete (String table, String where){
        db = dbHelper.getWritableDatabase();
        long del = -1;

        if (db == null) return del;

        del = db.delete(table, where, null);

        return del;
    }

    public long deleteAll (String table){
        db = dbHelper.getWritableDatabase();
        long del = -1;

        if (db == null) return del;

        del = db.delete(table, null, null);

        return del;
    }
}
