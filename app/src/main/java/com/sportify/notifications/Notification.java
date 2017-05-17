package com.sportify.notifications;

import android.widget.ImageView;

/**
 * Created by Maja on 2017-05-17.
 */

public class Notification {

    private String typeOfNotification;
    private String eventName;
    private ImageView profilePicture;
    private ImageView symbolToLeft;
    private String host;
    private int eventID;

//    public Notification(String typeOfNotification, String eventName, ImageView profilePicture, ImageView symbolToLeft){
//        this.typeOfNotification = typeOfNotification;
//        this.eventName = eventName;
//        this.profilePicture = profilePicture;
//        this.symbolToLeft = symbolToLeft;
//    }
    public Notification(String host, String eventName, int eventID) {
        this.host = host;
        this.eventName = eventName;
        this.eventID = eventID;
    }

    public String getTypeOfNotification() {
        return typeOfNotification;
    }

    public String getEventName() {
        return eventName;
    }

    public ImageView getProfilePicture() {
        return profilePicture;
    }

    public ImageView getSymbolToLeft() {
        return symbolToLeft;
    }

    public String getHost() {
        return host;
    }

    public int getEventID() {
        return eventID;
    }
}
