package com.csc.shmakov.weatherapp.model.entities;

/**
 * Created by Pavel on 4/23/2016.
 */
public class PendingForecast extends Forecast {
    public PendingForecast(String cityId) {
        super(cityId, 0, null);
    }
}
