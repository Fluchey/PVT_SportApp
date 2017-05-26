package com.sportify.friendprofile.request;

import com.sportify.storage.Profile;

/**
 * Created by rasmu on 25/05/2017.
 */

public interface FriendprofileRequest {
    interface onRequestFinishedListener{
        void showApiResponse(String command, String... params);
    }

    public void updateProfile(int id);
    public void setProfile(String jsonMessage);
    public Profile getProfile();

    void makeApiRequestPut(String jsonMessage, String endURL, String method, String command);
    void makeApiRequestGet(String endURL, String method, String command);
}
