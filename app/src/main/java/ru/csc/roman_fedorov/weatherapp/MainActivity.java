package ru.csc.roman_fedorov.weatherapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private MyCursorAdapter adapter;

    public static final Uri DISTINCT_VALUES_URI = Uri.withAppendedPath(WeatherContentProvider.CONTENT_URI, "distinct");
    public static final String QUERIED_CITY = "QUERIED_CITY";

    public static String getQuery(String city) {
        try {
            return "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from" +
                    "%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from" +
                    "%20geo.places(1)%20where%20text%3D" +
                    URLEncoder.encode("\"" + city + "\"", "utf-8") +
                    ")&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportLoaderManager().initLoader(0, null, this);

        Intent serviceIntent = new Intent(this, AutoUpdateWeatherService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, serviceIntent, 0);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(MainActivity.ALARM_SERVICE);
        int hour = 1000 * 60 * 60;
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, hour, hour, pendingIntent);

        Button queryButton = (Button) findViewById(R.id.query_city_button);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText cityET = (EditText) findViewById(R.id.input_city);
                String queryUrl = getQuery(cityET.getText().toString());
                new DownloadWeatherData().execute(queryUrl);
            }
        });

        final ListView citiesLV = (ListView) findViewById(R.id.cities_list);
        adapter = new MyCursorAdapter(this, null, 0);
        citiesLV.setAdapter(adapter);
        citiesLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor c = adapter.getCursor();
                c.moveToPosition(position);
                String cityName = c.getString(c.getColumnIndex(WeatherTable.WEATHER_CITY));
                Intent intent = new Intent(MainActivity.this, DetailWeatherActivity.class);
                intent.putExtra(QUERIED_CITY, cityName);
                startActivity(intent);
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, DISTINCT_VALUES_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    class DownloadWeatherData extends AsyncTask<String, Void, ForecastItem[]> {
        private String location;

        @Override
        protected ForecastItem[] doInBackground(String... strings) {
            try {
                Scanner s = new java.util.Scanner(downloadUrl(strings[0])).useDelimiter("\\A");
                String json = s.hasNext() ? s.next() : "";
                JSONObject serverOutput = new JSONObject(json);
                JSONObject results = serverOutput.getJSONObject("query").getJSONObject("results");
                JSONObject item = results.getJSONObject("channel").getJSONObject("item");
                JSONObject location = results.getJSONObject("channel").getJSONObject("location");
                this.location = location.getString("city") + ", " + location.getString("country");
                JSONArray jsonForecast = item.getJSONArray("forecast");
                ForecastItem[] forecast = new ForecastItem[jsonForecast.length()];
                for (int i = 0; i < jsonForecast.length(); i++) {
                    JSONObject jsonForecastItem = jsonForecast.getJSONObject(i);
                    forecast[i] = new ForecastItem(jsonForecastItem.getString("date"),
                            jsonForecastItem.getInt("high"),
                            jsonForecastItem.getInt("low"),
                            jsonForecastItem.getString("text"));
                }
                return forecast;
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(ForecastItem[] data) {
            if (data == null) {
                Toast.makeText(MainActivity.this, R.string.no_internet_connection, Toast.LENGTH_LONG).show();
            } else {
                for (ForecastItem currentItem : data) {
                    ContentValues newValues = new ContentValues();
                    newValues.put(WeatherTable.WEATHER_CITY, location);
                    newValues.put(WeatherTable.WEATHER_DATE, currentItem.date);
                    newValues.put(WeatherTable.WEATHER_DESCRIPTION, currentItem.description);
                    newValues.put(WeatherTable.WEATHER_LOW_TEMPERATURE, currentItem.lowTemperature);
                    newValues.put(WeatherTable.WEATHER_HIGH_TEMPERATURE, currentItem.highTemperature);
                    String mSelectionClause = WeatherTable.WEATHER_CITY + " LIKE ? AND " + WeatherTable.WEATHER_DATE + " LIKE ?";
                    String[] mSelectionArgs = {location, currentItem.date};
                    int rowsUpdated = getContentResolver().update(MainActivity.DISTINCT_VALUES_URI, newValues, mSelectionClause, mSelectionArgs);
                    if (rowsUpdated == 0) {
                        getContentResolver().insert(MainActivity.DISTINCT_VALUES_URI, newValues);
                    }
                }
            }
        }

        private InputStream downloadUrl(String urlString) throws IOException {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(10000 /* milliseconds */);
            return conn.getInputStream();
        }
    }
}
