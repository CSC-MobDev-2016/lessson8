package com.csc.weatherapp;

import android.provider.BaseColumns;

public interface FeedsTable extends BaseColumns {
    public String TABLE_NAME = "weather";

    public String COLUMN_NAME = "C_NAME";
    public String COLUMN_ID = "C_ID";
    public String COLUMN_COD = "C_COD";
    public String COLUMN_DATE = "C_DATE";
    public String COLUMN_TEMP = "C_TEMP";
    public String COLUMN_PRESSURE = "C_PRESSURE";
    public String COLUMN_WIND_SPEED = "C_WIND_SPEED";
    public String COLUMN_WIND_DEG = "C_WIND_DEG";
    public String COLUMN_TEMP_MIN = "C_TEMP_MIN";
    public String COLUMN_TEMP_MAX = "C_TEMP_MAX";
}
