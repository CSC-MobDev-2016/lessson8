package com.csc.shmakov.weatherapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.csc.lesson8.R;
import com.csc.shmakov.weatherapp.common.DataChangeObserver;
import com.csc.shmakov.weatherapp.model.CityListModel;
import com.csc.shmakov.weatherapp.model.ForecastsModel;
import com.csc.shmakov.weatherapp.model.entities.Forecast;
import com.csc.shmakov.weatherapp.model.entities.PendingForecast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailWeatherFragment extends Fragment {
    private static final String ARG_CITY_ID = "ARG_CITY_ID";


    public static DetailWeatherFragment newInstance(Forecast forecast) {
        DetailWeatherFragment masterFragment = new DetailWeatherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CITY_ID, forecast.cityId);
        masterFragment.setArguments(args);
        return masterFragment;
    }

    public DetailWeatherFragment() {}

    private ForecastsModel forecastsModel;

    private CityListModel cityListModel;

    private Forecast forecast;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    @Bind(R.id.city_name_textview)
    TextView cityNameTextView;

    @Bind(R.id.last_updated_textview)
    TextView lastUpdatedTextview;

    @Bind(R.id.update_city_button)
    Button updateButton;

    @Bind(R.id.progress_bar)
    ProgressBar progressBar;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        forecastsModel = ForecastsModel.of(getActivity().getApplication());
        cityListModel = CityListModel.of(getActivity().getApplication());
        String cityId = getArguments().getString(ARG_CITY_ID);
        forecast = forecastsModel.getForecastByCityId(cityId);
        if (forecast == null) {
            throw new RuntimeException();
        }
        forecastsModel.addObserver(dataChangeObserver);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        forecastsModel.removeObserver(dataChangeObserver);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        prepareViews(view);
        return view;
    }

    private void prepareViews(View rootView) {
        ButterKnife.bind(this, rootView);
        updateViews();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void updateViews() {
        cityNameTextView.setText(cityListModel.getCityNameById(forecast.cityId));
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forecastsModel.updateForecast(forecast);
            }
        });

        if (forecast instanceof PendingForecast) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            updateButton.setEnabled(false);
            lastUpdatedTextview.setText("");
        } else {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            updateButton.setEnabled(true);
            lastUpdatedTextview.setText(Common.formatLastUpdatedString(forecast.lastUpdated));
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.date_textview) TextView dateTextView;
        @Bind(R.id.data_textview) TextView dataTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindToItem(final Forecast.DayForecast dayForecast) {
            dateTextView.setText(dayForecast.date);
            dataTextView.setText(Common.formatWeatherString(dayForecast.weather));
        }
    }

    private final RecyclerView.Adapter<ViewHolder> adapter = new RecyclerView.Adapter<ViewHolder>() {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.weather_full_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bindToItem(forecast.data.dayForecasts.get(position));
        }

        @Override
        public int getItemCount() {
            return forecast.data.dayForecasts.size();
        }
    };

    private final DataChangeObserver dataChangeObserver = new DataChangeObserver() {
        @Override
        public void onItemChanged(int position) {
            Forecast newForecast = forecastsModel.getForecasts().get(position);
            if (newForecast.cityId.equals(forecast.cityId)) {
                forecast = newForecast;
                updateViews();
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onItemInserted(int position) {}

        @Override
        public void onItemMoved(int from, int to) {}

        @Override
        public void onItemRemoved(int position) {}

        @Override
        public void onDataReset() {}
    };

}
