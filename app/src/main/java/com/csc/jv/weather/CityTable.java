package com.csc.jv.weather;

import android.provider.BaseColumns;


public interface CityTable extends BaseColumns {
    String TABLE_NAME = "cities";

    String COLUMN_CITY_ID = "city_id";
    String COLUMN_CITY_NAME = "city_name";
}
