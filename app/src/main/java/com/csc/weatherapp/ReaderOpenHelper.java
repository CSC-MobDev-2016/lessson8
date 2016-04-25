package com.csc.weatherapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ReaderOpenHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "reader.db";

    private static final String SQL_CREATE_ENTRIES_TABLE = "CREATE TABLE " + FeedsTable.TABLE_NAME + "("
            + FeedsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FeedsTable.COLUMN_NAME + " TEXT, "
            + FeedsTable.COLUMN_ID + " INTEGER, "
            + FeedsTable.COLUMN_COD + " INTEGER, "
            + FeedsTable.COLUMN_DATE + " DATE, "
            + FeedsTable.COLUMN_TEMP + " DOUBLE, "
            + FeedsTable.COLUMN_PRESSURE + " INTEGER, "
            + FeedsTable.COLUMN_WIND_SPEED + " INTEGER, "
            + FeedsTable.COLUMN_WIND_DEG + " INTEGER, "
            + FeedsTable.COLUMN_TEMP_MIN + " DOUBLE, "
            + FeedsTable.COLUMN_TEMP_MAX + " DOUBLE "
            + ")";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + FeedsTable.TABLE_NAME;

    public ReaderOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
