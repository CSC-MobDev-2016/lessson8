package com.csc.jv.weather;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class WeatherService extends IntentService {

    public static final String CITIES = "cities";
    public static final String RESPONSE = "response";
    public static final String FORECAST = "forecast";
    public static final String FORECAST_UPDATE = "forecast_update";
    public static final String FORECAST_URL = "forecast";
    public static final String FORECAST_ID = "forecast_id";
    public static final String UPDATE_TIME = "update_time";

    public WeatherService() {
        super("WeatherService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String urlString;

            String action = intent.getAction();
            switch (action) {
                case CITIES:
                    try {
                        List<CityItem> result = loadXmlFromResourses();

                        if (result != null) {
                            for (CityItem item : result) {

                                ContentValues values = new ContentValues();

                                values.put(CityTable.COLUMN_CITY_ID, item.city_id);
                                values.put(CityTable.COLUMN_CITY_NAME, item.city_name.toLowerCase().trim());
                                Log.d("download", item.city_name.toLowerCase().trim());

                                getContentResolver().insert(MainActivity.CITY_ENTRIES_URI, values);
                            }
                        }

                    } catch (XmlPullParserException | IOException ignored) {

                    }


                    break;
                case FORECAST:
                    urlString = intent.getStringExtra(FORECAST_URL);
                    try {

                        List<ForecastItem> result = loadXmlFromNetwork(urlString);

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

                                getContentResolver().insert(MainActivity.FORECAST_ENTRIES_URI, values);
                            }

                            SimpleDateFormat format = new SimpleDateFormat(getString(R.string.date_format), Locale.getDefault());

                            Intent responseIntent = new Intent();
                            responseIntent.setAction(RESPONSE);
                            responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
                            responseIntent.putExtra(UPDATE_TIME, format.format(new Date()));
                            sendBroadcast(responseIntent);
                        }
                    } catch (XmlPullParserException | IOException e) {
                    }
                    break;
                default:
                    throw new UnsupportedOperationException();
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
        // Starts the query
        conn.connect();
        return conn.getInputStream();
    }

    private List loadXmlFromResourses() throws XmlPullParserException, IOException {
        InputStream stream = null;
        // Instantiate the parser
        CityXMLparser xmlParser = new CityXMLparser();
        List<CityItem> entries = null;

        try {
            stream = getResourseStream();
            entries = xmlParser.parse(stream);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }

        return entries;
    }


    private InputStream getResourseStream() {
        return getResources().openRawResource(R.raw.cities);
    }


}
