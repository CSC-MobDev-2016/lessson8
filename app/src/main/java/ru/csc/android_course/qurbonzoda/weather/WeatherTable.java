package ru.csc.android_course.qurbonzoda.weather;

import android.provider.BaseColumns;

interface WeatherTable extends BaseColumns {
    String TABLE_NAME = "weather";

    String COLUMN_CITY_NAME = "name";
    String COLUMN_CURRENT_TEMP = "temp";
    String COLUMN_TEMP_MIN = "temp_min";
    String COLUMN_TEMP_MAX = "temp_max";
    String COLUMN_PRESSURE = "pressure";
    String COLUMN_HUMIDITY = "humidity";
    String COLUMN_LAST_UPDATE = "last_update";
    String COLUMN_WIND_SPEED = "wind_speed";
    String COLUMN_WIND_DEGREE = "wind_degree";
    String COLUMN_COUNTRY = "country";
    String COLUMN_CLOUDS = "clouds";
    String COLUMN_WEATHER_MAIN = "weather_main";
    String COLUMN_WEATHER_DESCRIPTION = "weather_description";
    String COLUMN_WEATHER_ICON = "weather_icon";
    String COLUMN_COORD_LON = "coord_lon";
    String COLUMN_COORD_LAT = "coord_lat";
}
