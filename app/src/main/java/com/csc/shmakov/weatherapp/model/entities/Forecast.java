package com.csc.shmakov.weatherapp.model.entities;

import java.util.List;

/**
 * Created by Pavel on 4/23/2016.
 */
public class Forecast {
    public final String cityId;
    public final long lastUpdated;
    public final Data data;

    public Forecast(String cityId, long lastUpdated, Data data) {
        this.cityId = cityId;
        this.lastUpdated = lastUpdated;
        this.data = data;
    }

    public static class Data {
        public final Weather current;
        public final List<DayForecast> dayForecasts;

        public Data(Weather current, List<DayForecast> dayForecasts) {
            this.current = current;
            this.dayForecasts = dayForecasts;
        }
    }

    public static class DayForecast {
        public final String date;
        public final Weather weather;

        public DayForecast(String date, Weather weather) {
            this.date = date;
            this.weather = weather;
        }
    }

    public static class Weather {
        public final String description;
        public final int temperature;

        public Weather(String description, int temperature) {
            this.description = description;
            this.temperature = temperature;
        }
    }
}
