package com.csc.shmakov.weatherapp.model;

import android.app.Application;
import android.content.Context;

import com.csc.shmakov.weatherapp.common.DataChangeObserver;
import com.csc.shmakov.weatherapp.common.EventDispatcher;
import com.csc.shmakov.weatherapp.model.entities.City;
import com.csc.shmakov.weatherapp.model.entities.Forecast;
import com.csc.shmakov.weatherapp.model.entities.PendingForecast;
import com.csc.shmakov.weatherapp.services.ForecastFetchRepeater;
import com.csc.shmakov.weatherapp.services.WeatherApiService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel on 4/23/2016.
 */
public class ForecastsModel extends EventDispatcher<DataChangeObserver> {
    private static ForecastsModel instance;
    public synchronized static ForecastsModel of(Application application) {
        if (instance == null) {
            instance = new ForecastsModel(application);
        }
        return instance;
    }

    private final Context context;
    private final StorageBridge storageBridge;
    private List<Forecast> forecasts = new ArrayList<>();
    private final CityListModel cityListModel;

    private ForecastsModel(Application application){
        this.context = application;
        storageBridge = StorageBridge.of(application);
        cityListModel = CityListModel.of(application);
        storageBridge.addObserver(new StorageObserver());
        storageBridge.readForecasts();
    }

    public void addForecast(String cityName) {
        City city = cityListModel.getCity(cityName);
        if (city == null || hasForecast(city)) {
            return;
        }
        Forecast forecast = new PendingForecast(city.apiId);
        forecasts.add(forecast);
        for (DataChangeObserver observer : observers) {
            observer.onItemInserted(forecasts.size() - 1);
        }
        storageBridge.writeForecast(forecast);
        WeatherApiService.getForecast(context, city.apiId);
        ForecastFetchRepeater.setupRepeatingForCity(context, city.apiId);
    }

    private boolean hasForecast(City city) {
        for (Forecast forecast : forecasts) {
            if (forecast.cityId.equals(city.apiId)) {
                return true;
            }
        }
        return false;
    }

    public void removeForecast(Forecast forecast) {
        int position = forecasts.indexOf(forecast);
        if (position == -1) {
            return;
        }
        forecasts.remove(position);
        for (DataChangeObserver observer : observers) {
            observer.onItemRemoved(position);
        }

        storageBridge.deleteForecast(forecast);
        ForecastFetchRepeater.stopRepeating(context, forecast.cityId);
    }

    public List<Forecast> getForecasts() {
        return forecasts;
    }

    public Forecast getForecastByCityId(String cityId) {
        for (Forecast forecast : forecasts) {
            if (forecast.cityId.equals(cityId)) {
                return forecast;
            }
        }
        return null;
    }

    public void updateForecast(Forecast forecast) {
        if (!forecasts.contains(forecast)) {
            throw new RuntimeException();
        }
        Forecast pendingForecast = new PendingForecast(forecast.cityId);
        int position = forecasts.indexOf(forecast);
        forecasts.set(position, pendingForecast);
        for (DataChangeObserver observer : observers) {
            observer.onItemChanged(position);
        }
        storageBridge.updateForecast(pendingForecast);
        WeatherApiService.getForecast(context, forecast.cityId);
    }

    public void updateAll() {
        for (Forecast forecast : forecasts) {
            updateForecast(forecast);
        }
    }

    private class StorageObserver extends StorageBridge.Observer {
        @Override
        public void onForecastsLoaded(List<Forecast> forecasts) {
            ForecastsModel.this.forecasts = forecasts;
            for (DataChangeObserver observer: observers) {
                observer.onDataReset();
            }
        }

        @Override
        public void onForecastUpdated(Forecast updatedForecast) {
            if (updatedForecast instanceof PendingForecast) {
                return; // self-change
            }
            for (int i = 0; i < forecasts.size(); i++) {
                Forecast forecast = forecasts.get(i);
                if (forecast.cityId.equals(updatedForecast.cityId)) {
                    forecasts.set(i, updatedForecast);
                    for (DataChangeObserver observer: observers) {
                        observer.onItemChanged(i);
                    }
                    return;
                }
            }
        }
    }

}
