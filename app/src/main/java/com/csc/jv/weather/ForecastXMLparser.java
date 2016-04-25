package com.csc.jv.weather;


import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ForecastXMLparser {

    private static final String START_TAG = "forecast";
    private static final String ENTRY = "fact";
    private static final String UPDATE_TIME = "observation_time";
    private static final String TEMPERATURE = "temperature";
    private static final String WEATHER_TYPE = "weather_type";
    private static final String WIND_DIRECTION = "wind_direction";
    private static final String WIND_SPEED = "wind_speed";
    private static final String HUMIDITY = "humidity";
    private static final String PRESSURE = "pressure";
    private static final String MSLP_PRESSURE = "mslp_pressure";
    private static final String DAYTIME = "daytime";
    private static final String WATER_TEMPERATURE = "water_temperature";
    private static final String CITY = "city";
    private static final String ID = "id";

    private String city_name;
    private String city_id;


    // We don't use namespaces
    private static final String ns = null;

    public List parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List entries = new ArrayList();


        parser.require(XmlPullParser.START_TAG, ns, START_TAG);

        city_name = getCityName(parser);
        city_id = getCityId(parser);
        
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals(ENTRY)) {
                entries.add(readEntry(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    private String getCityName(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, START_TAG);

        String cityName = parser.getAttributeValue(null, CITY);

        return cityName;
    }

    private String getCityId(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, START_TAG);

        String cityId = parser.getAttributeValue(null, ID);

        return cityId;
    }

    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
// to their respective "read" methods for processing. Otherwise, skips the tag.
    private ForecastItem readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {

        parser.require(XmlPullParser.START_TAG, ns, ENTRY);

        String update_time = null;
        String temperature = null;
        String weather_type = null;
        String wind_direction = null;
        String wind_speed = null;
        String humidity = null;
        String pressure = null;
        String mslp_pressure = null;
        String daytime = null;
        String water_temperature = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String entryName = parser.getName();
            switch (entryName) {
                case UPDATE_TIME:
                    update_time = readText(parser);
                    break;
                case TEMPERATURE:
                    temperature = readText(parser);
                    break;
                case WEATHER_TYPE:
                    weather_type = readText(parser);
                    break;
                case WIND_DIRECTION:
                    wind_direction = readText(parser);
                    break;
                case WIND_SPEED:
                    wind_speed = readText(parser);
                    break;
                case HUMIDITY:
                    humidity = readText(parser);
                    break;
                case PRESSURE:
                    pressure = readText(parser);
                    break;
                case MSLP_PRESSURE:
                    mslp_pressure = readText(parser);
                    break;
                case DAYTIME:
                    daytime = readText(parser);
                    break;
                case WATER_TEMPERATURE:
                    water_temperature = readText(parser);
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        return new ForecastItem(city_id, city_name, update_time, temperature, weather_type,
                wind_direction, wind_speed, humidity, pressure,
                mslp_pressure, daytime, water_temperature);
    }


    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
