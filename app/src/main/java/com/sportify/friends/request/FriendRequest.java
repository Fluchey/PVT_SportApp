package com.sportify.friends.request;

import java.util.ArrayList;

/**
 * Created by Maja on 2017-04-27.
 */

public interface FriendRequest {

    interface OnShowFriendsFinishedListener{
        void showApiResponse(String apiResponse);
        void getFriends(String friends);
    }

    void makeApiRequest(String jsonMessage);
}
