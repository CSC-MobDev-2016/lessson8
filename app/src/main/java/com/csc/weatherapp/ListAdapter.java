package com.csc.weatherapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.csc.whetherapi.WeatherDescription;

/**
 * Created by Oleg Doronin
 * TaskList
 * Copyright (c) 2016 CS. All rights reserved.
 */
public class ListAdapter extends CursorAdapter {

    private ListCitiesFragment activity;
    private int paintFlags;

    ListAdapter(Context context, Cursor cursor, ListCitiesFragment activity) {
        super(context, cursor, 0);
        this.activity = activity;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        final FrameLayout flItemList = (FrameLayout) view.findViewById(R.id.item_list);
        final TextView tvName = (TextView) view.findViewById(R.id.item_list_name);
        final TextView tvTemp = (TextView) view.findViewById(R.id.item_list_temp);

        final String name = cursor.getString(cursor.getColumnIndex(FeedsTable.COLUMN_NAME));
        final String temp = cursor.getString(cursor.getColumnIndex(FeedsTable.COLUMN_TEMP));
        final int city_id = cursor.getInt(cursor.getColumnIndex(FeedsTable.COLUMN_ID));
        tvName.setText(name);
        tvTemp.setText(temp);
        final int id = cursor.getInt(cursor.getColumnIndex(FeedsTable._ID));


        flItemList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(name);
                builder.setPositiveButton(context.getString(R.string.open), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(activity.getContext(), DescriptionCityActivity.class);
                        intent.putExtra("CITY_ID", Integer.toString(city_id));
                        activity.startActivity(intent);
                    }
                });
                builder.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.setNeutralButton(context.getString(R.string.remove), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.getActivity().getContentResolver().delete(activity.ENTRIES_URI, FeedsTable._ID + "=" + Integer.toString(id), null);
                    }
                });

                builder.show();
            }
        });

    }
}
