package com.csc.shmakov.weatherapp.tests;

import com.csc.shmakov.weatherapp.api.cities.CityParser;
import com.csc.shmakov.weatherapp.model.entities.City;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

/**
 * Created by Pavel on 4/23/2016.
 */

@RunWith(JUnit4.class)
public class CityParserTest {
//        extends ApplicationTestCase<Application> {
    private static final String TYPICAL_RESPONSE =
            "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<cities>\n" +
            "    <country name=\"Абхазия\">\n" +
            "        <city id=\"37188\" region=\"27028\" head=\"\" type=\"3\" country=\"Абхазия\" part=\"\" resort=\"\" climate=\"\">Новый Афон</city>\n" +
            "        <city id=\"37178\" region=\"10282\" head=\"\" type=\"3\" country=\"Абхазия\" part=\"\" resort=\"\" climate=\"\">Пицунда</city>\n" +
            "        <city id=\"37187\" region=\"37187\" head=\"\" type=\"3\" country=\"Абхазия\" part=\"\" resort=\"\" climate=\"\">Гудаута</city>\n" +
            "        <city id=\"37172\" region=\"10280\" head=\"\" type=\"3\" country=\"Абхазия\" part=\"\" resort=\"\" climate=\"\">Гагра</city>\n" +
            "        <city id=\"37189\" region=\"10281\" head=\"0\" type=\"3\" country=\"Абхазия\" part=\"\" resort=\"0\" climate=\"\">Сухум</city>\n" +
            "    </country>\n" +
            "    <country name=\"Австралия\">\n" +
            "        <city id=\"94610\" region=\"21770\" head=\"0\" type=\"4\" country=\"Австралия\" part=\"\" resort=\"0\" climate=\"\">Перт</city>\n" +
            "        <city id=\"94774\" region=\"94774\" head=\"\" type=\"4\" country=\"Австралия\" part=\"\" resort=\"\" climate=\"\">Ньюкасл</city>\n" +
            "        <city id=\"94120\" region=\"21456\" head=\"0\" type=\"4\" country=\"Австралия\" part=\"\" resort=\"0\" climate=\"\">Дарвин</city>\n" +
            "        <city id=\"94866\" region=\"21265\" head=\"0\" type=\"4\" country=\"Австралия\" part=\"\" resort=\"0\" climate=\"\">Мельбурн</city>\n" +
            "        <city id=\"94926\" region=\"211\" head=\"0\" type=\"4\" country=\"Австралия\" part=\"\" resort=\"1\" climate=\"\">Канберра</city>\n" +
            "        <city id=\"94287\" region=\"94287\" head=\"\" type=\"4\" country=\"Австралия\" part=\"Квинсленд\" resort=\"\" climate=\"\">Кэрнс</city>\n" +
            "        <city id=\"94768\" region=\"10145\" head=\"0\" type=\"4\" country=\"Австралия\" part=\"\" resort=\"0\" climate=\"\">Сидней</city>\n" +
            "        <city id=\"94578\" region=\"21210\" head=\"0\" type=\"4\" country=\"Австралия\" part=\"\" resort=\"0\" climate=\"\">Брисбен</city>\n" +
            "        <city id=\"94675\" region=\"20774\" head=\"0\" type=\"4\" country=\"Австралия\" part=\"\" resort=\"0\" climate=\"\">Аделаида</city>\n" +
            "        <city id=\"94326\" region=\"20839\" head=\"0\" type=\"4\" country=\"Австралия\" part=\"\" resort=\"0\" climate=\"\">Алис-Спрингс</city>\n" +
            "        <city id=\"94294\" region=\"10146\" head=\"0\" type=\"4\" country=\"Австралия\" part=\"\" resort=\"1\" climate=\"\">Таунсвилл</city>\n" +
            "        <city id=\"94975\" region=\"20914\" head=\"0\" type=\"4\" country=\"Австралия\" part=\"\" resort=\"0\" climate=\"\">Хобарт</city>\n" +
            "    </country>\n" +
            "</cities>";



//    public CityParserTest() {
//        super(Application.class);
//    }

    @Test
    public void testParser() throws Exception {
        List<City> cities = new CityParser().parse(TYPICAL_RESPONSE);
        Assert.assertEquals(17, cities.size());
        Assert.assertEquals("37178", cities.get(1).apiId);
        Assert.assertEquals("Пицунда", cities.get(1).name);
        Assert.assertEquals("94120", cities.get(7).apiId);
        Assert.assertEquals("Дарвин", cities.get(7).name);
    }
}
