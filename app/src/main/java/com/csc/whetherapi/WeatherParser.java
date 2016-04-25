package com.csc.whetherapi;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;

import java.util.Hashtable;

/**
 * Created by Oleg Doronin
 * WhetherApp
 * Copyright (c) 2016 CS. All rights reserved.
 */
public class WeatherParser {

    private static final String TAG_GET_REQUEST = "TAG_GET_REQUEST";

    public static WeatherDescription parse(@NonNull String request) {
        Gson gson = new Gson();
        return gson.fromJson(request, WeatherDescription.class);
    }

}
