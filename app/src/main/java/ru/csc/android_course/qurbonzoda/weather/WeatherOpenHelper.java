package ru.csc.android_course.qurbonzoda.weather;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WeatherOpenHelper extends SQLiteOpenHelper {
  public static final int DATABASE_VERSION = 3;
  public static final String DATABASE_NAME = "weather.db";

  private static final String SQL_CREATE_ENTRIES_TABLE =
      "CREATE TABLE " + WeatherTable.TABLE_NAME
          + "("
          + WeatherTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
          + WeatherTable.COLUMN_CITY_NAME + " TEXT, "
          + WeatherTable.COLUMN_CURRENT_TEMP + " TEXT, "
          + WeatherTable.COLUMN_TEMP_MIN + " TEXT, "
          + WeatherTable.COLUMN_TEMP_MAX + " TEXT, "
          + WeatherTable.COLUMN_PRESSURE + " TEXT, "
          + WeatherTable.COLUMN_HUMIDITY + " TEXT, "
          + WeatherTable.COLUMN_LAST_UPDATE + " TEXT, "
          + WeatherTable.COLUMN_WIND_SPEED + " TEXT, "
          + WeatherTable.COLUMN_WIND_DEGREE + " TEXT, "
          + WeatherTable.COLUMN_COUNTRY + " TEXT, "
          + WeatherTable.COLUMN_CLOUDS + " TEXT, "
          + WeatherTable.COLUMN_WEATHER_MAIN + " TEXT, "
          + WeatherTable.COLUMN_WEATHER_DESCRIPTION + " TEXT, "
          + WeatherTable.COLUMN_WEATHER_ICON + " TEXT, "
          + WeatherTable.COLUMN_COORD_LON + " TEXT, "
          + WeatherTable.COLUMN_COORD_LAT + " TEXT"
          + ")";

  private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + WeatherTable.TABLE_NAME;

  public WeatherOpenHelper(Context context) {
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
