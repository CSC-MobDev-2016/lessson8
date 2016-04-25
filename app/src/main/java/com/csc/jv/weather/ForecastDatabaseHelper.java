package com.csc.jv.weather;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ForecastDatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "reader.db";

    private static final String SQL_CREATE_ENTRIES_TABLE =
            "CREATE TABLE " + ForecastTable.TABLE_NAME
                    + "("
                    + ForecastTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ForecastTable.COLUMN_CITY_ID + " TEXT, "
                    + ForecastTable.COLUMN_CITY_NAME + " TEXT, "
                    + ForecastTable.COLUMN_UPDATE_TIME + " DATETIME, "
                    + ForecastTable.COLUMN_TEMPERATURE + " TEXT, "
                    + ForecastTable.COLUMN_WEATHER_TYPE + " TEXT, "
                    + ForecastTable.COLUMN_WIND_DIRECTION + " TEXT, "
                    + ForecastTable.COLUMN_WIND_SPEED + " TEXT, "
                    + ForecastTable.COLUMN_HUMIDITY + " TEXT, "
                    + ForecastTable.COLUMN_PRESSURE + " TEXT, "
                    + ForecastTable.COLUMN_MSLP_PRESSURE + " TEXT, "
                    + ForecastTable.COLUMN_DAYTIME + " TEXT, "
                    + ForecastTable.COLUMN_WATER_TEMPERATURE + " TEXT, "
                    + "unique(" + ForecastTable.COLUMN_CITY_ID + ") ON CONFLICT replace "
                    + ")";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + ForecastTable.TABLE_NAME;

    public ForecastDatabaseHelper(Context context) {
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
