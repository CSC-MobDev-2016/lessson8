package com.csc.shmakov.weatherapp.api.forecast;

import com.csc.shmakov.weatherapp.model.entities.Forecast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Pavel on 4/23/2016.
 */
public class ForecastParser {
    public Forecast.Data parse(String input) {
        Forecast.Weather current = null;
        List<Forecast.DayForecast> forecasts = new ArrayList<>();
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(input.getBytes("utf-8")));
            Element root = doc.getDocumentElement();
            NodeList forecastNodes = root.getChildNodes();

            for (int i = 0; i < forecastNodes.getLength(); i++) {
                Node node = forecastNodes.item(i);
                if (node.getNodeName().equals("fact")) {
                    current = parseWeather(node);
                } else if (node.getNodeName().equals("day")) {
                    String date = getAttr(node, "date");
                    NodeList dayNodes = node.getChildNodes();
                    for (int j = 0; j < dayNodes.getLength(); j++) {
                        Node dayNode = dayNodes.item(j);
                        if (dayNode.getNodeName().equals("day_part") &&
                                getAttr(dayNode, "type").equals("day_short")) {
                            forecasts.add(new Forecast.DayForecast(
                                    date,
                                    parseWeather(dayNode)));
                        }
                    }

                }
            }
            return new Forecast.Data(current, forecasts);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Forecast.Weather parseWeather(Node node) {
        String weatherType = "";
        int temperature = 0;
        NodeList forecastNodes = node.getChildNodes();
        for (int i = 0; i < forecastNodes.getLength(); i++) {
            Node child = forecastNodes.item(i);
            if (child.getNodeName().equals("weather_type")) {
                weatherType = child.getTextContent();
            } else if (child.getNodeName().equals("temperature")) {
                temperature = Integer.parseInt(child.getTextContent());
            }
        }
        return new Forecast.Weather(weatherType, temperature);
    }

    private String getAttr(Node node, String attrName) {
        return node.getAttributes().getNamedItem(attrName).getTextContent();
    }
}
