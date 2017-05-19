package com.sportify.arrayAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sportify.notifications.Notification;

import java.util.ArrayList;

import sportapp.pvt_sportapp.R;

/**
 * Created by Maja on 2017-05-09.
 */

public class MyArrayAdapterNotifications extends ArrayAdapter {


    private ArrayList<Notification> eventNotifications = new ArrayList<>();

    public MyArrayAdapterNotifications(Context context, int rowId, ArrayList<Notification> eventNotifications) {
        super(context, rowId, eventNotifications);
        this.eventNotifications = eventNotifications;
    }

    /**
     * @param position    - position in listView
     * @param convertView - row to be converted
     * @param parent      - parent view
     * @return
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View row = convertView;

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = layoutInflater.inflate(R.layout.notification_list_item, null);
        final TextView notificationMessage = (TextView) row.findViewById(R.id.tvNotification);

        ImageView imageView = (ImageView) row.findViewById(R.id.profilePictureNotificationView);
        //TODO: Fånga upp host och hämta dess profilbild
        //ImageView profilePicture = eventNotifications.get(position).getProfilePicture();
        //imageView.setImageResource();

        notificationMessage.setText(eventNotifications.get(position).getNotificationText(getContext()));

        return row;
    }
}
