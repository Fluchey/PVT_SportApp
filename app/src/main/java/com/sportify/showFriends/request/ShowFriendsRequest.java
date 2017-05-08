package com.sportify.showFriends.request;

/**
 * Created by Maja on 2017-05-05.
 */

public interface ShowFriendsRequest {

    interface OnShowFriendsFinishedListener{
        void showApiResponse(String... params);
    }

    void makeApiRequest(String jsonMessage);
}
