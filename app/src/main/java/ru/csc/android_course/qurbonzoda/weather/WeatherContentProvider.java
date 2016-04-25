package ru.csc.android_course.qurbonzoda.weather;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WeatherContentProvider extends ContentProvider {
  public static final String AUTHORITY = "ru.csc.android_course.qurbonzoda.weather";
  public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

  public static final int ENTRIES = 1;
  public static final int ENTRIES_ID = 2;
  public static final int ADD_ENTRY = 3;

  private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

  static {
    uriMatcher.addURI(AUTHORITY, "/entries", ENTRIES);
    uriMatcher.addURI(AUTHORITY, "/entries/#", ENTRIES_ID);
    uriMatcher.addURI(AUTHORITY, "/add_entry", ADD_ENTRY);
  }

  private WeatherOpenHelper helper;

  public WeatherContentProvider() {
    helper = new WeatherOpenHelper(getContext());
  }

  @Override
  public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
    switch (uriMatcher.match(uri)) {
      case ENTRIES:
        break;
      case ENTRIES_ID:
        String id = uri.getLastPathSegment();

        selection = (selection != null && !selection.isEmpty() ? selection + " AND " : "")
            + WeatherTable._ID + " = " + id;
        break;
      default:
        throw new IllegalArgumentException("Wrong URI: " + uri);
    }

    int result = helper.getWritableDatabase().delete(WeatherTable.TABLE_NAME, selection, selectionArgs);
    getContext().getContentResolver().notifyChange(uri, null);
    return result;
  }

  @Override
  public String getType(@NonNull Uri uri) {
    // TODO: Implement this to handle requests for the MIME type of the data
    // at the given URI.
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public Uri insert(@NonNull Uri uri, ContentValues values) {
    int match = uriMatcher.match(uri);
    switch (match) {
      case ENTRIES:
        break;
      case ENTRIES_ID:
      default:
        throw new UnsupportedOperationException("Not yet implemented");
    }
    long rowId = helper.getWritableDatabase().insert(WeatherTable.TABLE_NAME, "?", values);
    Uri inserted = ContentUris.withAppendedId(uri, rowId);
    getContext().getContentResolver().notifyChange(inserted, null);
    return inserted;
  }

  @Override
  public boolean onCreate() {
    helper = new WeatherOpenHelper(getContext());
    return true;
  }

  @Override
  public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                      String[] selectionArgs, String sortOrder) {
    SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
    builder.setTables(WeatherTable.TABLE_NAME);
    int match = uriMatcher.match(uri);
    switch (match) {
      case ENTRIES:
        break;
      case ADD_ENTRY:
        Observable.just(selection)
            .flatMap(this::getWeatherValues)
            .doOnNext(values -> Log.d("ADD_ENTRY", "adding " + values.get(
                WeatherTable.COLUMN_CITY_NAME) + ", " + values.get(WeatherTable.COLUMN_COUNTRY)))
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(values -> insert(Uri.withAppendedPath(CONTENT_URI, "entries"), values),
                (e) -> Log.d("ADD_ENTRY", "Exception occurred ", e.getCause()),
                () -> Log.d("ADD_ENTRY", "City adding completed"));
        return null;
      case ENTRIES_ID:
        String id = uri.getLastPathSegment();
        selection = (selection != null && !selection.isEmpty() ? selection + " AND " : "")
            + WeatherTable._ID + " = " + id;
        break;
      default:
        throw new UnsupportedOperationException("Not yet implemented");
    }
    SQLiteDatabase db = helper.getReadableDatabase();
    Cursor cursor = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
    cursor.setNotificationUri(getContext().getContentResolver(), uri);
    return cursor;
  }

  @NonNull
  private Observable<ContentValues> getWeatherValues(String cityName) {
    List<ContentValues> valuesList = new ArrayList<>();
    JsonReader jsonReader = null;
    try {
      URL url = new URL("http://api.openweathermap.org/data/2.5/find?q="
          + URLEncoder.encode(cityName, "UTF-8")
          + "&units=metric&lang=en&appid=46f591c303a950b801481439e141d1ff");

      jsonReader = new JsonReader(new InputStreamReader(url.openStream()));
      jsonReader.beginObject();
      int count = 0;
      while (jsonReader.hasNext()) {
        String attribute = jsonReader.nextName();
        if (attribute.equals("count")) {
          count = jsonReader.nextInt();
        } else if (attribute.equals("list")) {
          jsonReader.beginArray();
          for (int i = 0; i < count; i++) {
            ContentValues values = new ContentValues();
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
              String listElement = jsonReader.nextName();
              switch (listElement) {
                case "name":
                  values.put(WeatherTable.COLUMN_CITY_NAME, jsonReader.nextString());
                  break;
                case "coord":
                  jsonReader.beginObject();
                  while (jsonReader.hasNext()) {
                    switch (jsonReader.nextName()) {
                      case "lon":
                        values.put(WeatherTable.COLUMN_COORD_LON, jsonReader.nextString());
                        break;
                      case "lat":
                        values.put(WeatherTable.COLUMN_COORD_LAT, jsonReader.nextString());
                        break;
                      default:
                        jsonReader.skipValue();
                    }
                  }
                  jsonReader.endObject();
                  break;
                case "main":
                  jsonReader.beginObject();
                  while (jsonReader.hasNext()) {
                    switch (jsonReader.nextName()) {
                      case "temp":
                        values.put(WeatherTable.COLUMN_CURRENT_TEMP, jsonReader.nextString());
                        break;
                      case "pressure":
                        values.put(WeatherTable.COLUMN_PRESSURE, jsonReader.nextString());
                        break;
                      case "humidity":
                        values.put(WeatherTable.COLUMN_HUMIDITY, jsonReader.nextString());
                        break;
                      case "temp_min":
                        values.put(WeatherTable.COLUMN_TEMP_MIN, jsonReader.nextString());
                        break;
                      case "temp_max":
                        values.put(WeatherTable.COLUMN_TEMP_MAX, jsonReader.nextString());
                        break;
                      default:
                        jsonReader.skipValue();
                    }
                  }
                  jsonReader.endObject();
                  break;
                case "wind":
                  jsonReader.beginObject();
                  while (jsonReader.hasNext()) {
                    switch (jsonReader.nextName()) {
                      case "speed":
                        values.put(WeatherTable.COLUMN_WIND_SPEED, jsonReader.nextString());
                        break;
                      case "deg":
                        values.put(WeatherTable.COLUMN_WIND_DEGREE, jsonReader.nextString());
                        break;
                      default:
                        jsonReader.skipValue();
                    }
                  }
                  jsonReader.endObject();
                  break;
                case "sys":
                  jsonReader.beginObject();
                  while (jsonReader.hasNext()) {
                    switch (jsonReader.nextName()) {
                      case "country":
                        values.put(WeatherTable.COLUMN_COUNTRY, jsonReader.nextString());
                        break;
                      default:
                        jsonReader.skipValue();
                    }
                  }
                  jsonReader.endObject();
                  break;
                case "weather":
                  jsonReader.beginArray();
                  jsonReader.beginObject();
                  while (jsonReader.hasNext()) {
                    switch (jsonReader.nextName()) {
                      case "main":
                        values.put(WeatherTable.COLUMN_WEATHER_MAIN, jsonReader.nextString());
                        break;
                      case "description":
                        values.put(WeatherTable.COLUMN_WEATHER_DESCRIPTION, jsonReader.nextString());
                        break;
                      case "icon":
                        values.put(WeatherTable.COLUMN_WEATHER_ICON, jsonReader.nextString());
                        break;
                      default:
                        jsonReader.skipValue();
                    }
                  }
                  jsonReader.endObject();
                  jsonReader.endArray();
                  break;
                default:
                  jsonReader.skipValue();
              }
            }
            jsonReader.endObject();
            valuesList.add(values);
          }
          jsonReader.endArray();
        } else {
          jsonReader.skipValue();
        }
      }
      jsonReader.endObject();
    } catch (IOException e) {
      Log.d("Exception json parsing", e.toString());
      // ignore
    } finally {
      if (jsonReader != null) {
        try {
          jsonReader.close();
        } catch (IOException e) {
          // ignore
        }
      }
    }
    return Observable.from(valuesList);
  }

  @Override
  public int update(@NonNull Uri uri, ContentValues values, String selection,
                    String[] selectionArgs) {

    int match = uriMatcher.match(uri);
    switch (match) {
      case ENTRIES:
        break;
      case ENTRIES_ID:
        String id = uri.getLastPathSegment();
        selection = (selection != null && !selection.isEmpty() ? selection + " AND " : "")
            + WeatherTable._ID + " = " + id;
        break;

      default:
        throw new UnsupportedOperationException("Not yet implemented");
    }
    int result = helper.getWritableDatabase().update(WeatherTable.TABLE_NAME, values, selection, selectionArgs);
    getContext().getContentResolver().notifyChange(uri, null);
    return result;
  }
}
