package com.csc.jv.weather;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements
        CityListFragment.OnListFragmentInteractionListener,
        ExtendCityFragment.OnFragmentInteractionListener {

    public static final Uri FORECAST_ENTRIES_URI = Uri.withAppendedPath(WeatherContentProvider.CONTENT_URI, "entries");

    public static final Uri CITY_ENTRIES_URI = Uri.withAppendedPath(CityContentProvider.CONTENT_URI, "entries");

    private static final String[] defaultCities = {"27612", "26063", "31960", "72503"};

    public static final String FORECAST_URL = "http://export.yandex.ru/weather-ng/forecasts/";

    public static final String CITY_URL = "http://pogoda.yandex.ru/static/cities.xml";

    private Button addButton;
    private EditText addEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        if (savedInstanceState == null) {

            Intent intent = new Intent(this, WeatherService.class);

            for (String city : defaultCities) {
                String urlString = FORECAST_URL + city + ".xml";
                intent.setAction(WeatherService.FORECAST).putExtra(WeatherService.FORECAST_URL, urlString);
                startService(intent);
            }

            intent.setAction(WeatherService.CITIES);
            startService(intent);


            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_main_container, CityListFragment.newInstance(1))
                    .commit();
        }

        addButton = (Button) findViewById(R.id.add_button);
        addEditText = (EditText) findViewById(R.id.add_editText);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = String.valueOf((addEditText.getText())).toLowerCase().trim();
                String selection = CityTable.COLUMN_CITY_NAME + "=?";
                String[] selectionArgs = new String[]{city};
                Cursor cursor = getContentResolver().query(CITY_ENTRIES_URI, null, selection, selectionArgs, null, null);

                String city_id = null;
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        city_id = cursor.getString(cursor.getColumnIndex(CityTable.COLUMN_CITY_ID));
                    }
                    cursor.close();
                }

                if (city_id != null) {
                    DownloadForecastXmlTask downloadForecastXmlTask = new DownloadForecastXmlTask(MainActivity.this);
                    downloadForecastXmlTask.execute(FORECAST_URL + city_id + ".xml");
                } else {
                    Toast.makeText(MainActivity.this, R.string.city_not_founded, Toast.LENGTH_LONG).show();
                }

                addEditText.setText("");
            }
        });
    }

    @Override
    public void onListFragmentInteraction(ForecastItem forecastItem, String cursor_id) {
        Log.d("MainActivity", forecastItem.city_name);
        ExtendCityFragment extendCityFragment = ExtendCityFragment.newInstance(
                forecastItem.city_id, forecastItem.city_name, forecastItem.update_time, forecastItem.temperature,
                forecastItem.weather_type, forecastItem.wind_direction, forecastItem.wind_speed, forecastItem.humidity,
                forecastItem.pressure, forecastItem.mslp_pressure, forecastItem.daytime, forecastItem.temperature, cursor_id);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_main_container, extendCityFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
