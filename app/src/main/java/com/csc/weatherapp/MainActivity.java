package com.csc.weatherapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            addNewFragment();
        }
        restartNotify();

    }

    public void addCity(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.select_city));
        final LinearLayout ll = (LinearLayout) getLayoutInflater().inflate(R.layout.city_alert, null);
        builder.setView(ll);
        final String cities[] = { "Moscow", "Saint Petersburg", "Belgorod" };
        final Integer codes[] = { 5601538, 498817,  578072 };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cities);
        final Spinner spinner = (Spinner) ll.findViewById(R.id.spinner_city);
        spinner.setAdapter(adapter);

        builder.setPositiveButton(getString(R.string.add), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int position = spinner.getSelectedItemPosition();
                Cursor cursor = null;
                try {
                    cursor = getContentResolver().query(ListCitiesFragment.ENTRIES_URI, null, FeedsTable.COLUMN_ID + " = " + codes[position], null, null);

                } catch (Exception ex) {

                } finally {
                    if (cursor == null || cursor.getCount() <= 0) {
                        ContentValues values = new ContentValues();
                        values.put(FeedsTable.COLUMN_NAME, cities[position]);
                        values.put(FeedsTable.COLUMN_ID, codes[position]);
                        getContentResolver().insert(ListCitiesFragment.ENTRIES_URI, values);
                        WeatherTask task = new WeatherTask(getContentResolver());
                        task.execute();
                    }

                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void addNewFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_main_container, new ListCitiesFragment())
                .commit();
        WeatherTask task = new WeatherTask(getContentResolver());
        task.execute();
    }

    public void fullUpdate() {

    }

    AlarmManager am;
    private void restartNotify() {
        am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, TimeNotification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT );
// На случай, если мы ранее запускали активити, а потом поменяли время,
// откажемся от уведомления
        am.cancel(pendingIntent);
// Устанавливаем разовое напоминание
        am.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(), 1000 * 60 * 60, pendingIntent);
    }

}
