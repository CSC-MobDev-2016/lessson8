package com.csc.shmakov.weatherapp.api.forecast;

import com.csc.shmakov.weatherapp.api.forecast.ForecastParser;
import com.csc.shmakov.weatherapp.model.entities.Forecast;

import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Pavel on 4/23/2016.
 */
public class ForecastRequestLauncher {

    private final OkHttpClient client = new OkHttpClient();

    public Forecast run(String cityId) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("export.yandex.ru")
                .addPathSegment("weather-ng")
                .addPathSegment("forecasts")
                .addPathSegment(cityId + ".xml")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.code() != 200) {
                throw new RuntimeException();
            }
            Forecast.Data data = new ForecastParser().parse(response.body().string());
            return new Forecast(cityId, System.currentTimeMillis(), data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
