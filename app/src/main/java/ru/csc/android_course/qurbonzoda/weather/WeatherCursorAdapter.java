package ru.csc.android_course.qurbonzoda.weather;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class WeatherCursorAdapter extends CursorAdapter {
  final WeatherListFragment fragment;
  public WeatherCursorAdapter(WeatherListFragment fragment, Context context, Cursor c, int flags) {
    super(context, c, flags);
    this.fragment = fragment;
  }

  @Override
  public View newView(Context context, Cursor cursor, ViewGroup parent) {
    return LayoutInflater.from(context).inflate(R.layout.weather_item, parent, false);
  }

  @Override
  public void bindView(View view, final Context context, final Cursor cursor) {
    final TextView cityName = (TextView) view.findViewById(R.id.city_name);
    final TextView currentTemp = (TextView) view.findViewById(R.id.current_temp);

    final String columnCityName = cursor.getString(cursor.getColumnIndex(WeatherTable.COLUMN_CITY_NAME));
    final String columnCountryName = cursor.getString(cursor.getColumnIndex(WeatherTable.COLUMN_COUNTRY));
    final String columnCurrentTemp = cursor.getString(cursor.getColumnIndex(WeatherTable.COLUMN_CURRENT_TEMP));
    final String id = cursor.getString(cursor.getColumnIndex(WeatherTable._ID));

    cityName.setText(String.format("%s, %s", columnCityName, columnCountryName));
    currentTemp.setText(columnCurrentTemp);

    view.setOnClickListener(v -> {
      fragment.getActivity().getSupportFragmentManager()
          .beginTransaction()
          .replace(R.id.fragment_main_container, CityWeatherFragment.newInstance(id, null))
          .addToBackStack(null)
          .commit();
    });
  }
}
