package com.csc.jv.weather;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

public class DownloadCityXmlTask extends AsyncTask<String, Void, List<CityItem>> {

    private Context context;

    public DownloadCityXmlTask(Context context) {
        this.context = context;
    }

    @Override
    protected List<CityItem> doInBackground(String... urls) {
        try {
            return loadXmlFromNetwork(urls[0]);
        } catch (IOException | XmlPullParserException e) {
            return null;
        }
    }


    @Override
    protected void onPostExecute(List<CityItem> result) {

        if (result != null) {
            for (CityItem item : result) {

                ContentValues values = new ContentValues();

                values.put(CityTable.COLUMN_CITY_ID, item.city_id);
                values.put(CityTable.COLUMN_CITY_NAME, item.city_name.toLowerCase().trim());
                Log.d("download", item.city_name.toLowerCase().trim());

                context.getContentResolver().insert(MainActivity.CITY_ENTRIES_URI, values);
            }
        }

    }


    private List loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {
        InputStream stream = null;
        // Instantiate the parser
        CityXMLparser xmlParser = new CityXMLparser();
        List<CityItem> entries = null;

        try {
            stream = downloadUrl(urlString);
            entries = xmlParser.parse(stream);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }

        return entries;
    }


    private InputStream downloadUrl(String urlString) throws IOException {

//        URL url = new URL(urlString);

        //HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        ///conn.setReadTimeout(10000 /* milliseconds */);
        //conn.setConnectTimeout(15000 /* milliseconds */);
        //conn.setRequestMethod("GET");
        //conn.setDoInput(true);

        // Starts the query
       // conn.connect();
        //Log.d("ConnectionCity", conn.getResponseMessage());
//        return conn.getInputStream();
        return context.getResources().openRawResource(R.raw.cities);
        // почему то скачивался html файл при http соединении, и вообще не подключался при https
    }
}