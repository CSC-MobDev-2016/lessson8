package com.csc.shmakov.weatherapp.api.cities;

import com.csc.shmakov.weatherapp.model.entities.City;

import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Pavel on 4/23/2016.
 */
public class CityRequestLauncher {

    private final OkHttpClient client = new OkHttpClient();

    public List<City> run() {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("pogoda.yandex.ru")
                .addPathSegment("static")
                .addPathSegment("cities.xml")
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
            return new CityParser().parse(response.body().string());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
