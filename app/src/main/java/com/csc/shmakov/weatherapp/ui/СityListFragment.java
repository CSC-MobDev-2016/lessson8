package com.csc.shmakov.weatherapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.csc.lesson8.R;
import com.csc.shmakov.weatherapp.common.RecyclerViewDataChangeRouter;
import com.csc.shmakov.weatherapp.model.CityListModel;
import com.csc.shmakov.weatherapp.model.ForecastsModel;
import com.csc.shmakov.weatherapp.model.entities.Forecast;
import com.csc.shmakov.weatherapp.model.entities.PendingForecast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class СityListFragment extends Fragment {

    public static СityListFragment newInstance() {
        СityListFragment masterFragment = new СityListFragment();
        return masterFragment;
    }

    public СityListFragment() {}

    private CityListModel cityListModel;

    private ForecastsModel forecastsModel;

    private RecyclerViewDataChangeRouter recyclerViewDataChangeRouter;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    @Bind(R.id.cities_textview)
    AutoCompleteTextView citiesInput;

    @Bind(R.id.add_city_button)
    Button addCityButton;

    @Bind(R.id.update_all_button)
    Button updateAllButton;

    private FragmentInteractionListener fragmentInteractionListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        cityListModel = CityListModel.of(getActivity().getApplication());
        cityListModel.addObserver(cityListObserver);
        forecastsModel = ForecastsModel.of(getActivity().getApplication());
        recyclerViewDataChangeRouter = new RecyclerViewDataChangeRouter(adapter);
        forecastsModel.addObserver(recyclerViewDataChangeRouter);
        fragmentInteractionListener = (FragmentInteractionListener) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        cityListModel.removeObserver(cityListObserver);
        forecastsModel.removeObserver(recyclerViewDataChangeRouter);
        fragmentInteractionListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        prepareViews(view);
        cityListModel.fetchCities();
        return view;
    }

    private void prepareViews(View rootView) {
        ButterKnife.bind(this, rootView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        citiesInput.addTextChangedListener(cityNameInputWatcher);

        addCityButton.setOnClickListener(addClickListener);
        updateAllButton.setOnClickListener(updateAllClickListener);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.city_name_textview) TextView titleTextView;
        @Bind(R.id.data_textview) TextView dataTextView;
        @Bind(R.id.last_updated_textview) TextView lastUpdatedTextView;
        @Bind(R.id.progress_bar) ProgressBar progressBar;
        @Bind(R.id.remove_button) Button removeButton;
        @Bind(R.id.data_group) ViewGroup dataGroup;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindToItem(final Forecast forecast) {
            titleTextView.setText(cityListModel.getCityNameById(forecast.cityId));
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    forecastsModel.removeForecast(forecast);
                }
            });
            if (forecast instanceof PendingForecast) {
                progressBar.setVisibility(View.VISIBLE);
                dataGroup.setVisibility(View.GONE);
                itemView.setOnClickListener(null);
                return;
            }
            progressBar.setVisibility(View.GONE);
            dataGroup.setVisibility(View.VISIBLE);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentInteractionListener.onForecastSelected(forecast);
                }
            });

            Forecast.Weather currentWeather = forecast.data.current;
            dataTextView.setText(Common.formatWeatherString(currentWeather));

            lastUpdatedTextView.setText(Common.formatLastUpdatedString(forecast.lastUpdated));
        }
    }

    private final RecyclerView.Adapter<ViewHolder> adapter = new RecyclerView.Adapter<ViewHolder>() {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.weather_short_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bindToItem(forecastsModel.getForecasts().get(position));
        }

        @Override
        public int getItemCount() {
            return forecastsModel.getForecasts().size();
        }
    };

    private final CityListModel.Observer cityListObserver = new CityListModel.Observer() {
        @Override
        public void onCitiesFetched() {
            List<String> cityNames = cityListModel.getCityNames();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    cityNames.toArray(new String[cityNames.size()]));
            citiesInput.setAdapter(adapter);
        }
    };

    private final View.OnClickListener addClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            forecastsModel.addForecast(citiesInput.getText().toString());
        }
    };

    private final View.OnClickListener updateAllClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            forecastsModel.updateAll();
        }
    };

    private final TextWatcher cityNameInputWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            addCityButton.setEnabled(cityListModel.hasCity(s.toString()));
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void afterTextChanged(Editable s) {}
    };

    public interface FragmentInteractionListener {
        void onForecastSelected(Forecast forecast);
    }
}
