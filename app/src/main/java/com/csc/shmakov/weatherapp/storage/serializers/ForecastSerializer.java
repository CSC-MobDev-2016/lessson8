package com.csc.shmakov.weatherapp.storage.serializers;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import com.csc.shmakov.weatherapp.model.entities.City;
import com.csc.shmakov.weatherapp.model.entities.Forecast;
import com.csc.shmakov.weatherapp.model.entities.PendingForecast;
import com.csc.shmakov.weatherapp.storage.CitiesTable;
import com.csc.shmakov.weatherapp.storage.ForecastsTable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel on 4/23/2016.
 */
public class ForecastSerializer {

    private static final String LAST_UPDATED = "lastUpdated";
    private static final String CURRENT = "current";
    private static final String DAY_FORECASTS = "dayForecasts";
    private static final String DESCRIPTION = "description";
    private static final String TEMPERATURE = "temperature";
    private static final String DATE = "date";
    private static final String WEATHER = "weather";

    public ContentValues toContentValues(Forecast forecast) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ForecastsTable.COLUMN_CITY_ID, forecast.cityId);
            if (!(forecast instanceof PendingForecast)) {
                contentValues.put(ForecastsTable.COLUMN_DATA, serializeData(forecast));
            } else {
                contentValues.put(ForecastsTable.COLUMN_DATA, "");
            }
            return contentValues;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private String serializeData(Forecast forecast) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(LAST_UPDATED, forecast.lastUpdated);

        Forecast.Data data = forecast.data;
        jsonObject.put(CURRENT, serializeWeather(data.current));
        JSONArray forecastJson = new JSONArray();
        for (Forecast.DayForecast dayForecast : data.dayForecasts) {
            forecastJson.put(serializeDayForeCast(dayForecast));
        }
        jsonObject.put(DAY_FORECASTS, forecastJson);
        return jsonObject.toString();
    }

    private JSONObject serializeWeather(Forecast.Weather weather) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(DESCRIPTION, weather.description);
        jsonObject.put(TEMPERATURE, weather.temperature);
        return jsonObject;
    }

    private JSONObject serializeDayForeCast(Forecast.DayForecast dayForecast) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(DATE, dayForecast.date);
        jsonObject.put(WEATHER, serializeWeather(dayForecast.weather));
        return jsonObject;
    }


    public List<Forecast> readFromCursor(Cursor cursor) {
        List<Forecast> forecasts = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            forecasts.add(toEntity(cursor));
        }
        return forecasts;
    }

    private Forecast toEntity(Cursor cursor) {
        try {
            String cityId = getString(cursor, ForecastsTable.COLUMN_CITY_ID);
            String data = getString(cursor, ForecastsTable.COLUMN_DATA);
            if (TextUtils.isEmpty(data)) {
                return new PendingForecast(cityId);
            }
            JSONObject jsonObject = new JSONObject(data);
            long lastUpdated = jsonObject.getLong(LAST_UPDATED);
            Forecast.Data forecastData = parseData(jsonObject);
            return new Forecast(cityId, lastUpdated, forecastData);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private Forecast.Data parseData(JSONObject jsonObject) throws JSONException {
        Forecast.Weather current = parseWeather(jsonObject.getJSONObject(CURRENT));
        List<Forecast.DayForecast> dayForecasts = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray(DAY_FORECASTS);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject dayJson = jsonArray.getJSONObject(i);
            dayForecasts.add(new Forecast.DayForecast(dayJson.getString(DATE),
                    parseWeather(dayJson.getJSONObject(WEATHER))));
        }
        return new Forecast.Data(current, dayForecasts);
    }

    private Forecast.Weather parseWeather(JSONObject jsonObject) throws JSONException {
        return new Forecast.Weather(jsonObject.getString(DESCRIPTION), jsonObject.getInt(TEMPERATURE));
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
