package com.csc.shmakov.weatherapp.common;

/**
 * Created by Pavel on 4/3/2016.
 */
public interface DataChangeObserver {
    void onItemInserted(int position);
    void onItemMoved(int from, int to);
    void onItemChanged(int position);
    void onItemRemoved(int position);
    void onDataReset();
}
