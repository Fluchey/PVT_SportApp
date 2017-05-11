package com.sportify.addFriend.request;

/**
 * Created by Maja on 2017-05-05.
 */

public interface AddFriendRequest {

    interface OnShowFriendsFinishedListener{
        void showApiResponse(String... params);
    }

    void makeApiRequestGetUsers(String method, String url);

    void makeApiRequestAddFriend(String method, String url, String jsonMessage);
}
