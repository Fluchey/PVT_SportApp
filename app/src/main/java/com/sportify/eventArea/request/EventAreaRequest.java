package com.sportify.eventArea.request;


import com.sportify.storage.Event;
import com.sportify.storage.Place;

/**
 * Created by fluchey on 2017-05-17.
 */

public interface EventAreaRequest {
     void makeApiRequestPut(String jsonMessage, String endUrl, String method, String command);

    void loadEventData(String json);

    Event getEvent();

    Place getPlace();


    interface onRequestFinishedListener{
        void showApiResponse(String command, String... params);
    }
}
