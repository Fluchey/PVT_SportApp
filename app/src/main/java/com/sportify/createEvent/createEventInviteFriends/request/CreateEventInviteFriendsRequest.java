package com.sportify.createEvent.createEventInviteFriends.request;

/**
 * Created by Maja on 2017-04-27.
 */

public interface CreateEventInviteFriendsRequest {

    interface OnShowFriendsFinishedListener{
        void showApiResponse(String... params);
    }

//    void makeApiRequest(String jsonMessage);
//
    void makeApiRequest(String endUrl, String method, String jsonMessage);
}
