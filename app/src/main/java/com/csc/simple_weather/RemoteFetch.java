package com.csc.simple_weather;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import static com.csc.simple_weather.MainActivity.ENTRIES_URI;
import static com.csc.simple_weather.WeatherTable.*;

public class RemoteFetch {

    private static final String OPEN_WEATHER_MAP_API =
            "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";
    private static final String KEY = "3969da621640d52c3f016a41b7771585";

    public static JSONObject getJSON(String city){
        try {
            URL url = new URL(String.format(OPEN_WEATHER_MAP_API, city));
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.addRequestProperty("x-api-key",KEY);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp="";
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            if(data.getInt("cod") != 200){
                return null;
            }

            return data;
        }catch(Exception e){
            return null;
        }
    }
    public static City renderWeather(String city) throws Exception {

        try {
            JSONObject json = getJSON(city);
            JSONObject main = json.getJSONObject("main");
            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject wind = json.getJSONArray("wind").getJSONObject(0);
            DateFormat df = DateFormat.getDateTimeInstance();
            String upd = df.format(new Date(json.getLong("dt") * 1000));

            return new City(json.getString("name"), details.getString("description"),
                    String.format("%.2f", main.getDouble("temp")) + " ℃", main.getString("humidity"),
                    main.getString("pressure"), wind.getString("speed"), upd);


        } catch (Exception e) {
            throw new Exception("No such city found");
        }
    }

    public static void updateWeatherData(final Context context, final String city) {
        new AsyncTask<String, Void, Void>() {
            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
            }

            @Override
            protected Void doInBackground(String... params) {
                try {
                    if (params[0].equals("all")) {
                        Cursor cursor = context.getContentResolver().query(ENTRIES_URI, null, null, null, null, null);

                        while (cursor.moveToNext()) {
                            City city = City.fromCursor(cursor);
                            City newCity = renderWeather(city.name);

                            ContentValues values = new ContentValues();
                            values.put(COLUMN_WEATHER, newCity.weather);
                            values.put(COLUMN_CITY, newCity.name);
                            values.put(COLUMN_DESCRIPTION, newCity.description);
                            values.put(COLUMN_HUMIDITY, newCity.humidity);
                            values.put(COLUMN_PRESSURE, newCity.pressure);
                            values.put(COLUMN_WIND, newCity.wind);
                            values.put(COLUMN_LASTUPD, newCity.lastUpd);
                            context.getContentResolver().update(ENTRIES_URI, values, null, null);
                        }
                    } else {
                        City newCity = renderWeather(params[0]);
                        ContentValues values = new ContentValues();
                        values.put(COLUMN_WEATHER, newCity.weather);
                        values.put(COLUMN_CITY, newCity.name);
                        values.put(COLUMN_DESCRIPTION, newCity.description);
                        values.put(COLUMN_HUMIDITY, newCity.humidity);
                        values.put(COLUMN_PRESSURE, newCity.pressure);
                        values.put(COLUMN_WIND, newCity.wind);
                        values.put(COLUMN_LASTUPD, newCity.lastUpd);
                        context.getContentResolver().insert(ENTRIES_URI, values);
                    }
                } catch (Exception e) {
                }
                return null;
            }
            }.execute(city);
        }
}
