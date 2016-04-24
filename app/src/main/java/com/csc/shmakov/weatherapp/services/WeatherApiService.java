package com.csc.shmakov.weatherapp.services;

import android.app.IntentService;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.net.Uri;

import com.csc.shmakov.weatherapp.api.cities.CityRequestLauncher;
import com.csc.shmakov.weatherapp.api.forecast.ForecastRequestLauncher;
import com.csc.shmakov.weatherapp.model.entities.City;
import com.csc.shmakov.weatherapp.model.entities.Forecast;
import com.csc.shmakov.weatherapp.storage.WeatherContentProvider;
import com.csc.shmakov.weatherapp.storage.serializers.CitySerializer;
import com.csc.shmakov.weatherapp.storage.serializers.ForecastSerializer;

import java.util.List;

public class WeatherApiService extends IntentService {
    private static final String ACTION_CITIES = "com.csc.shmakov.weatherapp.action.cities";
    private static final String ACTION_FORECAST = "com.csc.shmakov.weatherapp.action.forecast";

    private static final String EXTRA_CITY_ID = "com.csc.shmakov.weatherapp.extra.city";

    private final CityRequestLauncher cityRequestLauncher = new CityRequestLauncher();
    private final CitySerializer citySerializer = new CitySerializer();

    private final ForecastRequestLauncher forecastRequestLauncher = new ForecastRequestLauncher();
    private final ForecastSerializer forecastSerializer = new ForecastSerializer();

    public WeatherApiService() {
        super("WeatherApiService");
    }


    public static void getCities(Context context) {
        Intent intent = new Intent(context, WeatherApiService.class);
        intent.setAction(ACTION_CITIES);
        context.startService(intent);
    }

    public static void getForecast(Context context, String apiId) {
        Intent intent = new Intent(context, WeatherApiService.class);
        intent.setAction(ACTION_FORECAST);
        intent.putExtra(EXTRA_CITY_ID, apiId);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null) {
            return;
        }
        String action = intent.getAction();
        if (ACTION_CITIES.equals(action)) {
            handleActionCities();
        } else if (ACTION_FORECAST.equals(action)) {
            handleActionForecast(intent);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    private void handleActionCities() {
        List<City> cities = cityRequestLauncher.run();
        ContentValues[] contentValues = new ContentValues[cities.size()];
        for (int i = 0; i < cities.size(); i++) {
            contentValues[i] = citySerializer.toContentValues(cities.get(i));
        }
        getContentResolver().bulkInsert(WeatherContentProvider.CITIES_URI, contentValues);
    }

    private void handleActionForecast(Intent intent) {
        String cityApiId = intent.getStringExtra(EXTRA_CITY_ID);
        try {
            Forecast forecast = forecastRequestLauncher.run(cityApiId);
            ContentValues contentValues = forecastSerializer.toContentValues(forecast);
            getContentResolver().update(Uri.withAppendedPath(WeatherContentProvider.FORECASTS_URI, cityApiId),
                    contentValues, null, null);
        } catch (Exception e) {}
    }
}
