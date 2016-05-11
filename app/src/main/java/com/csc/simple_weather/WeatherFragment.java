package com.csc.simple_weather;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.csc.simple_weather.MainActivity.ENTRIES_URI;

/**
 * Created by anastasia on 23.04.16.
 */
public class WeatherFragment extends Fragment {
    TextView city;
    TextView weather;
    TextView description;
    TextView humidity;
    TextView pressure;
    TextView wind;
    ImageView icon;
    Typeface weatherFont;
    City favCity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weather.ttf");
        RemoteFetch.updateWeatherData(getActivity(), new CityPreference(getActivity()).getCity());
    }

    public static WeatherFragment newInstance(String city, Context context) {
        WeatherFragment masterFragment = new WeatherFragment();
        RemoteFetch.updateWeatherData(context, city);
        Cursor cursor = context.getContentResolver().query(ENTRIES_URI, null, "city = ?",
                new String[] {city}, null);
        cursor.moveToNext();
        masterFragment.favCity = City.fromCursor(cursor);

        return masterFragment;
    }

    public void onDataUpd(Context context) {
        Cursor cursor = context.getContentResolver().query(ENTRIES_URI, null, "city = ?",
                new String[] {favCity.name}, null);
        cursor.moveToNext();
        favCity = City.fromCursor(cursor);
        city.setText(favCity.name);
        weather.setText(favCity.weather);
        description.setText(favCity.description);
        humidity.setText(favCity.humidity);
        pressure.setText(favCity.pressure);
        wind.setText(favCity.wind);
    }
    public void onDataUpd(Context context, String cityName) {
        Cursor cursor = context.getContentResolver().query(ENTRIES_URI, null, "city = ?",
                new String[] {cityName}, null);
        cursor.moveToNext();
        favCity = City.fromCursor(cursor);
        city.setText(favCity.name);
        weather.setText(favCity.weather);
        description.setText(favCity.description);
        humidity.setText(favCity.humidity);
        pressure.setText(favCity.pressure);
        wind.setText(favCity.wind);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (favCity == null){
            Cursor cursor = getActivity().getContentResolver().query(ENTRIES_URI, null, "city = ?",
                    new String[] {new CityPreference(getActivity()).getCity()}, null);
            cursor.moveToNext();
            favCity = City.fromCursor(cursor);
        }
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        city = (TextView)view.findViewById(R.id.favCityView);
        weather = (TextView)view.findViewById(R.id.favTempView);
        description = (TextView)view.findViewById(R.id.favDescView);
        humidity = (TextView)view.findViewById(R.id.favHumidView);
        pressure = (TextView)view.findViewById(R.id.favPressureView);
        wind = (TextView)view.findViewById(R.id.favWindView);
        icon = (ImageView)view.findViewById(R.id.star);

        city.setText(favCity.name);
        weather.setText(favCity.weather);
        description.setText(favCity.description);
        humidity.setText(favCity.humidity);
        pressure.setText(favCity.pressure);
        wind.setText(favCity.wind);
        return view;
    }



}
