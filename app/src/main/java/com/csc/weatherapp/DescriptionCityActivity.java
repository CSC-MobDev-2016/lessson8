package com.csc.weatherapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by Oleg Doronin
 * WhetherApp
 * Copyright (c) 2016 CS. All rights reserved.
 */
public class DescriptionCityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_city);
        TextView tv = (TextView)findViewById(R.id.full_description);
        Intent intent = getIntent();

        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(ListCitiesFragment.ENTRIES_URI, null, FeedsTable.COLUMN_ID + " = " + intent.getStringExtra("CITY_ID"), null, null);
            cursor.moveToNext();
                tv.setText("COD = " + cursor.getString(cursor.getColumnIndex(FeedsTable.COLUMN_COD)) +
                        " \nDATE = " + cursor.getString(cursor.getColumnIndex(FeedsTable.COLUMN_DATE)) +
                        " \nTEMP = " + cursor.getString(cursor.getColumnIndex(FeedsTable.COLUMN_TEMP)) +
                        " \nPRESSURE = " + cursor.getString(cursor.getColumnIndex(FeedsTable.COLUMN_PRESSURE)) +
                        " \nTEMP_MAX = " + cursor.getString(cursor.getColumnIndex(FeedsTable.COLUMN_TEMP_MAX)) +
                        " \nTEMP_MIN = " + cursor.getString(cursor.getColumnIndex(FeedsTable.COLUMN_TEMP_MIN)) +
                        " \nWIND_DEG = " + cursor.getString(cursor.getColumnIndex(FeedsTable.COLUMN_WIND_DEG)) +
                        " \nWIND_SPEED = " + cursor.getString(cursor.getColumnIndex(FeedsTable.COLUMN_WIND_SPEED)));

        } catch (Exception ex) {

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void update(View view) {
        WeatherTask task = new WeatherTask(getContentResolver());
        task.execute();
    }
}
