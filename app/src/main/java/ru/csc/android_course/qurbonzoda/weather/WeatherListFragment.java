package ru.csc.android_course.qurbonzoda.weather;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WeatherListFragment} interface
 * to handle interaction events.
 * Use the {@link WeatherListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
  public static final Uri ENTRIES_URI = Uri.withAppendedPath(WeatherContentProvider.CONTENT_URI, "entries");
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  ListView weatherListView;
  WeatherCursorAdapter weatherCursorAdapter;
  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;
  // NOTE: http://openweathermap.org/img/w/09d.png

  public WeatherListFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment WeatherListFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static WeatherListFragment newInstance(String param1, String param2) {
    WeatherListFragment fragment = new WeatherListFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
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
    View view = inflater.inflate(R.layout.weather_list, container, false);

    weatherListView = (ListView) view.findViewById(R.id.weather_list);
    weatherCursorAdapter = new WeatherCursorAdapter(this, getActivity(), null, 0);

    weatherListView.setAdapter(weatherCursorAdapter);
    getActivity().getSupportLoaderManager().initLoader(0, null, this);
    return view;
  }

  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    return new CursorLoader(getActivity(), ENTRIES_URI, null, null, null, null);
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    weatherCursorAdapter.swapCursor(data);
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) {
    weatherCursorAdapter.swapCursor(null);
  }

  public void addCity(String cityName) {
    getContext().getContentResolver().query(Uri.withAppendedPath(WeatherContentProvider.CONTENT_URI,
        "add_entry"), null, cityName, null, null);
  }
}
