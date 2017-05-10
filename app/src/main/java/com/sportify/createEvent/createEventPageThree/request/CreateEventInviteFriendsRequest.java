package com.sportify.createEvent.createEventPageThree.request;

/**
 * Created by Maja on 2017-04-27.
 */

public interface CreateEventInviteFriendsRequest {

    interface OnShowFriendsFinishedListener{
        void showApiResponse(String... params);
    }

    void makeApiRequest(String jsonMessage);
}
