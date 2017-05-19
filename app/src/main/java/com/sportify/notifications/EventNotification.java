package com.sportify.notifications;

import android.content.Context;
import android.widget.ImageView;

import sportapp.pvt_sportapp.R;

/**
 * Created by Maja on 2017-05-17.
 */

public class EventNotification extends Notification {

    private String eventName;
    private int eventID;

    public EventNotification(String friendname, String eventName, int eventID) {
        super(friendname);
        this.eventName = eventName;
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    @Override
    public String getNotificationText(Context context){
        return friendName + " " + context.getString(R.string.event_invite_notification) + " " + eventName;
    }

    public int getEventID() {
        return eventID;
    }
}
