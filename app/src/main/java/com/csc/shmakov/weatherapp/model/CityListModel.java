package com.csc.shmakov.weatherapp.model;

import android.app.Application;
import android.content.Context;

import com.csc.shmakov.weatherapp.common.EventDispatcher;
import com.csc.shmakov.weatherapp.model.entities.City;
import com.csc.shmakov.weatherapp.model.entities.Forecast;
import com.csc.shmakov.weatherapp.services.WeatherApiService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pavel on 4/23/2016.
 */
public class CityListModel extends EventDispatcher<CityListModel.Observer> {
    private static CityListModel instance;
    public synchronized static CityListModel of(Application application) {
        if (instance == null) {
            instance = new CityListModel(application);
        }
        return instance;
    }

    private Map<String, City> cities = new HashMap<>();

    private Context context;
    private StorageBridge storageBridge;
    private final StorageObserver storageObserver = new StorageObserver();

    private CityListModel(Application application){
        this.context = application;
        storageBridge = StorageBridge.of(application);
        storageBridge.addObserver(storageObserver);
    }

    public List<String> getCityNames() {
        return new ArrayList<>(cities.keySet());
    }

    public boolean hasCity(String name) {
        return cities.containsKey(name);
    }

    public void fetchCities() {
        storageObserver.queryApiIfNoCitiesInStorage = true;
        storageBridge.readCities();
    }

    public City getCity(String name) {
        return cities.get(name);
    }

    public String getCityNameById(String cityId) {
        for (City city: cities.values()) {
            if (city.apiId.equals(cityId)) {
                return city.name;
            }
        }
        return "";
    }

    private class StorageObserver extends StorageBridge.Observer {
        private boolean queryApiIfNoCitiesInStorage;

        @Override
        public void onCitiesUpdated(List<City> cityList) {
            for (City city : cityList) {
                cities.put(city.name, city);
            }
            if (cities.isEmpty() && queryApiIfNoCitiesInStorage) {
                WeatherApiService.getCities(context);
                queryApiIfNoCitiesInStorage = false;
                return;
            }
            for (Observer o: observers) {
                o.onCitiesFetched();
            }
        }
    }

    public interface Observer {
        void onCitiesFetched();
    }

}
