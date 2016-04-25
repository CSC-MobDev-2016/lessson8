package com.csc.jv.weather;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExtendCityFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExtendCityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExtendCityFragment extends Fragment {

    private static final String CITY_ID = "city_id";
    private static final String CITY_NAME = "city_name";
    private static final String UPDATE_TIME = "observation_time";
    private static final String TEMPERATURE = "temperature";
    private static final String WEATHER_TYPE = "weather_type";
    private static final String WIND_DIRECTION = "wind_direction";
    private static final String WIND_SPEED = "wind_speed";
    private static final String HUMIDITY = "humidity";
    private static final String PRESSURE = "pressure";
    private static final String MSLP_PRESSURE = "mslp_pressure";
    private static final String DAYTIME = "daytime";
    private static final String WATER_TEMPERATURE = "water_temperature";
    private static final String CURSOR_ID = "cursor_id";


    private ForecastItem forecastItem;
    private String cursor_id;
    private UpdateBroadcastReceiver updateBroadcastReceiver;

    private OnFragmentInteractionListener mListener;

    public ExtendCityFragment() {
        // Required empty public constructor
    }


    public static ExtendCityFragment newInstance(String city_id, String city_name, String update_time, String temperature, String weather_type,
                                                 String wind_direction, String wind_speed, String humidity, String pressure,
                                                 String mslp_pressure, String daytime, String water_temperature, String _id) {
        ExtendCityFragment fragment = new ExtendCityFragment();
        Bundle args = new Bundle();
        args.putString(CURSOR_ID, _id);
        args.putString(CITY_ID, city_id);
        args.putString(CITY_NAME, city_name);
        args.putString(UPDATE_TIME, update_time);
        args.putString(TEMPERATURE, temperature);
        args.putString(WEATHER_TYPE, weather_type);
        args.putString(WIND_DIRECTION, wind_direction);
        args.putString(WIND_SPEED, wind_speed);
        args.putString(HUMIDITY, humidity);
        args.putString(PRESSURE, pressure);
        args.putString(MSLP_PRESSURE, mslp_pressure);
        args.putString(DAYTIME, daytime);
        args.putString(WATER_TEMPERATURE, water_temperature);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            cursor_id = getArguments().getString(CURSOR_ID);
            String city_id = getArguments().getString(CITY_ID);
            String city_name = getArguments().getString(CITY_NAME);
            String update_time = getArguments().getString(UPDATE_TIME);
            String temperature = getArguments().getString(TEMPERATURE);
            String weather_type = getArguments().getString(WEATHER_TYPE);
            String wind_direction = getArguments().getString(WIND_DIRECTION);
            String wind_speed = getArguments().getString(WIND_SPEED);
            String humidity = getArguments().getString(HUMIDITY);
            String pressure = getArguments().getString(PRESSURE);
            String mslp_pressure = getArguments().getString(MSLP_PRESSURE);
            String daytime = getArguments().getString(DAYTIME);
            String water_temperature = getArguments().getString(WATER_TEMPERATURE);

            forecastItem = new ForecastItem(city_id, city_name, update_time, temperature, weather_type,
                    wind_direction, wind_speed, humidity, pressure,
                    mslp_pressure, daytime, water_temperature);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        updateBroadcastReceiver = new UpdateBroadcastReceiver();

        IntentFilter intentFilter = new IntentFilter(WeatherService.RESPONSE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        getActivity().registerReceiver(updateBroadcastReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();

        getActivity().unregisterReceiver(updateBroadcastReceiver);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_extend_city, container, false);
        ((TextView) view.findViewById(R.id.update_time)).setText(forecastItem.update_time);
        ((TextView) view.findViewById(R.id.city_name)).setText(forecastItem.city_name);
        ((TextView) view.findViewById(R.id.city_temperature)).setText(forecastItem.temperature);
        ((TextView) view.findViewById(R.id.weather_type)).setText(forecastItem.weather_type);
        ((TextView) view.findViewById(R.id.wind_direction)).setText(forecastItem.wind_direction);
        ((TextView) view.findViewById(R.id.wind_speed)).setText(forecastItem.wind_speed);
        ((TextView) view.findViewById(R.id.humidity)).setText(forecastItem.humidity);
        ((TextView) view.findViewById(R.id.pressure)).setText(forecastItem.pressure);
        ((TextView) view.findViewById(R.id.mslp_pressure)).setText(forecastItem.mslp_pressure);
        ((TextView) view.findViewById(R.id.daytime)).setText(forecastItem.daytime);
        ((TextView) view.findViewById(R.id.water_temperature)).setText(forecastItem.water_temperature);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.refresh_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh :
                Intent intent = new Intent(getActivity(), WeatherService.class);{
                String urlString = MainActivity.FORECAST_URL + forecastItem.city_id + ".xml";
                intent.setAction(WeatherService.FORECAST).putExtra(WeatherService.FORECAST_URL, urlString);
                getActivity().startService(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    public class UpdateBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String update_time = intent.getStringExtra(WeatherService.UPDATE_TIME);
            TextView textView = (TextView) getView().findViewById(R.id.update_time);
            textView.setText(update_time);
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
