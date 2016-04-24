package com.csc.shmakov.weatherapp.model;

import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

import com.csc.shmakov.weatherapp.common.EventDispatcher;
import com.csc.shmakov.weatherapp.model.entities.City;
import com.csc.shmakov.weatherapp.model.entities.Forecast;
import com.csc.shmakov.weatherapp.model.entities.PendingForecast;
import com.csc.shmakov.weatherapp.storage.WeatherContentProvider;
import com.csc.shmakov.weatherapp.storage.serializers.CitySerializer;
import com.csc.shmakov.weatherapp.storage.serializers.ForecastSerializer;

import org.junit.Assert;

import java.util.List;

/**
 * Created by Pavel on 4/23/2016.
 */
public class StorageBridge extends EventDispatcher<StorageBridge.Observer> {
    private static StorageBridge instance;
    public synchronized static StorageBridge of(Application application) {
        if (instance == null) {
            instance = new StorageBridge(application);
        }
        return instance;
    }

    private final Context context;
    private final ContentResolver contentResolver;
    private final CitySerializer citySerializer = new CitySerializer();
    private final ForecastSerializer forecastSerializer = new ForecastSerializer();

    private StorageBridge(Context context) {
        this.context = context;
        contentResolver = context.getContentResolver();
        registerContentObservers();
    }

    private void registerContentObservers() {
        contentResolver.registerContentObserver(WeatherContentProvider.CITIES_URI, false,
                new ContentObserver(new Handler()) {
                    @Override
                    public void onChange(boolean selfChange, Uri uri) {
                        queryCitiesAndNotify();
                    }
                });
        contentResolver.registerContentObserver(WeatherContentProvider.FORECASTS_URI, true,
                new ContentObserver(new Handler()) {
                    @Override
                    public void onChange(boolean selfChange, Uri uri) {
                        queryForecastAndNotify(uri);
                    }
                });
    }

    public void readCities() {
        queryCitiesAndNotify();
    }

    private void queryCitiesAndNotify() {
        List<City> cities = queryCities();
        for (Observer observer : observers) {
            observer.onCitiesUpdated(cities);
        }
    }

    private List<City> queryCities() {
        Cursor cursor = contentResolver.query(WeatherContentProvider.CITIES_URI, null, null, null, null);
        List<City> cities = citySerializer.readFromCursor(cursor);
        cursor.close();
        return cities;
    }

    public void readForecasts() {
        Cursor cursor = contentResolver.query(WeatherContentProvider.FORECASTS_URI, null, null, null, null);
        List<Forecast> forecasts = forecastSerializer.readFromCursor(cursor);
        cursor.close();
        for (Observer observer : observers) {
            observer.onForecastsLoaded(forecasts);
        }
    }

    private void queryForecastAndNotify(Uri uri) {
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        List<Forecast> forecasts = forecastSerializer.readFromCursor(cursor);
        cursor.close();
        if (forecasts.size() != 1) {
            throw new RuntimeException();
        }
        for (Observer observer : observers) {
            observer.onForecastUpdated(forecasts.get(0));
        }
    }

    public void writeForecast(Forecast forecast) {
        ContentValues contentValues = forecastSerializer.toContentValues(forecast);
        contentResolver.insert(Uri.withAppendedPath(WeatherContentProvider.FORECASTS_URI, forecast.cityId),
                contentValues);
    }

    public void updateForecast(Forecast forecast) {
        ContentValues contentValues = forecastSerializer.toContentValues(forecast);
        contentResolver.update(Uri.withAppendedPath(WeatherContentProvider.FORECASTS_URI, forecast.cityId),
                contentValues, null, null);
    }

    public void deleteForecast(Forecast forecast) {
        ContentValues contentValues = forecastSerializer.toContentValues(forecast);
        contentResolver.delete(Uri.withAppendedPath(WeatherContentProvider.FORECASTS_URI, forecast.cityId),
                null, null);
    }

    abstract static class Observer {
        public void onCitiesUpdated(List<City> cities) {}
        public void onForecastsLoaded(List<Forecast> forecasts) {}
        public void onForecastUpdated(Forecast forecast) {}
    }

}
