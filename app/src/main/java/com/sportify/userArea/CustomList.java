package com.sportify.userArea;

/**
 * Created by fluchey on 2017-05-19.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sportify.storage.Event;

import sportapp.pvt_sportapp.R;

public class CustomList extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] eventName;
    private final Event[] events;
    private final Integer[] imageId;

    public CustomList(Activity context, String[] web, Event[] events, Integer[] imageId) {
        super(context, R.layout.user_area_list_item, web);
        this.context = context;
        this.eventName = web;
        this.events = events;
        this.imageId = imageId;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.user_area_list_item, null, true);
        TextView eventName = (TextView) rowView.findViewById(R.id.tvEventName);
        TextView eventHost = (TextView) rowView.findViewById(R.id.tvEventHost);
        TextView eventPlace = (TextView) rowView.findViewById(R.id.tvEventPlace);
        TextView eventDate = (TextView) rowView.findViewById(R.id.tvEventDate);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.userAreaListItemImg);
        eventName.setText(this.eventName[position]);
        eventHost.setText("Adrian");
        eventPlace.setText("Abrahamsbergs Bollplan");
        eventDate.setText("2017-01-15");

        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}
