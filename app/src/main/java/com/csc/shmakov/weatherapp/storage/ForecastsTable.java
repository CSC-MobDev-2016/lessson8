package com.csc.shmakov.weatherapp.storage;

import android.provider.BaseColumns;

/**
 * Created by Pavel on 4/3/2016.
 */
public interface ForecastsTable extends BaseColumns {
    String TABLE_NAME = "forecasts";
    String COLUMN_CITY_ID = "cityId";
    String COLUMN_DATA = "data";

    String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME
                    + "("
                    + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_CITY_ID + " TEXT, "
                    + COLUMN_DATA + " TEXT"
                    + ")";
    String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
