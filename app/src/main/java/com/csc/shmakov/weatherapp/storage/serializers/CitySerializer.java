package com.csc.shmakov.weatherapp.storage.serializers;

import android.content.ContentValues;
import android.database.Cursor;

import com.csc.shmakov.weatherapp.model.entities.City;
import com.csc.shmakov.weatherapp.storage.CitiesTable;

import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;

/**
 * Created by Pavel on 4/23/2016.
 */
public class CitySerializer {
    public ContentValues toContentValues(City city) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CitiesTable.COLUMN_NAME, city.name);
        contentValues.put(CitiesTable.COLUMN_API_ID, city.apiId);
        return contentValues;
    }

    public List<City> readFromCursor(Cursor cursor) {
        List<City> cities = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            cities.add(toEntity(cursor));
        }
        return cities;
    }

    private City toEntity(Cursor cursor) {
        return new City(
                getString(cursor, CitiesTable.COLUMN_NAME),
                getString(cursor, CitiesTable.COLUMN_API_ID));
    }

    private static String getString(Cursor cursor, String column) {
        return cursor.getString(cursor.getColumnIndex(column));
    }

    private int getInt(Cursor cursor, String column) {
        return cursor.getInt(cursor.getColumnIndex(column));
    }

    private long getLong(Cursor cursor, String column) {
        return cursor.getLong(cursor.getColumnIndex(column));
    }
}
