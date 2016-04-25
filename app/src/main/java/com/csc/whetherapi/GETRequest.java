package com.csc.whetherapi;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Hashtable;

public class GETRequest {
    private static final String url = "http://api.openweathermap.org/data/2.5/weather";
    private static final String encoding = "UTF-8";
    private static final int TIME_OUT = 5000;

    public static String sendGet(@NonNull final Hashtable<String, String> parameters) throws Exception {

        boolean firstStep = true;
        String url = GETRequest.url;
        if (parameters != null) {
            Enumeration<String> keys = parameters.keys();
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                String value = URLEncoder.encode(parameters.get(key), encoding);
                key = URLEncoder.encode(key, encoding);
                url += (firstStep ? '?' : '&') + key + '=' + value;
                firstStep = false;
            }
        }
        Log.d("TAG", "GO");
        URL urlObject = new URL(url);
        HttpURLConnection connection = (HttpURLConnection)urlObject.openConnection();
        connection.setConnectTimeout(TIME_OUT);
        connection.setReadTimeout(TIME_OUT);
        connection.setRequestProperty("Content-Type", "text/plain; charset=" + encoding);
        connection.setRequestProperty("Accept-Charset", encoding);
        connection.setRequestMethod("GET");

        BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = input.readLine()) != null) {
            response.append(inputLine);
        }
        input.close();

        return response.toString();
    }
}