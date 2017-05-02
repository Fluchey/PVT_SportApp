package com.sportify.friends.request;

/**
 * Created by Maja on 2017-04-27.
 */

public interface FriendRequest {

    interface OnShowFriendsFinishedListener{
        void showApiResponse(String... params);
//        void getFriends(String... params);
    }

    void makeApiRequest(String jsonMessage);
}
