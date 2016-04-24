package com.csc.simple_weather;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class MyListCursorAdapter extends CursorRecyclerViewAdapter<MyListCursorAdapter.ViewHolder>{
    private MainActivity activity;
    private Context context;

    public MyListCursorAdapter(Context context,Cursor cursor){
        super(context,cursor);
        activity = (MainActivity) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
        context = parent.getContext();
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        City city = City.fromCursor(cursor);

        viewHolder.city.setText(city.name);
        viewHolder.weather.setText(city.weather);
        viewHolder.description.setText(city.description);
        viewHolder.city.setText(city.name);
        if (city.description.equals("sunny")) {
            viewHolder.icon.setImageResource(R.drawable.star);
        } else {
            viewHolder.icon.setImageResource(R.drawable.emptystar);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView city;
        TextView weather;
        TextView description;
        ImageView icon;

        ViewHolder(View itemView) {
            super(itemView);
            city = (TextView)itemView.findViewById(R.id.cityView);
            weather = (TextView)itemView.findViewById(R.id.weatherView);
            icon = (ImageView)itemView.findViewById(R.id.star);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select The Action");
            menu.add(0, v.getId(), 0, "Set favourite");
            menu.add(0, v.getId(), 0, "Delete");
            menu.getItem(0).setOnMenuItemClickListener(onFav);
            menu.getItem(1).setOnMenuItemClickListener(onFileDelete);
        }


        private final MenuItem.OnMenuItemClickListener onFav = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                final EditText editText = new EditText(context);
                final AlertDialog dialog = new AlertDialog.Builder(activity).create();
                dialog.setMessage("Enter new city");
                dialog.setView(editText);
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    RemoteFetch.renderWeather(editText.getText().toString());
                                } catch (Exception e) {
                                    dialog.dismiss();
                                    final AlertDialog alert;

                                    AlertDialog.Builder dialog2 = new AlertDialog.Builder(activity);
                                    alert = dialog2.create();
                                    alert.setTitle("City" + editText.getText().toString() + " not found");
                                    editText.setText("");
                                }
                            }});
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE,"Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}});
                dialog.show();
                return true;
            }
        };

        private final MenuItem.OnMenuItemClickListener onFileDelete = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                        .setMessage("Delete?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                            }})
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {}});
                builder.create().show();
                return true;
            }
        };
    }
}
