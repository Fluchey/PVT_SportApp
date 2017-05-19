package com.sportify.notifications;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by Maja on 2017-05-19.
 */

public class Notification {

    ImageView profilePicture;
    String friendName;

    public Notification(String friendName){
        //TODO: Hämta med profilbild
        this.friendName = friendName;
    }

    public String getNotificationText(Context context){
        return null;
    }

    public String getName(){
        return friendName;
    }

    public ImageView getProfilePicture(){
        return profilePicture;
    }
}
