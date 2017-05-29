package com.sportify.friendprofile.request;

import com.sportify.storage.Event;
import com.sportify.storage.Profile;

import java.util.ArrayList;
import java.util.HashMap;

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
    public ArrayList<Event> getEvents();
    public void setEvents(String json);
    public void updateEvents(int userId);
    public HashMap<Integer, String> getCreator();
    public HashMap<Integer, String> getPlaceName();
    public ArrayList<String> getEventImages();

    void makeApiRequestPut(String jsonMessage, String endURL, String method, String command);
    void makeApiRequestGet(String endURL, String method, String command);
}
