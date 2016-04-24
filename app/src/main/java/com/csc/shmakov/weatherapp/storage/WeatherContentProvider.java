package com.csc.shmakov.weatherapp.storage;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;

public class WeatherContentProvider extends ContentProvider {
    public static final String AUTHORITY = "com.csc.shmakov.weatherapp";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final Uri CITIES_URI = Uri.withAppendedPath(WeatherContentProvider.CONTENT_URI, "cities");
    public static final Uri FORECASTS_URI = Uri.withAppendedPath(WeatherContentProvider.CONTENT_URI, "forecasts");

    private static final int CITIES = 1;
    private static final int FORECASTS = 2;
    private static final int FORECAST = 3;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, "/cities", CITIES);
        uriMatcher.addURI(AUTHORITY, "/forecasts", FORECASTS);
        uriMatcher.addURI(AUTHORITY, "/forecasts/#", FORECAST);
    }

    private DatabaseOpenHelper helper;

    public WeatherContentProvider() {
        helper = new DatabaseOpenHelper(getContext());
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        if (uriMatcher.match(uri) != FORECAST) {
            throw new UnsupportedOperationException("Not yet implemented");
        }
        String id = uri.getLastPathSegment();
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete(ForecastsTable.TABLE_NAME, ForecastsTable.COLUMN_CITY_ID + "=?", new String[]{id});
    }

    @Override
    public String getType(@NonNull Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        helper = new DatabaseOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        String tableName, where = null;
        String[] whereArgs = null;
        int match = uriMatcher.match(uri);
        if (match == CITIES) {
            tableName = CitiesTable.TABLE_NAME;
        } else if (match == FORECASTS){
            tableName = ForecastsTable.TABLE_NAME;
        }
        else if (match == FORECAST){
            tableName = ForecastsTable.TABLE_NAME;
            where = ForecastsTable.COLUMN_CITY_ID+"=?";
            whereArgs = new String[]{uri.getLastPathSegment()};
        }
        else {
            throw new UnsupportedOperationException();
        }
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(tableName);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = builder.query(db, projection, where, whereArgs, null, null, null);
//        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        if (uriMatcher.match(uri) != FORECAST) {
            throw new UnsupportedOperationException("Not yet implemented");
        }
        helper.getWritableDatabase().insert(ForecastsTable.TABLE_NAME, null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        if (uriMatcher.match(uri) != CITIES) {
            throw new UnsupportedOperationException("Not yet implemented");
        }
        SQLiteDatabase database = helper.getWritableDatabase();
        database.execSQL(CitiesTable.SQL_DROP_TABLE);
        database.execSQL(CitiesTable.SQL_CREATE_TABLE);
        for (ContentValues cityContentValues : values) {
            database.insert(CitiesTable.TABLE_NAME,  null, cityContentValues);
        }

        getContext().getContentResolver().notifyChange(CITIES_URI, null);
        return values.length;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        if (uriMatcher.match(uri) != FORECAST) {
            throw new UnsupportedOperationException("Not yet implemented");
        }
        String where = ForecastsTable.COLUMN_CITY_ID+"=?";
        String[] whereArgs = new String[]{uri.getLastPathSegment()};
        int affected = helper.getWritableDatabase().update(ForecastsTable.TABLE_NAME, values, where, whereArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return affected;
    }
}
