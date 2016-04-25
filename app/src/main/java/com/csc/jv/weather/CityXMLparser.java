package com.csc.jv.weather;


import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CityXMLparser {

    private static final String START_TAG = "cities";
    private static final String ENTRY = "country";
    private static final String CITY = "city";
    private static final String ID = "id";


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


    private String getCityId(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, CITY);

        return parser.getAttributeValue(null, ID);
    }

    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
// to their respective "read" methods for processing. Otherwise, skips the tag.
    private CityItem readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {

        parser.require(XmlPullParser.START_TAG, ns, ENTRY);

        String city_name = null;
        String city_id = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String entryName = parser.getName();
            switch (entryName) {
                case CITY:
                    city_id = getCityId(parser);
                    city_name = readText(parser);
                    break;
                default:
                    skip(parser);
                    break;
            }
        }
        return new CityItem(city_id, city_name);
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
