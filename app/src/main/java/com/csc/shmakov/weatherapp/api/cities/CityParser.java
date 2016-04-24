package com.csc.shmakov.weatherapp.api.cities;

import com.csc.shmakov.weatherapp.model.entities.City;

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
public class CityParser {
    public List<City> parse(String input) {
        List<City> cities = new ArrayList<>();
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(input.getBytes("utf-8")));
            Element root = doc.getDocumentElement();
            NodeList countryNodes = root.getElementsByTagName("country");
            for (int i = 0; i < countryNodes.getLength(); i++) {
                Node countryNode = countryNodes.item(i);
                NodeList cityNodes = countryNode.getChildNodes();
                for (int j = 0; j < cityNodes.getLength(); j++) {
                    Node cityNode = cityNodes.item(j);
                    if (!cityNode.getNodeName().equals("city")) {
                        continue;
                    }
                    String name = cityNode.getTextContent();
                    String id = cityNode.getAttributes().getNamedItem("id").getTextContent();
                    cities.add(new City(name, id));
                }
            }
            return cities;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
