package com.csc.shmakov.weatherapp.common;

import org.w3c.dom.NodeList;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Pavel on 4/23/2016.
 */
public class EventDispatcher<O> {
    protected final CopyOnWriteArrayList<O> observers = new CopyOnWriteArrayList<>();

    public final void addObserver(O observer) {
        observers.add(observer);
    }

    public final void removeObserver(O observer) {
        observers.remove(observer);
    }
}
