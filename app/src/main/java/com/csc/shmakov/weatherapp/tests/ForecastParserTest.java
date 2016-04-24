package com.csc.shmakov.weatherapp.tests;

import com.csc.shmakov.weatherapp.api.cities.CityParser;
import com.csc.shmakov.weatherapp.api.forecast.ForecastParser;
import com.csc.shmakov.weatherapp.model.entities.City;
import com.csc.shmakov.weatherapp.model.entities.Forecast;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Pavel on 4/23/2016.
 */

@RunWith(JUnit4.class)
public class ForecastParserTest {
//        extends ApplicationTestCase<Application> {




//    public CityParserTest() {
//        super(Application.class);
//    }

    @Test
    public void testParser() throws Exception {
        Forecast.Data data = new ForecastParser().parse(TYPICAL_RESPONSE);
        assertEquals(16, data.current.temperature);
        assertEquals("малооблачно", data.current.description);

        Forecast.DayForecast dayForecast = data.dayForecasts.get(2);
        assertEquals("2016-04-25", dayForecast.date);
        assertEquals(15, dayForecast.weather.temperature);
        assertEquals("пасмурно", dayForecast.weather.description);
    }


    private static final String TYPICAL_RESPONSE =
            "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<forecast city=\"Кемерово\" xmlns=\"http://weather.yandex.ru/forecast\" country=\"Россия\" region=\"64\" lon=\"86.087314\" zoom=\"12\" id=\"29642\" source=\"Station\" country_id=\"1ed1c408a4ac822b728a2077a1d9935c\" link=\"http://pogoda.yandex.ru/kemerovo/\" part_id=\"5f1a76eb94a6e7651cd0d4296d843d40\" geoid=\"64\" lat=\"55.354968\" exactname=\"Кемерово\" part=\"Кемеровская область\" slug=\"kemerovo\">\n" +
            "  <fact>\n" +
            "    <station lang=\"ru\" distance=\"11\">Кемерово</station>\n" +
            "    <station lang=\"en\" distance=\"11\">Kemerovo Airport</station>\n" +
            "    <observation_time>2016-04-23T21:00:00</observation_time>\n" +
            "    <uptime>2016-04-23T21:28:32</uptime>\n" +
            "    <temperature color=\"F6F3D6\" plate=\"fdf392\">16</temperature>\n" +
            "    <weather_condition code=\"partly-cloudy\"/>\n" +
            "    <image type=\"1\">n6</image>\n" +
            "    <image-v2 color=\"b7a838\" type=\"colored\">bkn_n_+16</image-v2>\n" +
            "    <image-v3 type=\"mono\">bkn_n</image-v3>\n" +
            "    <weather_type>малооблачно</weather_type>\n" +
            "    <weather_type_tt>аз болытлы</weather_type_tt>\n" +
            "    <weather_type_tr>az bulutlu</weather_type_tr>\n" +
            "    <weather_type_kz>аздаған бұлт</weather_type_kz>\n" +
            "    <weather_type_ua>малохмарно</weather_type_ua>\n" +
            "    <weather_type_by>малавоблачна</weather_type_by>\n" +
            "    <wind_direction>ne</wind_direction>\n" +
            "    <wind_speed>3.0</wind_speed>\n" +
            "    <humidity>42</humidity>\n" +
            "    <pressure units=\"torr\">736</pressure>\n" +
            "    <mslp_pressure units=\"hPa\">981</mslp_pressure>\n" +
            "    <daytime>n</daytime>\n" +
            "    <season type=\"calendar\">spring</season>\n" +
            "    <ipad_image>1-ночь-лето</ipad_image>\n" +
            "  </fact>\n" +
            "  <yesterday id=\"437301289\">\n" +
            "    <station lang=\"ru\" distance=\"11\">Кемерово</station>\n" +
            "    <station lang=\"en\" distance=\"11\">Kemerovo Airport</station>\n" +
            "    <observation_time>2016-04-22T21:00:00</observation_time>\n" +
            "    <uptime>2016-04-22T21:27:00</uptime>\n" +
            "    <temperature color=\"F7F3D3\" plate=\"fff28e\">17</temperature>\n" +
            "    <weather_condition code=\"cloudy\"/>\n" +
            "    <image type=\"1\">6</image>\n" +
            "    <image-v2 color=\"c3ab2c\" type=\"colored\">bkn_d_+18</image-v2>\n" +
            "    <image-v3 type=\"mono\">bkn_d</image-v3>\n" +
            "    <weather_type>облачно с прояснениями</weather_type>\n" +
            "    <weather_type_tt>аязучан болытлы</weather_type_tt>\n" +
            "    <weather_type_tr>parçalı bulutlu</weather_type_tr>\n" +
            "    <weather_type_kz>ауыспалы бұлтты</weather_type_kz>\n" +
            "    <weather_type_ua>хмарно із проясненнями</weather_type_ua>\n" +
            "    <weather_type_by>воблачна з праясненнямі</weather_type_by>\n" +
            "    <wind_direction>e</wind_direction>\n" +
            "    <wind_speed>4.0</wind_speed>\n" +
            "    <humidity>55</humidity>\n" +
            "    <pressure units=\"torr\">734</pressure>\n" +
            "    <mslp_pressure units=\"hPa\">979</mslp_pressure>\n" +
            "    <season type=\"calendar\">spring</season>\n" +
            "    <ipad_image>1-ночь-лето</ipad_image>\n" +
            "  </yesterday>\n" +
            "  <informer>\n" +
            "    <temperature color=\"F3F1E3\" type=\"night\">8</temperature>\n" +
            "    <temperature color=\"F7F3D3\" type=\"tomorrow\">18</temperature>\n" +
            "  </informer>\n" +
            "  <day date=\"2016-04-23\">\n" +
            "    <sunrise>05:51</sunrise>\n" +
            "    <sunset>20:37</sunset>\n" +
            "    <moon_phase code=\"full-moon\">0</moon_phase>\n" +
            "    <moonrise>21:39</moonrise>\n" +
            "    <moonset>06:41</moonset>\n" +
            "    <biomet index=\"2\" geomag=\"2\" uv=\"1\">\n" +
            "      <message code=\"meteosens-likely\"/>\n" +
            "      <message code=\"solar-minor\"/>\n" +
            "      <message code=\"uv-small\"/>\n" +
            "    </biomet>\n" +
            "    <day_part typeid=\"1\" type=\"morning\">\n" +
            "      <temperature_from>9</temperature_from>\n" +
            "      <temperature_to>15</temperature_to>\n" +
            "      <temperature-data>\n" +
            "        <avg bgcolor=\"F5F2DC\">11</avg>\n" +
            "        <from>9</from>\n" +
            "        <to>15</to>\n" +
            "      </temperature-data>\n" +
            "      <weather_condition code=\"cloudy\"/>\n" +
            "      <image type=\"1\">6</image>\n" +
            "      <image-v2 color=\"ad9745\" type=\"colored\">bkn_d_+12</image-v2>\n" +
            "      <image-v3 type=\"mono\">bkn_d</image-v3>\n" +
            "      <weather_type>облачно с прояснениями</weather_type>\n" +
            "      <weather_type_tt>аязучан болытлы</weather_type_tt>\n" +
            "      <weather_type_tr>parçalı bulutlu</weather_type_tr>\n" +
            "      <weather_type_kz>ауыспалы бұлтты</weather_type_kz>\n" +
            "      <weather_type_ua>хмарно із проясненнями</weather_type_ua>\n" +
            "      <weather_type_by>воблачна з праясненнямі</weather_type_by>\n" +
            "      <wind_direction>e</wind_direction>\n" +
            "      <wind_speed>2.1</wind_speed>\n" +
            "      <humidity>78</humidity>\n" +
            "      <pressure units=\"torr\">747</pressure>\n" +
            "      <mslp_pressure units=\"hPa\">996</mslp_pressure>\n" +
            "    </day_part>\n" +
            "    <day_part typeid=\"2\" type=\"day\">\n" +
            "      <temperature_from>16</temperature_from>\n" +
            "      <temperature_to>20</temperature_to>\n" +
            "      <temperature-data>\n" +
            "        <avg bgcolor=\"F8F4D0\">19</avg>\n" +
            "        <from>16</from>\n" +
            "        <to>20</to>\n" +
            "      </temperature-data>\n" +
            "      <weather_condition code=\"partly-cloudy\"/>\n" +
            "      <image type=\"1\">6</image>\n" +
            "      <image-v2 color=\"cab123\" type=\"colored\">bkn_d_+20</image-v2>\n" +
            "      <image-v3 type=\"mono\">bkn_d</image-v3>\n" +
            "      <weather_type>малооблачно</weather_type>\n" +
            "      <weather_type_tt>аз болытлы</weather_type_tt>\n" +
            "      <weather_type_tr>az bulutlu</weather_type_tr>\n" +
            "      <weather_type_kz>аздаған бұлт</weather_type_kz>\n" +
            "      <weather_type_ua>малохмарно</weather_type_ua>\n" +
            "      <weather_type_by>малавоблачна</weather_type_by>\n" +
            "      <wind_direction>ne</wind_direction>\n" +
            "      <wind_speed>3.7</wind_speed>\n" +
            "      <humidity>43</humidity>\n" +
            "      <pressure units=\"torr\">747</pressure>\n" +
            "      <mslp_pressure units=\"hPa\">996</mslp_pressure>\n" +
            "    </day_part>\n" +
            "    <day_part typeid=\"3\" type=\"evening\">\n" +
            "      <temperature_from>13</temperature_from>\n" +
            "      <temperature_to>20</temperature_to>\n" +
            "      <temperature-data>\n" +
            "        <avg bgcolor=\"F7F3D3\">17</avg>\n" +
            "        <from>13</from>\n" +
            "        <to>20</to>\n" +
            "      </temperature-data>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">n5</image>\n" +
            "      <image-v2 color=\"c3ab2c\" type=\"colored\">ovc_+18</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "      <weather_type>пасмурно</weather_type>\n" +
            "      <weather_type_tt>болытлы</weather_type_tt>\n" +
            "      <weather_type_tr>bulutlu</weather_type_tr>\n" +
            "      <weather_type_kz>бұлыңғыр</weather_type_kz>\n" +
            "      <weather_type_ua>хмарно</weather_type_ua>\n" +
            "      <weather_type_by>пахмурна</weather_type_by>\n" +
            "      <wind_direction>ne</wind_direction>\n" +
            "      <wind_speed>3.6</wind_speed>\n" +
            "      <humidity>37</humidity>\n" +
            "      <pressure units=\"torr\">748</pressure>\n" +
            "      <mslp_pressure units=\"hPa\">997</mslp_pressure>\n" +
            "    </day_part>\n" +
            "    <day_part typeid=\"4\" type=\"night\">\n" +
            "      <temperature_from>8</temperature_from>\n" +
            "      <temperature_to>12</temperature_to>\n" +
            "      <temperature-data>\n" +
            "        <avg bgcolor=\"F4F1E0\">10</avg>\n" +
            "        <from>8</from>\n" +
            "        <to>12</to>\n" +
            "      </temperature-data>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">n5</image>\n" +
            "      <image-v2 color=\"a29652\" type=\"colored\">ovc_+10</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "      <weather_type>пасмурно</weather_type>\n" +
            "      <weather_type_tt>болытлы</weather_type_tt>\n" +
            "      <weather_type_tr>bulutlu</weather_type_tr>\n" +
            "      <weather_type_kz>бұлыңғыр</weather_type_kz>\n" +
            "      <weather_type_ua>хмарно</weather_type_ua>\n" +
            "      <weather_type_by>пахмурна</weather_type_by>\n" +
            "      <wind_direction>e</wind_direction>\n" +
            "      <wind_speed>2.7</wind_speed>\n" +
            "      <humidity>52</humidity>\n" +
            "      <pressure units=\"torr\">749</pressure>\n" +
            "      <mslp_pressure units=\"hPa\">999</mslp_pressure>\n" +
            "    </day_part>\n" +
            "    <day_part typeid=\"5\" type=\"day_short\">\n" +
            "      <temperature>20</temperature>\n" +
            "      <temperature-data>\n" +
            "        <avg bgcolor=\"F8F4D0\">20</avg>\n" +
            "      </temperature-data>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">5</image>\n" +
            "      <image-v2 color=\"cab123\" type=\"colored\">ovc_+20</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "      <weather_type>пасмурно</weather_type>\n" +
            "      <weather_type_tt>болытлы</weather_type_tt>\n" +
            "      <weather_type_tr>bulutlu</weather_type_tr>\n" +
            "      <weather_type_kz>бұлыңғыр</weather_type_kz>\n" +
            "      <weather_type_ua>хмарно</weather_type_ua>\n" +
            "      <weather_type_by>пахмурна</weather_type_by>\n" +
            "      <wind_direction>ne</wind_direction>\n" +
            "      <wind_speed>3.7</wind_speed>\n" +
            "      <humidity>43</humidity>\n" +
            "      <pressure units=\"torr\">747</pressure>\n" +
            "      <mslp_pressure units=\"hPa\">996</mslp_pressure>\n" +
            "    </day_part>\n" +
            "    <day_part typeid=\"6\" type=\"night_short\">\n" +
            "      <temperature>8</temperature>\n" +
            "      <temperature-data>\n" +
            "        <avg bgcolor=\"F3F1E3\">8</avg>\n" +
            "      </temperature-data>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">n5</image>\n" +
            "      <image-v2 color=\"9e9256\" type=\"colored\">ovc_+8</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "      <weather_type>пасмурно</weather_type>\n" +
            "      <weather_type_tt>болытлы</weather_type_tt>\n" +
            "      <weather_type_tr>bulutlu</weather_type_tr>\n" +
            "      <weather_type_kz>бұлыңғыр</weather_type_kz>\n" +
            "      <weather_type_ua>хмарно</weather_type_ua>\n" +
            "      <weather_type_by>пахмурна</weather_type_by>\n" +
            "      <wind_direction>e</wind_direction>\n" +
            "      <wind_speed>2.7</wind_speed>\n" +
            "      <humidity>52</humidity>\n" +
            "      <pressure units=\"torr\">749</pressure>\n" +
            "      <mslp_pressure units=\"hPa\">999</mslp_pressure>\n" +
            "    </day_part>\n" +
            "    <hour at=\"0\">\n" +
            "      <temperature>13</temperature>\n" +
            "      <weather_condition code=\"partly-cloudy\"/>\n" +
            "      <image type=\"1\">n6</image>\n" +
            "      <image-v2 color=\"b49d3c\" type=\"colored\">bkn_n_+14</image-v2>\n" +
            "      <image-v3 type=\"mono\">bkn_n</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"1\">\n" +
            "      <temperature>12</temperature>\n" +
            "      <weather_condition code=\"partly-cloudy\"/>\n" +
            "      <image type=\"1\">n6</image>\n" +
            "      <image-v2 color=\"ad9745\" type=\"colored\">bkn_n_+12</image-v2>\n" +
            "      <image-v3 type=\"mono\">bkn_n</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"2\">\n" +
            "      <temperature>12</temperature>\n" +
            "      <weather_condition code=\"cloudy\"/>\n" +
            "      <image type=\"1\">n6</image>\n" +
            "      <image-v2 color=\"ad9745\" type=\"colored\">bkn_n_+12</image-v2>\n" +
            "      <image-v3 type=\"mono\">bkn_n</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"3\">\n" +
            "      <temperature>11</temperature>\n" +
            "      <weather_condition code=\"cloudy\"/>\n" +
            "      <image type=\"1\">n6</image>\n" +
            "      <image-v2 color=\"ad9745\" type=\"colored\">bkn_n_+12</image-v2>\n" +
            "      <image-v3 type=\"mono\">bkn_n</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"4\">\n" +
            "      <temperature>11</temperature>\n" +
            "      <weather_condition code=\"cloudy\"/>\n" +
            "      <image type=\"1\">n6</image>\n" +
            "      <image-v2 color=\"ad9745\" type=\"colored\">bkn_n_+12</image-v2>\n" +
            "      <image-v3 type=\"mono\">bkn_n</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"5\">\n" +
            "      <temperature>10</temperature>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">n5</image>\n" +
            "      <image-v2 color=\"a29652\" type=\"colored\">ovc_+10</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"6\">\n" +
            "      <temperature>10</temperature>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">5</image>\n" +
            "      <image-v2 color=\"a29652\" type=\"colored\">ovc_+10</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"7\">\n" +
            "      <temperature>9</temperature>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">5</image>\n" +
            "      <image-v2 color=\"a29652\" type=\"colored\">ovc_+10</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"8\">\n" +
            "      <temperature>9</temperature>\n" +
            "      <weather_condition code=\"partly-cloudy\"/>\n" +
            "      <image type=\"1\">6</image>\n" +
            "      <image-v2 color=\"a29652\" type=\"colored\">bkn_d_+10</image-v2>\n" +
            "      <image-v3 type=\"mono\">bkn_d</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"9\">\n" +
            "      <temperature>11</temperature>\n" +
            "      <weather_condition code=\"partly-cloudy\"/>\n" +
            "      <image type=\"1\">6</image>\n" +
            "      <image-v2 color=\"ad9745\" type=\"colored\">bkn_d_+12</image-v2>\n" +
            "      <image-v3 type=\"mono\">bkn_d</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"10\">\n" +
            "      <temperature>14</temperature>\n" +
            "      <weather_condition code=\"partly-cloudy\"/>\n" +
            "      <image type=\"1\">6</image>\n" +
            "      <image-v2 color=\"b49d3c\" type=\"colored\">bkn_d_+14</image-v2>\n" +
            "      <image-v3 type=\"mono\">bkn_d</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"11\">\n" +
            "      <temperature>15</temperature>\n" +
            "      <weather_condition code=\"partly-cloudy\"/>\n" +
            "      <image type=\"1\">6</image>\n" +
            "      <image-v2 color=\"b7a838\" type=\"colored\">bkn_d_+16</image-v2>\n" +
            "      <image-v3 type=\"mono\">bkn_d</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"12\">\n" +
            "      <temperature>16</temperature>\n" +
            "      <weather_condition code=\"partly-cloudy\"/>\n" +
            "      <image type=\"1\">6</image>\n" +
            "      <image-v2 color=\"b7a838\" type=\"colored\">bkn_d_+16</image-v2>\n" +
            "      <image-v3 type=\"mono\">bkn_d</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"13\">\n" +
            "      <temperature>19</temperature>\n" +
            "      <weather_condition code=\"partly-cloudy\"/>\n" +
            "      <image type=\"1\">6</image>\n" +
            "      <image-v2 color=\"cab123\" type=\"colored\">bkn_d_+20</image-v2>\n" +
            "      <image-v3 type=\"mono\">bkn_d</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"14\">\n" +
            "      <temperature>19</temperature>\n" +
            "      <weather_condition code=\"partly-cloudy\"/>\n" +
            "      <image type=\"1\">6</image>\n" +
            "      <image-v2 color=\"cab123\" type=\"colored\">bkn_d_+20</image-v2>\n" +
            "      <image-v3 type=\"mono\">bkn_d</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"15\">\n" +
            "      <temperature>20</temperature>\n" +
            "      <weather_condition code=\"partly-cloudy\"/>\n" +
            "      <image type=\"1\">6</image>\n" +
            "      <image-v2 color=\"cab123\" type=\"colored\">bkn_d_+20</image-v2>\n" +
            "      <image-v3 type=\"mono\">bkn_d</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"16\">\n" +
            "      <temperature>20</temperature>\n" +
            "      <weather_condition code=\"partly-cloudy\"/>\n" +
            "      <image type=\"1\">6</image>\n" +
            "      <image-v2 color=\"cab123\" type=\"colored\">bkn_d_+20</image-v2>\n" +
            "      <image-v3 type=\"mono\">bkn_d</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"17\">\n" +
            "      <temperature>20</temperature>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">5</image>\n" +
            "      <image-v2 color=\"cab123\" type=\"colored\">ovc_+20</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"18\">\n" +
            "      <temperature>20</temperature>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">5</image>\n" +
            "      <image-v2 color=\"cab123\" type=\"colored\">ovc_+20</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"19\">\n" +
            "      <temperature>20</temperature>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">5</image>\n" +
            "      <image-v2 color=\"cab123\" type=\"colored\">ovc_+20</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"20\">\n" +
            "      <temperature>18</temperature>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">5</image>\n" +
            "      <image-v2 color=\"c3ab2c\" type=\"colored\">ovc_+18</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"21\">\n" +
            "      <temperature>16</temperature>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">n5</image>\n" +
            "      <image-v2 color=\"b7a838\" type=\"colored\">ovc_+16</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"22\">\n" +
            "      <temperature>15</temperature>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">n5</image>\n" +
            "      <image-v2 color=\"b7a838\" type=\"colored\">ovc_+16</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"23\">\n" +
            "      <temperature>13</temperature>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">n5</image>\n" +
            "      <image-v2 color=\"b49d3c\" type=\"colored\">ovc_+14</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "    </hour>\n" +
            "  </day>\n" +
            "  <day date=\"2016-04-24\">\n" +
            "    <sunrise>05:49</sunrise>\n" +
            "    <sunset>20:39</sunset>\n" +
            "    <moon_phase code=\"decreasing-moon\">1</moon_phase>\n" +
            "    <moonrise>22:41</moonrise>\n" +
            "    <moonset>07:08</moonset>\n" +
            "    <biomet index=\"2\" geomag=\"2\" uv=\"1\">\n" +
            "      <message code=\"meteosens-likely\"/>\n" +
            "      <message code=\"solar-minor\"/>\n" +
            "      <message code=\"uv-small\"/>\n" +
            "    </biomet>\n" +
            "    <day_part typeid=\"1\" type=\"morning\">\n" +
            "      <temperature_from>7</temperature_from>\n" +
            "      <temperature_to>14</temperature_to>\n" +
            "      <temperature-data>\n" +
            "        <avg bgcolor=\"F4F1E0\">10</avg>\n" +
            "        <from>7</from>\n" +
            "        <to>14</to>\n" +
            "      </temperature-data>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">5</image>\n" +
            "      <image-v2 color=\"a29652\" type=\"colored\">ovc_+10</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "      <weather_type>пасмурно</weather_type>\n" +
            "      <weather_type_tt>болытлы</weather_type_tt>\n" +
            "      <weather_type_tr>bulutlu</weather_type_tr>\n" +
            "      <weather_type_kz>бұлыңғыр</weather_type_kz>\n" +
            "      <weather_type_ua>хмарно</weather_type_ua>\n" +
            "      <weather_type_by>пахмурна</weather_type_by>\n" +
            "      <wind_direction>e</wind_direction>\n" +
            "      <wind_speed>2.1</wind_speed>\n" +
            "      <humidity>52</humidity>\n" +
            "      <pressure units=\"torr\">750</pressure>\n" +
            "      <mslp_pressure units=\"hPa\">1000</mslp_pressure>\n" +
            "    </day_part>\n" +
            "    <day_part typeid=\"2\" type=\"day\">\n" +
            "      <temperature_from>15</temperature_from>\n" +
            "      <temperature_to>18</temperature_to>\n" +
            "      <temperature-data>\n" +
            "        <avg bgcolor=\"F7F3D3\">17</avg>\n" +
            "        <from>15</from>\n" +
            "        <to>18</to>\n" +
            "      </temperature-data>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">5</image>\n" +
            "      <image-v2 color=\"c3ab2c\" type=\"colored\">ovc_+18</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "      <weather_type>пасмурно</weather_type>\n" +
            "      <weather_type_tt>болытлы</weather_type_tt>\n" +
            "      <weather_type_tr>bulutlu</weather_type_tr>\n" +
            "      <weather_type_kz>бұлыңғыр</weather_type_kz>\n" +
            "      <weather_type_ua>хмарно</weather_type_ua>\n" +
            "      <weather_type_by>пахмурна</weather_type_by>\n" +
            "      <wind_direction>se</wind_direction>\n" +
            "      <wind_speed>1.9</wind_speed>\n" +
            "      <humidity>34</humidity>\n" +
            "      <pressure units=\"torr\">750</pressure>\n" +
            "      <mslp_pressure units=\"hPa\">1000</mslp_pressure>\n" +
            "    </day_part>\n" +
            "    <day_part typeid=\"3\" type=\"evening\">\n" +
            "      <temperature_from>11</temperature_from>\n" +
            "      <temperature_to>17</temperature_to>\n" +
            "      <temperature-data>\n" +
            "        <avg bgcolor=\"F5F2D9\">14</avg>\n" +
            "        <from>11</from>\n" +
            "        <to>17</to>\n" +
            "      </temperature-data>\n" +
            "      <weather_condition code=\"cloudy\"/>\n" +
            "      <image type=\"1\">n6</image>\n" +
            "      <image-v2 color=\"b49d3c\" type=\"colored\">bkn_n_+14</image-v2>\n" +
            "      <image-v3 type=\"mono\">bkn_n</image-v3>\n" +
            "      <weather_type>облачно с прояснениями</weather_type>\n" +
            "      <weather_type_tt>аязучан болытлы</weather_type_tt>\n" +
            "      <weather_type_tr>parçalı bulutlu</weather_type_tr>\n" +
            "      <weather_type_kz>ауыспалы бұлтты</weather_type_kz>\n" +
            "      <weather_type_ua>хмарно із проясненнями</weather_type_ua>\n" +
            "      <weather_type_by>воблачна з праясненнямі</weather_type_by>\n" +
            "      <wind_direction>e</wind_direction>\n" +
            "      <wind_speed>2.9</wind_speed>\n" +
            "      <humidity>37</humidity>\n" +
            "      <pressure units=\"torr\">750</pressure>\n" +
            "      <mslp_pressure units=\"hPa\">1000</mslp_pressure>\n" +
            "    </day_part>\n" +
            "    <day_part typeid=\"4\" type=\"night\">\n" +
            "      <temperature_from>6</temperature_from>\n" +
            "      <temperature_to>9</temperature_to>\n" +
            "      <temperature-data>\n" +
            "        <avg bgcolor=\"F3F1E3\">7</avg>\n" +
            "        <from>6</from>\n" +
            "        <to>9</to>\n" +
            "      </temperature-data>\n" +
            "      <weather_condition code=\"clear\"/>\n" +
            "      <image type=\"1\">n7</image>\n" +
            "      <image-v2 color=\"9e9256\" type=\"colored\">skc_n_+8</image-v2>\n" +
            "      <image-v3 type=\"mono\">skc_n</image-v3>\n" +
            "      <weather_type>ясно</weather_type>\n" +
            "      <weather_type_tt>аяз</weather_type_tt>\n" +
            "      <weather_type_tr>açık</weather_type_tr>\n" +
            "      <weather_type_kz>ашық</weather_type_kz>\n" +
            "      <weather_type_ua>ясно</weather_type_ua>\n" +
            "      <weather_type_by>ясна</weather_type_by>\n" +
            "      <wind_direction>ne</wind_direction>\n" +
            "      <wind_speed>2.6</wind_speed>\n" +
            "      <humidity>62</humidity>\n" +
            "      <pressure units=\"torr\">750</pressure>\n" +
            "      <mslp_pressure units=\"hPa\">1000</mslp_pressure>\n" +
            "    </day_part>\n" +
            "    <day_part typeid=\"5\" type=\"day_short\">\n" +
            "      <temperature>18</temperature>\n" +
            "      <temperature-data>\n" +
            "        <avg bgcolor=\"F7F3D3\">18</avg>\n" +
            "      </temperature-data>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">5</image>\n" +
            "      <image-v2 color=\"c3ab2c\" type=\"colored\">ovc_+18</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "      <weather_type>пасмурно</weather_type>\n" +
            "      <weather_type_tt>болытлы</weather_type_tt>\n" +
            "      <weather_type_tr>bulutlu</weather_type_tr>\n" +
            "      <weather_type_kz>бұлыңғыр</weather_type_kz>\n" +
            "      <weather_type_ua>хмарно</weather_type_ua>\n" +
            "      <weather_type_by>пахмурна</weather_type_by>\n" +
            "      <wind_direction>se</wind_direction>\n" +
            "      <wind_speed>1.9</wind_speed>\n" +
            "      <humidity>34</humidity>\n" +
            "      <pressure units=\"torr\">750</pressure>\n" +
            "      <mslp_pressure units=\"hPa\">1000</mslp_pressure>\n" +
            "    </day_part>\n" +
            "    <day_part typeid=\"6\" type=\"night_short\">\n" +
            "      <temperature>6</temperature>\n" +
            "      <temperature-data>\n" +
            "        <avg bgcolor=\"F2F0E6\">6</avg>\n" +
            "      </temperature-data>\n" +
            "      <weather_condition code=\"clear\"/>\n" +
            "      <image type=\"1\">n7</image>\n" +
            "      <image-v2 color=\"938863\" type=\"colored\">skc_n_+6</image-v2>\n" +
            "      <image-v3 type=\"mono\">skc_n</image-v3>\n" +
            "      <weather_type>ясно</weather_type>\n" +
            "      <weather_type_tt>аяз</weather_type_tt>\n" +
            "      <weather_type_tr>açık</weather_type_tr>\n" +
            "      <weather_type_kz>ашық</weather_type_kz>\n" +
            "      <weather_type_ua>ясно</weather_type_ua>\n" +
            "      <weather_type_by>ясна</weather_type_by>\n" +
            "      <wind_direction>ne</wind_direction>\n" +
            "      <wind_speed>2.6</wind_speed>\n" +
            "      <humidity>62</humidity>\n" +
            "      <pressure units=\"torr\">750</pressure>\n" +
            "      <mslp_pressure units=\"hPa\">1000</mslp_pressure>\n" +
            "    </day_part>\n" +
            "    <hour at=\"0\">\n" +
            "      <temperature>12</temperature>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">n5</image>\n" +
            "      <image-v2 color=\"ad9745\" type=\"colored\">ovc_+12</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"1\">\n" +
            "      <temperature>11</temperature>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">n5</image>\n" +
            "      <image-v2 color=\"ad9745\" type=\"colored\">ovc_+12</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"2\">\n" +
            "      <temperature>10</temperature>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">n5</image>\n" +
            "      <image-v2 color=\"a29652\" type=\"colored\">ovc_+10</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"3\">\n" +
            "      <temperature>9</temperature>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">n5</image>\n" +
            "      <image-v2 color=\"a29652\" type=\"colored\">ovc_+10</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"4\">\n" +
            "      <temperature>8</temperature>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">n5</image>\n" +
            "      <image-v2 color=\"9e9256\" type=\"colored\">ovc_+8</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"5\">\n" +
            "      <temperature>8</temperature>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">n5</image>\n" +
            "      <image-v2 color=\"9e9256\" type=\"colored\">ovc_+8</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"6\">\n" +
            "      <temperature>8</temperature>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">5</image>\n" +
            "      <image-v2 color=\"9e9256\" type=\"colored\">ovc_+8</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"7\">\n" +
            "      <temperature>7</temperature>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">5</image>\n" +
            "      <image-v2 color=\"9e9256\" type=\"colored\">ovc_+8</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"8\">\n" +
            "      <temperature>9</temperature>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">5</image>\n" +
            "      <image-v2 color=\"a29652\" type=\"colored\">ovc_+10</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"9\">\n" +
            "      <temperature>11</temperature>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">5</image>\n" +
            "      <image-v2 color=\"ad9745\" type=\"colored\">ovc_+12</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"10\">\n" +
            "      <temperature>12</temperature>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">5</image>\n" +
            "      <image-v2 color=\"ad9745\" type=\"colored\">ovc_+12</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"11\">\n" +
            "      <temperature>14</temperature>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">5</image>\n" +
            "      <image-v2 color=\"b49d3c\" type=\"colored\">ovc_+14</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"12\">\n" +
            "      <temperature>15</temperature>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">5</image>\n" +
            "      <image-v2 color=\"b7a838\" type=\"colored\">ovc_+16</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"13\">\n" +
            "      <temperature>16</temperature>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">5</image>\n" +
            "      <image-v2 color=\"b7a838\" type=\"colored\">ovc_+16</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"14\">\n" +
            "      <temperature>17</temperature>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">5</image>\n" +
            "      <image-v2 color=\"c3ab2c\" type=\"colored\">ovc_+18</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"15\">\n" +
            "      <temperature>17</temperature>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">5</image>\n" +
            "      <image-v2 color=\"c3ab2c\" type=\"colored\">ovc_+18</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"16\">\n" +
            "      <temperature>18</temperature>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">5</image>\n" +
            "      <image-v2 color=\"c3ab2c\" type=\"colored\">ovc_+18</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"17\">\n" +
            "      <temperature>18</temperature>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">5</image>\n" +
            "      <image-v2 color=\"c3ab2c\" type=\"colored\">ovc_+18</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"18\">\n" +
            "      <temperature>17</temperature>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">5</image>\n" +
            "      <image-v2 color=\"c3ab2c\" type=\"colored\">ovc_+18</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"19\">\n" +
            "      <temperature>17</temperature>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">5</image>\n" +
            "      <image-v2 color=\"c3ab2c\" type=\"colored\">ovc_+18</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"20\">\n" +
            "      <temperature>15</temperature>\n" +
            "      <weather_condition code=\"cloudy\"/>\n" +
            "      <image type=\"1\">6</image>\n" +
            "      <image-v2 color=\"b7a838\" type=\"colored\">bkn_d_+16</image-v2>\n" +
            "      <image-v3 type=\"mono\">bkn_d</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"21\">\n" +
            "      <temperature>14</temperature>\n" +
            "      <weather_condition code=\"cloudy\"/>\n" +
            "      <image type=\"1\">n6</image>\n" +
            "      <image-v2 color=\"b49d3c\" type=\"colored\">bkn_n_+14</image-v2>\n" +
            "      <image-v3 type=\"mono\">bkn_n</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"22\">\n" +
            "      <temperature>12</temperature>\n" +
            "      <weather_condition code=\"cloudy\"/>\n" +
            "      <image type=\"1\">n6</image>\n" +
            "      <image-v2 color=\"ad9745\" type=\"colored\">bkn_n_+12</image-v2>\n" +
            "      <image-v3 type=\"mono\">bkn_n</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"23\">\n" +
            "      <temperature>11</temperature>\n" +
            "      <weather_condition code=\"clear\"/>\n" +
            "      <image type=\"1\">n7</image>\n" +
            "      <image-v2 color=\"ad9745\" type=\"colored\">skc_n_+12</image-v2>\n" +
            "      <image-v3 type=\"mono\">skc_n</image-v3>\n" +
            "    </hour>\n" +
            "  </day>\n" +
            "  <day date=\"2016-04-25\">\n" +
            "    <sunrise>05:46</sunrise>\n" +
            "    <sunset>20:41</sunset>\n" +
            "    <moon_phase code=\"decreasing-moon\">2</moon_phase>\n" +
            "    <moonrise>23:41</moonrise>\n" +
            "    <moonset>07:39</moonset>\n" +
            "    <day_part typeid=\"1\" type=\"morning\">\n" +
            "      <temperature>6</temperature>\n" +
            "      <temperature-data>\n" +
            "        <avg bgcolor=\"F2F0E6\">6</avg>\n" +
            "      </temperature-data>\n" +
            "      <weather_condition code=\"clear\"/>\n" +
            "      <image type=\"1\">7</image>\n" +
            "      <image-v2 color=\"938863\" type=\"colored\">skc_d_+6</image-v2>\n" +
            "      <image-v3 type=\"mono\">skc_d</image-v3>\n" +
            "      <weather_type>ясно</weather_type>\n" +
            "      <weather_type_tt>аяз</weather_type_tt>\n" +
            "      <weather_type_tr>açık</weather_type_tr>\n" +
            "      <weather_type_kz>ашық</weather_type_kz>\n" +
            "      <weather_type_ua>ясно</weather_type_ua>\n" +
            "      <weather_type_by>ясна</weather_type_by>\n" +
            "      <wind_direction>ne</wind_direction>\n" +
            "      <wind_speed>2.2</wind_speed>\n" +
            "      <humidity>61</humidity>\n" +
            "      <pressure units=\"torr\">749</pressure>\n" +
            "      <mslp_pressure units=\"hPa\">999</mslp_pressure>\n" +
            "    </day_part>\n" +
            "    <day_part typeid=\"2\" type=\"day\">\n" +
            "      <temperature_from>6</temperature_from>\n" +
            "      <temperature_to>15</temperature_to>\n" +
            "      <temperature-data>\n" +
            "        <avg bgcolor=\"F6F3D6\">15</avg>\n" +
            "        <from>6</from>\n" +
            "        <to>15</to>\n" +
            "      </temperature-data>\n" +
            "      <weather_condition code=\"clear\"/>\n" +
            "      <image type=\"1\">7</image>\n" +
            "      <image-v2 color=\"b7a838\" type=\"colored\">skc_d_+16</image-v2>\n" +
            "      <image-v3 type=\"mono\">skc_d</image-v3>\n" +
            "      <weather_type>ясно</weather_type>\n" +
            "      <weather_type_tt>аяз</weather_type_tt>\n" +
            "      <weather_type_tr>açık</weather_type_tr>\n" +
            "      <weather_type_kz>ашық</weather_type_kz>\n" +
            "      <weather_type_ua>ясно</weather_type_ua>\n" +
            "      <weather_type_by>ясна</weather_type_by>\n" +
            "      <wind_direction>n</wind_direction>\n" +
            "      <wind_speed>1.7</wind_speed>\n" +
            "      <humidity>31</humidity>\n" +
            "      <pressure units=\"torr\">748</pressure>\n" +
            "      <mslp_pressure units=\"hPa\">997</mslp_pressure>\n" +
            "    </day_part>\n" +
            "    <day_part typeid=\"3\" type=\"evening\">\n" +
            "      <temperature_from>14</temperature_from>\n" +
            "      <temperature_to>15</temperature_to>\n" +
            "      <temperature-data>\n" +
            "        <avg bgcolor=\"F5F2D9\">14</avg>\n" +
            "        <from>14</from>\n" +
            "        <to>15</to>\n" +
            "      </temperature-data>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">n5</image>\n" +
            "      <image-v2 color=\"b49d3c\" type=\"colored\">ovc_+14</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "      <weather_type>пасмурно</weather_type>\n" +
            "      <weather_type_tt>болытлы</weather_type_tt>\n" +
            "      <weather_type_tr>bulutlu</weather_type_tr>\n" +
            "      <weather_type_kz>бұлыңғыр</weather_type_kz>\n" +
            "      <weather_type_ua>хмарно</weather_type_ua>\n" +
            "      <weather_type_by>пахмурна</weather_type_by>\n" +
            "      <wind_direction>n</wind_direction>\n" +
            "      <wind_speed>2.8</wind_speed>\n" +
            "      <humidity>27</humidity>\n" +
            "      <pressure units=\"torr\">748</pressure>\n" +
            "      <mslp_pressure units=\"hPa\">997</mslp_pressure>\n" +
            "    </day_part>\n" +
            "    <day_part typeid=\"4\" type=\"night\">\n" +
            "      <temperature_from>8</temperature_from>\n" +
            "      <temperature_to>14</temperature_to>\n" +
            "      <temperature-data>\n" +
            "        <avg bgcolor=\"F3F1E3\">8</avg>\n" +
            "        <from>8</from>\n" +
            "        <to>14</to>\n" +
            "      </temperature-data>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">n5</image>\n" +
            "      <image-v2 color=\"9e9256\" type=\"colored\">ovc_+8</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "      <weather_type>пасмурно</weather_type>\n" +
            "      <weather_type_tt>болытлы</weather_type_tt>\n" +
            "      <weather_type_tr>bulutlu</weather_type_tr>\n" +
            "      <weather_type_kz>бұлыңғыр</weather_type_kz>\n" +
            "      <weather_type_ua>хмарно</weather_type_ua>\n" +
            "      <weather_type_by>пахмурна</weather_type_by>\n" +
            "      <wind_direction>nw</wind_direction>\n" +
            "      <wind_speed>1.5</wind_speed>\n" +
            "      <humidity>56</humidity>\n" +
            "      <pressure units=\"torr\">748</pressure>\n" +
            "      <mslp_pressure units=\"hPa\">997</mslp_pressure>\n" +
            "    </day_part>\n" +
            "    <day_part typeid=\"5\" type=\"day_short\">\n" +
            "      <temperature>15</temperature>\n" +
            "      <temperature-data>\n" +
            "        <avg bgcolor=\"F6F3D6\">15</avg>\n" +
            "      </temperature-data>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">5</image>\n" +
            "      <image-v2 color=\"b7a838\" type=\"colored\">ovc_+16</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "      <weather_type>пасмурно</weather_type>\n" +
            "      <weather_type_tt>болытлы</weather_type_tt>\n" +
            "      <weather_type_tr>bulutlu</weather_type_tr>\n" +
            "      <weather_type_kz>бұлыңғыр</weather_type_kz>\n" +
            "      <weather_type_ua>хмарно</weather_type_ua>\n" +
            "      <weather_type_by>пахмурна</weather_type_by>\n" +
            "      <wind_direction>n</wind_direction>\n" +
            "      <wind_speed>1.7</wind_speed>\n" +
            "      <humidity>31</humidity>\n" +
            "      <pressure units=\"torr\">748</pressure>\n" +
            "      <mslp_pressure units=\"hPa\">997</mslp_pressure>\n" +
            "    </day_part>\n" +
            "    <day_part typeid=\"6\" type=\"night_short\">\n" +
            "      <temperature>8</temperature>\n" +
            "      <temperature-data>\n" +
            "        <avg bgcolor=\"F3F1E3\">8</avg>\n" +
            "      </temperature-data>\n" +
            "      <weather_condition code=\"overcast\"/>\n" +
            "      <image type=\"1\">n5</image>\n" +
            "      <image-v2 color=\"9e9256\" type=\"colored\">ovc_+8</image-v2>\n" +
            "      <image-v3 type=\"mono\">ovc</image-v3>\n" +
            "      <weather_type>пасмурно</weather_type>\n" +
            "      <weather_type_tt>болытлы</weather_type_tt>\n" +
            "      <weather_type_tr>bulutlu</weather_type_tr>\n" +
            "      <weather_type_kz>бұлыңғыр</weather_type_kz>\n" +
            "      <weather_type_ua>хмарно</weather_type_ua>\n" +
            "      <weather_type_by>пахмурна</weather_type_by>\n" +
            "      <wind_direction>nw</wind_direction>\n" +
            "      <wind_speed>1.5</wind_speed>\n" +
            "      <humidity>56</humidity>\n" +
            "      <pressure units=\"torr\">748</pressure>\n" +
            "      <mslp_pressure units=\"hPa\">997</mslp_pressure>\n" +
            "    </day_part>\n" +
            "    <hour at=\"0\">\n" +
            "      <temperature>9</temperature>\n" +
            "      <weather_condition code=\"clear\"/>\n" +
            "      <image type=\"1\">n7</image>\n" +
            "      <image-v2 color=\"a29652\" type=\"colored\">skc_n_+10</image-v2>\n" +
            "      <image-v3 type=\"mono\">skc_n</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"1\">\n" +
            "      <temperature>8</temperature>\n" +
            "      <weather_condition code=\"clear\"/>\n" +
            "      <image type=\"1\">n7</image>\n" +
            "      <image-v2 color=\"9e9256\" type=\"colored\">skc_n_+8</image-v2>\n" +
            "      <image-v3 type=\"mono\">skc_n</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"2\">\n" +
            "      <temperature>7</temperature>\n" +
            "      <weather_condition code=\"clear\"/>\n" +
            "      <image type=\"1\">n7</image>\n" +
            "      <image-v2 color=\"9e9256\" type=\"colored\">skc_n_+8</image-v2>\n" +
            "      <image-v3 type=\"mono\">skc_n</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"3\">\n" +
            "      <temperature>7</temperature>\n" +
            "      <weather_condition code=\"clear\"/>\n" +
            "      <image type=\"1\">n7</image>\n" +
            "      <image-v2 color=\"9e9256\" type=\"colored\">skc_n_+8</image-v2>\n" +
            "      <image-v3 type=\"mono\">skc_n</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"4\">\n" +
            "      <temperature>6</temperature>\n" +
            "      <weather_condition code=\"clear\"/>\n" +
            "      <image type=\"1\">n7</image>\n" +
            "      <image-v2 color=\"938863\" type=\"colored\">skc_n_+6</image-v2>\n" +
            "      <image-v3 type=\"mono\">skc_n</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"5\">\n" +
            "      <temperature>6</temperature>\n" +
            "      <weather_condition code=\"clear\"/>\n" +
            "      <image type=\"1\">n7</image>\n" +
            "      <image-v2 color=\"938863\" type=\"colored\">skc_n_+6</image-v2>\n" +
            "      <image-v3 type=\"mono\">skc_n</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"6\">\n" +
            "      <temperature>6</temperature>\n" +
            "      <weather_condition code=\"clear\"/>\n" +
            "      <image type=\"1\">7</image>\n" +
            "      <image-v2 color=\"938863\" type=\"colored\">skc_d_+6</image-v2>\n" +
            "      <image-v3 type=\"mono\">skc_d</image-v3>\n" +
            "    </hour>\n" +
            "    <hour at=\"7\">\n" +
            "      <temperature>6</temperature>\n" +
            "      <weather_condition code=\"clear\"/>\n" +
            "      <image type=\"1\">7</image>\n" +
            "      <image-v2 color=\"938863\" type=\"colored\">skc_d_+6</image-v2>\n" +
            "      <image-v3 type=\"mono\">skc_d</image-v3>\n" +
            "    </hour>\n" +
            "  </day>\n" +
            "</forecast>\n";
}
