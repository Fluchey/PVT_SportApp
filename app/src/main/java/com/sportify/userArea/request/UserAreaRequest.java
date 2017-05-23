package com.sportify.userArea.request;

import com.sportify.storage.Event;

import java.util.ArrayList;

/**
 * Created by fluchey on 2017-04-20.
 */

public interface UserAreaRequest {
    interface OnRequestFinishedListener{
        void showApiResponse(String command, String[] result);
    }

    void makeApiRequestPut(String jsonMessage, String endURL, String method, String command);

    void updateEvents(String json);

    ArrayList<Event> getEvents();
}
