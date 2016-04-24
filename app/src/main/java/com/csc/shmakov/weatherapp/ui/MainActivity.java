package com.csc.shmakov.weatherapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.csc.lesson8.R;
import com.csc.shmakov.weatherapp.model.entities.Forecast;

public class MainActivity extends AppCompatActivity implements СityListFragment.FragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            showMainFragment();
        }
    }

    private void showMainFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, СityListFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void onForecastSelected(Forecast forecast) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, DetailWeatherFragment.newInstance(forecast))
                .addToBackStack(null)
                .commit();
    }
}
