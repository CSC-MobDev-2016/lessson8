package com.csc.simple_weather;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListFragment.OnCitySelectedListener {
    private int counter = 0;
    private int hour = 60 * 60 * 1000;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    AlarmManager manager;
    public static final String CITY_UPD = "CITY_UPD";
    public static final Uri ENTRIES_URI = Uri.withAppendedPath(ReaderContentProvider.CONTENT_URI, "entries");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        //if (savedInstanceState == null) {
        //    addNewFragment();
        //}
        update();
    }

    @Override
    public void onCitySelected(String city) {
        ((ViewPagerAdapter)viewPager.getAdapter()).setItem
                (WeatherFragment.newInstance(city, this), 0);
        viewPager.setCurrentItem(0);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(WeatherFragment.newInstance(new CityPreference(this).getCity(), this), "FORECAST");
        adapter.addFragment(ListFragment.newInstance("Cities"), "CITIES");
        viewPager.setAdapter(adapter);
    }
    public void addData(View view) {
        ((ListFragment)((ViewPagerAdapter)viewPager.getAdapter()).getItem(1)).addData(view);
    }

    private void addNewFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_main_container, ListFragment.newInstance("#" + counter++ + ": " + new Date().toString()))
                .addToBackStack(null)
                .commit();
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
        public void setItem(Fragment f, int pos) {
            mFragmentList.set(pos, f);
        }

    }

    private void update() {
        Intent intent = new Intent(this, Updater.class);
        intent.putExtra(CITY_UPD, "all");
        manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        manager.cancel(pendingIntent);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis() + hour,
                hour, pendingIntent);
    }
}
