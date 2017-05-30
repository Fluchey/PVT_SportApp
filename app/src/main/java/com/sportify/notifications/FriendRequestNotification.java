package com.sportify.notifications;

import android.content.Context;

import sportapp.pvt_sportapp.R;

/**
 * Created by Maja on 2017-05-18.
 */

public class FriendRequestNotification extends Notification {

    private int friendID;

    public FriendRequestNotification(String friendName, String imageBase64, int friendID){
        super(friendName, imageBase64);
        this.friendID = friendID;
    }

    public int getFriendID() {
        return friendID;
    }

    @Override
    public String getNotificationText(Context context){
        return friendName + " " + context.getString(R.string.friend_request);
    }

}
