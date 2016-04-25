package ru.csc.roman_fedorov.weatherapp;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class DetailWeatherActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private MyCursorAdapter adapter;
    public static final Uri CITY_VALUES_URI = Uri.withAppendedPath(WeatherContentProvider.CONTENT_URI, "all");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_weather);
        getSupportLoaderManager().initLoader(0, null, this);

        final ListView citiesLV = (ListView) findViewById(R.id.detailed_weather_list);
        adapter = new MyCursorAdapter(this, null, 0);
        citiesLV.setAdapter(adapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String city = getIntent().getStringExtra(MainActivity.QUERIED_CITY);
        String mSelectionClause = WeatherTable.WEATHER_CITY + " LIKE ?";
        String[] mSelectionArgs = {city};

        return new CursorLoader(this, CITY_VALUES_URI, null, mSelectionClause, mSelectionArgs, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
