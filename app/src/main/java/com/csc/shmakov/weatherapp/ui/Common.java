package com.csc.shmakov.weatherapp.ui;

import com.csc.shmakov.weatherapp.model.entities.Forecast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Pavel on 4/24/2016.
 */
public class Common {
    public static String formatLastUpdatedString(long millis) {
        return "Last updated: " +
            new SimpleDateFormat("dd MMMM yyyy hh:mm:ss").format(new Date(millis));
    }

    public static String formatWeatherString(Forecast.Weather weather) {
        return weather.temperature + "°, " + weather.description;
    }
}
