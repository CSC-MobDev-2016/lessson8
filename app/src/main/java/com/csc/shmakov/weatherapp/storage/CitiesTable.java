package com.csc.shmakov.weatherapp.storage;

import android.provider.BaseColumns;

/**
 * Created by Pavel on 4/3/2016.
 */
public interface CitiesTable extends BaseColumns {
    String TABLE_NAME = "cities";

    String COLUMN_NAME = "name";

    String COLUMN_API_ID = "apiId";

    String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME
                    + "("
                    + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_NAME + " TEXT, "
                    + COLUMN_API_ID + " TEXT"
                    + ")";
    String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
