package com.csc.simple_weather;

import android.provider.BaseColumns;

interface WeatherTable extends BaseColumns {
    String TABLE_NAME = "weather";

    String COLUMN_CITY = "city";
    String COLUMN_WEATHER= "weather";
    String COLUMN_DESCRIPTION = "description";
    String COLUMN_WIND = "wind";
    String COLUMN_HUMIDITY = "humidity";
    String COLUMN_PRESSURE = "pressure";
    String COLUMN_LASTUPD = "lastupd";
}
