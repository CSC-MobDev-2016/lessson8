package com.csc.whetherapi;

import java.util.ArrayList;

/**
 * Created by Oleg Doronin
 * WhetherApp
 * Copyright (c) 2016 CS. All rights reserved.
 */
public class WeatherDescription {
    public String name;
    public int id;
    public int cod;
    public Coord coord;
    public ArrayList<Weather> weather;
    public String base;
    public Main main;
    public Wind wind;
    public Clouds clouds;
    public int dt;
    public Sys sys;

    public class Coord {
        public double lon;
        public double lat;
    }
    public class Weather {
        public int id;
        public String main;
        public String description;
        public String icon;
    }
    public class Main {
        public double temp;
        public double pressure;
        public double humidity;
        public double temp_min;
        public double temp_max;
    }
    public class Wind {
        public int speed;
        public int deg;
    }
    public class Clouds {
        public int all;
    }
    public class Sys {
        public int type;
        public int id;
        public double message;
        public String country;
        public int sunrise;
        public int sunset;
    }

    @Override
    public String toString() {
        return name;
    }
}


