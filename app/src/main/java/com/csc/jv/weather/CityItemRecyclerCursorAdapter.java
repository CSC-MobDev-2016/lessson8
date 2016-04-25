package com.csc.jv.weather;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class CityItemRecyclerCursorAdapter extends CursorRecyclerViewAdapter<CityItemRecyclerCursorAdapter.ViewHolder> {

    private final CityListFragment.OnListFragmentInteractionListener mListener;

    public CityItemRecyclerCursorAdapter(Context context, Cursor cursor, CityListFragment.OnListFragmentInteractionListener listener) {
        super(context, cursor);
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_city_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, Cursor cursor) {

        final ForecastItem forecastItem = ForecastItem.fromCursor(cursor);
        final String _id = cursor.getString(cursor.getColumnIndex(ForecastTable._ID));

        holder.mItem = forecastItem;
        holder.mIdView.setText(forecastItem.city_name);
        holder.mContentView.setText(forecastItem.temperature);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem, _id);
                }
            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle(R.string.delete)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                context.getContentResolver().delete(
                                        Uri.withAppendedPath(MainActivity.FORECAST_ENTRIES_URI, _id),
                                        null,
                                        null);
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setCancelable(false)
                        .show();

                return true;
            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public ForecastItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
