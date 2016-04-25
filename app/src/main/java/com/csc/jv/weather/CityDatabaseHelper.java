package com.csc.jv.weather;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CityDatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "cities_id.db";

    private static final String SQL_CREATE_ENTRIES_TABLE =
            "CREATE TABLE " + CityTable.TABLE_NAME
                    + "("
                    + CityTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CityTable.COLUMN_CITY_ID + " TEXT, "
                    + CityTable.COLUMN_CITY_NAME + " TEXT, "
                    + "unique(" + CityTable.COLUMN_CITY_ID + ") ON CONFLICT replace "
                    + ")";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + ForecastTable.TABLE_NAME;

    public CityDatabaseHelper(Context context) {
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
