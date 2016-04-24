package com.csc.shmakov.weatherapp.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.csc.shmakov.weatherapp.model.entities.City;

/**
 * Created by Pavel on 4/23/2016.
 */
public class ForecastFetchRepeater {
    private static final String EXTRA_CITY_API_ID = "EXTRA_CITY_API_ID";
    private static final int INTERVAL = 3600_000;

    public static void setupRepeatingForCity(Context context, String cityId) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_CITY_API_ID, cityId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, cityId.hashCode(), intent, 0);

        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + INTERVAL,
               INTERVAL, pendingIntent);
    }

    public static void stopRepeating(Context context, String cityId) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, cityId.hashCode(), intent, 0);
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.cancel(pendingIntent);
    }

    public static class AlarmReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String apiId = intent.getStringExtra(EXTRA_CITY_API_ID);
            WeatherApiService.getForecast(context, apiId);
        }
    }
}
