package com.csc.simple_weather;

import android.database.Cursor;

import static com.csc.simple_weather.WeatherTable.*;

/**
 * Created by anastasia on 23.04.16.
 */
public class City {
    int id;
    String name;
    String description;
    String weather;
    String humidity;
    String pressure;
    String wind;
    String lastUpd;

    public City(String name, String desc, String weather, String humidity,
                String pressure, String wind, String lastUpd) {
        this.name = name;
        this.description = desc;
        this.weather = weather;
        this.humidity = humidity;
        this.pressure = pressure;
        this.wind = wind;
        this.lastUpd = lastUpd;
    }

    public static City fromCursor(Cursor cursor) {
        return new City(
            cursor.getString(cursor.getColumnIndex(COLUMN_CITY)),
            cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
            cursor.getString(cursor.getColumnIndex(COLUMN_WEATHER)),
            cursor.getString(cursor.getColumnIndex(COLUMN_HUMIDITY)),
            cursor.getString(cursor.getColumnIndex(COLUMN_PRESSURE)),
            cursor.getString(cursor.getColumnIndex(COLUMN_WIND)),
            cursor.getString(cursor.getColumnIndex(COLUMN_LASTUPD)));
    }
}
