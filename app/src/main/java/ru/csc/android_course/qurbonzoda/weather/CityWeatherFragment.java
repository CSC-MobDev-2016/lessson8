package ru.csc.android_course.qurbonzoda.weather;


import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Arrays;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CityWeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CityWeatherFragment extends Fragment {
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;


  public CityWeatherFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment CityWeatherFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static CityWeatherFragment newInstance(String param1, String param2) {
    CityWeatherFragment fragment = new CityWeatherFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  private static int makeRandomColor() {
    Random rnd = new Random();
    return Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_city_weather, container, false);


    TextView cityName = (TextView) view.findViewById(R.id.detail_city_name);
    TextView countryName = (TextView) view.findViewById(R.id.detail_country_name);
    Button updateButton = (Button) view.findViewById(R.id.detail_update_button);
    ImageView weatherIcon = (ImageView) view.findViewById(R.id.detail_weather_icon);
    TextView weatherDesc = (TextView) view.findViewById(R.id.detail_current_weather_desc);
    TextView minMaxTemp = (TextView) view.findViewById(R.id.detail_min_max_temp);
    TextView pressure = (TextView) view.findViewById(R.id.detail_pressure);
    TextView humidity = (TextView) view.findViewById(R.id.detail_humidity);
    TextView wind = (TextView) view.findViewById(R.id.detail_wind);

    Cursor cursor = getActivity().getContentResolver().query(Uri.withAppendedPath(
        WeatherListFragment.ENTRIES_URI, mParam1), null, null, null, null);
    assert cursor != null;

    cursor.moveToFirst();

    cityName.setText(cursor.getString(cursor.getColumnIndexOrThrow(WeatherTable.COLUMN_CITY_NAME)));
    countryName.setText(cursor.getString(cursor.getColumnIndex(WeatherTable.COLUMN_COUNTRY)));
    Glide.with(weatherIcon.getContext())
        .load(String.format("http://openweathermap.org/img/w/%s.png",
            cursor.getString(cursor.getColumnIndex(WeatherTable.COLUMN_WEATHER_ICON))))
        .into(weatherIcon);
    weatherDesc.setText(cursor.getString(cursor.getColumnIndex(WeatherTable.COLUMN_WEATHER_DESCRIPTION)));
    minMaxTemp.setText(String.format("from %s C | to %s C",
        cursor.getString(cursor.getColumnIndex(WeatherTable.COLUMN_TEMP_MIN)),
        cursor.getString(cursor.getColumnIndex(WeatherTable.COLUMN_TEMP_MAX))));
    pressure.setText(String.format("Pressure %s hPa", cursor.getString(cursor.getColumnIndex(WeatherTable.COLUMN_PRESSURE))));
    humidity.setText(String.format("Humidity %s %%", cursor.getString(cursor.getColumnIndex(WeatherTable.COLUMN_HUMIDITY))));
    wind.setText(String.format("Wind %s hPa | %s deg",
        cursor.getString(cursor.getColumnIndex(WeatherTable.COLUMN_WIND_SPEED)),
        cursor.getString(cursor.getColumnIndex(WeatherTable.COLUMN_WIND_DEGREE))));

    cursor.close();
    view.setBackgroundColor(makeRandomColor());
    return view;
  }

}
