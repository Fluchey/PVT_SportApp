package com.sportify.notifications;

import android.content.Context;

/**
 * Created by Maja on 2017-05-19.
 */

public class Notification {

//    ImageView profilePicture;
    String profilePicture;
    String friendName;

    public Notification(String friendName, String imageBase64){
        this.friendName = friendName;
        this.profilePicture = imageBase64;
    }

    public String getNotificationText(Context context){
        return null;
    }

    public String getName(){
        return friendName;
    }

    public String getProfilePicture(){
        return profilePicture;
    }
//    public ImageView getProfilePicture(){
//        return profilePicture;
//    }
}
