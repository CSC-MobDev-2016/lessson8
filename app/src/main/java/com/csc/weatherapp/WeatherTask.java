package com.csc.weatherapp;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.csc.weatherapp.ListCitiesFragment;
import com.csc.weatherapp.MainActivity;
import com.csc.whetherapi.OpenWeatherMap;
import com.csc.whetherapi.WeatherDescription;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Oleg Doronin
 * WhetherApp
 * Copyright (c) 2016 CS. All rights reserved.
 */
public class WeatherTask extends AsyncTask<Void, Void, Void> {
    ContentResolver resolver;

    WeatherTask(ContentResolver resolver) {
        this.resolver = resolver;

    }
    @Override
    protected void onPreExecute() {

    }
    @Override
    protected Void doInBackground(Void... params) {
        ArrayList<Integer> list = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = resolver.query(ListCitiesFragment.ENTRIES_URI, null, null, null, null);

        } catch (Exception ex) {

        } finally {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    list.add(cursor.getInt(cursor.getColumnIndex(FeedsTable.COLUMN_ID)));

                }
            }

            if (cursor != null) {
                cursor.close();
            }
        }

        ArrayList<WeatherDescription> descriptions = OpenWeatherMap.getWeather(list);

        for (WeatherDescription d : descriptions) {
            ContentValues values = new ContentValues();
            values.put(FeedsTable.COLUMN_COD, d.cod);
            values.put(FeedsTable.COLUMN_DATE, new Date().toString());
            values.put(FeedsTable.COLUMN_TEMP, d.main.temp);
            values.put(FeedsTable.COLUMN_PRESSURE, d.main.pressure);
            values.put(FeedsTable.COLUMN_TEMP_MAX, d.main.temp_max);
            values.put(FeedsTable.COLUMN_TEMP_MIN, d.main.temp_min);
            values.put(FeedsTable.COLUMN_WIND_DEG, d.wind.deg);
            values.put(FeedsTable.COLUMN_WIND_SPEED, d.wind.speed);
            Log.d("MEGA_TAG", Double.toString(d.main.temp));
            resolver.update(ListCitiesFragment.ENTRIES_URI, values, FeedsTable.COLUMN_ID + " = " + d.id, null);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

    }
}
