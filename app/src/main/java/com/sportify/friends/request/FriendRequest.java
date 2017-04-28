package com.sportify.friends.request;

/**
 * Created by Maja on 2017-04-27.
 */

public interface FriendRequest {

    interface OnShowFriendsFinishedListener{
        void showApiResponse(String apiResponse);
    }

    void makeApiRequest(String jsonMessage);
}
