package com.csc.jv.weather;

import android.database.Cursor;


public class ForecastItem {

    public final String city_id;
    public final String city_name;
    public final String update_time;
    public final String temperature;
    public final String weather_type;
    public final String wind_direction;
    public final String wind_speed;
    public final String humidity;
    public final String pressure;
    public final String mslp_pressure;
    public final String daytime;
    public final String water_temperature;

    public ForecastItem(String city_id, String city_name, String update_time, String temperature, String weather_type,
                        String wind_direction, String wind_speed, String humidity, String pressure,
                        String mslp_pressure, String daytime, String water_temperature) {

        this.city_id = city_id;
        this.city_name = city_name;
        this.update_time = update_time;
        this.temperature = temperature;
        this.weather_type = weather_type;
        this.wind_direction = wind_direction;
        this.wind_speed = wind_speed;
        this.humidity = humidity;
        this.pressure = pressure;
        this.mslp_pressure = mslp_pressure;
        this.daytime = daytime;
        this.water_temperature = water_temperature;
    }


    public static ForecastItem fromCursor(Cursor cursor) {

        final String city_id = cursor.getString(cursor.getColumnIndex(ForecastTable.COLUMN_CITY_ID));
        final String city_name = cursor.getString(cursor.getColumnIndex(ForecastTable.COLUMN_CITY_NAME));
        final String update_time = cursor.getString(cursor.getColumnIndex(ForecastTable.COLUMN_UPDATE_TIME));
        final String temperature = cursor.getString(cursor.getColumnIndex(ForecastTable.COLUMN_TEMPERATURE));
        final String weather_type = cursor.getString(cursor.getColumnIndex(ForecastTable.COLUMN_WEATHER_TYPE));
        final String wind_direction = cursor.getString(cursor.getColumnIndex(ForecastTable.COLUMN_WIND_DIRECTION));
        final String wind_speed = cursor.getString(cursor.getColumnIndex(ForecastTable.COLUMN_WIND_SPEED));
        final String humidity = cursor.getString(cursor.getColumnIndex(ForecastTable.COLUMN_HUMIDITY));
        final String pressure = cursor.getString(cursor.getColumnIndex(ForecastTable.COLUMN_PRESSURE));
        final String mslp_pressure = cursor.getString(cursor.getColumnIndex(ForecastTable.COLUMN_MSLP_PRESSURE));
        final String daytime = cursor.getString(cursor.getColumnIndex(ForecastTable.COLUMN_DAYTIME));
        final String water_temperature = cursor.getString(cursor.getColumnIndex(ForecastTable.COLUMN_WATER_TEMPERATURE));

        return new ForecastItem(city_id, city_name, update_time, temperature, weather_type,
                wind_direction, wind_speed, humidity, pressure,
                mslp_pressure, daytime, water_temperature);
    }

}
