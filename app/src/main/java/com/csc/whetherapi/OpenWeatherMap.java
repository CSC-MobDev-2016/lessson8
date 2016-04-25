package com.csc.whetherapi;

import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Oleg Doronin
 * WhetherApp
 * Copyright (c) 2016 CS. All rights reserved.
 */
public class OpenWeatherMap {

    public static ArrayList<WeatherDescription> getWeather(@NonNull ArrayList<Integer> cities)
    {
        ArrayList<WeatherDescription> list = new ArrayList<>();
        for (Integer c : cities) {
            Hashtable<String, String> hash = new Hashtable<>();
            hash.put("APPID", "32a94831355c8ed914afc76bdc93ffa4");
            hash.put("id", c.toString());
            try {
                list.add(WeatherParser.parse(GETRequest.sendGet(hash)));
            } catch (Exception ex) {
            }
        }
        return list;
    }
}
