package com.csc.jv.weather;

import android.provider.BaseColumns;


public interface ForecastTable extends BaseColumns {
    String TABLE_NAME = "forecast";

    String COLUMN_CITY_ID = "city_id";
    String COLUMN_CITY_NAME = "city_name";
    String COLUMN_UPDATE_TIME = "update_time";
    String COLUMN_TEMPERATURE = "temperature";
    String COLUMN_WEATHER_TYPE = "weather_type";
    String COLUMN_WIND_DIRECTION = "wind_direction";
    String COLUMN_WIND_SPEED = "wind_speed";
    String COLUMN_HUMIDITY = "humidity";
    String COLUMN_PRESSURE = "pressure";
    String COLUMN_MSLP_PRESSURE = "mslp_pressure";
    String COLUMN_DAYTIME = "daytime";
    String COLUMN_WATER_TEMPERATURE = "water_temperature";

}
