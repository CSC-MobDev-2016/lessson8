package com.csc.shmakov.weatherapp.model.entities;

/**
 * Created by Pavel on 4/23/2016.
 */
public class City {
    public final String name;
    public final String apiId;

    public City(String name, String apiId) {
        this.name = name;
        this.apiId = apiId;
    }

    @Override
    public String toString() {
        return name;
    }
}
