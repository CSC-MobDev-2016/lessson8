package com.csc.jv.weather;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DownloadForecastXmlTask extends AsyncTask<String, Void, List<ForecastItem>> {

    private Context context;

    public DownloadForecastXmlTask(Context context) {
        this.context = context;
    }

    @Override
    protected List<ForecastItem> doInBackground(String... urls) {
        try {
            return loadXmlFromNetwork(urls[0]);
        } catch (IOException | XmlPullParserException e) {
            return null;
        }
    }


    @Override
    protected void onPostExecute(List<ForecastItem> result) {

        if (result != null) {
            for (ForecastItem item : result) {

                ContentValues values = new ContentValues();

                values.put(ForecastTable.COLUMN_CITY_ID, item.city_id);
                values.put(ForecastTable.COLUMN_CITY_NAME, item.city_name);
                values.put(ForecastTable.COLUMN_UPDATE_TIME, item.update_time);
                values.put(ForecastTable.COLUMN_TEMPERATURE, item.temperature);
                values.put(ForecastTable.COLUMN_WEATHER_TYPE, item.weather_type);
                values.put(ForecastTable.COLUMN_WIND_DIRECTION, item.wind_direction);
                values.put(ForecastTable.COLUMN_WIND_SPEED, item.wind_speed);
                values.put(ForecastTable.COLUMN_HUMIDITY, item.humidity);
                values.put(ForecastTable.COLUMN_PRESSURE, item.pressure);
                values.put(ForecastTable.COLUMN_MSLP_PRESSURE, item.mslp_pressure);
                values.put(ForecastTable.COLUMN_DAYTIME, item.daytime);
                values.put(ForecastTable.COLUMN_WATER_TEMPERATURE, item.water_temperature);

                context.getContentResolver().insert(MainActivity.FORECAST_ENTRIES_URI, values);
            }
        }

    }


    private List loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {
        InputStream stream = null;
        // Instantiate the parser
        ForecastXMLparser xmlParser = new ForecastXMLparser();
        List<ForecastItem> entries = null;

        try {
            stream = downloadUrl(urlString);
            entries = xmlParser.parse(stream);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }

        return entries;
    }


    private InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //conn.setReadTimeout(10000 /* milliseconds */);
        //conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        Log.d("Connection", conn.getResponseMessage());
        // Starts the query
        conn.connect();
        return conn.getInputStream();
    }
}