package com.csc.simple_weather;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weather.ttf");
        RemoteFetch.updateWeatherData(getActivity(), "Sydney");
        RemoteFetch.updateWeatherData(getActivity(), new CityPreference(getActivity()).getCity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        city = (TextView)view.findViewById(R.id.cityView);
        weather = (TextView)view.findViewById(R.id.weatherView);
        description = (TextView)view.findViewById(R.id.favDescView);
        humidity = (TextView)view.findViewById(R.id.favHumidView);
        pressure = (TextView)view.findViewById(R.id.favPressureView);
        wind = (TextView)view.findViewById(R.id.favWindView);
        icon = (ImageView)view.findViewById(R.id.star);
        return super.onCreateView(inflater, container, savedInstanceState);
    }



}
