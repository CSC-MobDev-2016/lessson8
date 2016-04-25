package com.csc.simple_weather;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import static com.csc.simple_weather.MainActivity.ENTRIES_URI;
import static com.csc.simple_weather.WeatherTable.COLUMN_CITY;
import static com.csc.simple_weather.WeatherTable.COLUMN_WEATHER;

public class ListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String EXTRA_TITLE = "extra_title";
    private MyListCursorAdapter adapter;
    private RecyclerView recyclerView;

    private final ContentObserver observer = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
        }
    };

    public static ListFragment newInstance(String title) {
        ListFragment masterFragment = new ListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_TITLE, title);
        masterFragment.setArguments(bundle);
        return masterFragment;
    }

    public ListFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        Cursor cursor = getActivity().getContentResolver().query(ENTRIES_URI, null, null, null, null);
        adapter = new MyListCursorAdapter(getActivity(), cursor);
        cursor.registerContentObserver(observer);
        //((TextView) view.findViewById(R.id.tv)).setText(getArguments().getString(EXTRA_TITLE));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                    }
                })
        );
        getActivity().getSupportLoaderManager().initLoader(0, null, this);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().getContentResolver().unregisterContentObserver(observer);
    }

    public void addData(View view) {
        final EditText editText = new EditText(getActivity());
        final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
        dialog.setMessage("Enter new city");
        dialog.setView(editText);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    RemoteFetch.updateWeatherData(getActivity(), editText.getText().toString());
                    getActivity().getSupportLoaderManager().getLoader(0).forceLoad();
                } catch (Exception e) {
                    dialog.dismiss();
                    final AlertDialog alert;

                    AlertDialog.Builder dialog2 = new AlertDialog.Builder(getActivity());
                    alert = dialog2.create();
                    alert.setTitle("City" + editText.getText().toString() + " not found");
                    editText.setText("");
                }
            }});
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE,"Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}});
        dialog.show();
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
        return new MyCursorLoader(getActivity(), getActivity().getContentResolver());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.changeCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    static class MyCursorLoader extends CursorLoader {

        ContentResolver res;

        public MyCursorLoader(Context context, ContentResolver res) {
            super(context);
            this.res = res;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = res.query(ENTRIES_URI, null, null, null, null);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return cursor;
        }

    }
}
