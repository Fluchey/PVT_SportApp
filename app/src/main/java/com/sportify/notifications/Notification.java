package com.sportify.notifications;

import android.widget.ImageView;

/**
 * Created by Maja on 2017-05-17.
 */

public class Notification {

    private String typeOfNotification;
    private String message;
    private ImageView profilePicture;
    private ImageView symbolToLeft;
    private String host;

//    public Notification(String typeOfNotification, String message, ImageView profilePicture, ImageView symbolToLeft){
//        this.typeOfNotification = typeOfNotification;
//        this.message = message;
//        this.profilePicture = profilePicture;
//        this.symbolToLeft = symbolToLeft;
//    }
    public Notification(String host, String message) {
        this.host = host;
        this.message = message;
    }

    public String getTypeOfNotification() {
        return typeOfNotification;
    }

    public String getMessage() {
        return message;
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
}
