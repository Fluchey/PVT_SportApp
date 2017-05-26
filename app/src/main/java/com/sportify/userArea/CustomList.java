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

import java.util.HashMap;

import sportapp.pvt_sportapp.R;

public class CustomList extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] eventName;
    private final Event[] events;
    private final Integer[] imageId;
    HashMap<Integer, String> creator;
    HashMap<Integer, String> placeName;

    public CustomList(Activity context, String[] web, Event[] events, HashMap<Integer, String> creator, HashMap<Integer, String> placeName, Integer[] imageId) {
        super(context, R.layout.user_area_list_item, web);
        this.context = context;
        this.eventName = web;
        this.events = events;
        this.imageId = imageId;
        this.creator = creator;
        this.placeName = placeName;
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
        eventName.setText(this.events[position].getEventName());
        eventHost.setText(this.creator.get(this.events[position].getId()));
        eventPlace.setText(this.placeName.get(this.events[position].getId()));
        eventDate.setText(this.events[position].getDate());

        imageView.setImageResource(imageId[1]);
        return rowView;
    }
}
