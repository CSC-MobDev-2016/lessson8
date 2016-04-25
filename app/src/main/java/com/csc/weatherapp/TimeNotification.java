package com.csc.weatherapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TimeNotification extends BroadcastReceiver {
    public TimeNotification() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            WeatherTask task = new WeatherTask(context.getContentResolver());
            task.execute();
        }
    }
}
