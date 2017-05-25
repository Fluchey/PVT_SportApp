package com.sportify.eventArea.request;


import com.sportify.storage.Event;
import com.sportify.storage.Place;
import com.sportify.storage.User;

/**
 * Created by fluchey on 2017-05-17.
 */

public interface EventAreaRequest {
     void makeApiRequestPut(String jsonMessage, String endUrl, String method, String command);

    String[] loadEventData(String json);

    Event getEvent();

    Place getPlace();

    User getUser();

    interface onRequestFinishedListener{
        void showApiResponse(String command, String... params);
    }
}
