package com.csc.simple_weather;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static com.csc.simple_weather.MainActivity.CITY_UPD;

/**
 * Created by anastasia on 25.04.16.
 */
public class Updater extends BroadcastReceiver {
    public Updater() {}

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            String city = intent.getStringExtra(CITY_UPD);
            RemoteFetch.updateWeatherData(context, city);
        }
    }
}
