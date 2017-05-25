package com.sportify.userArea.request;

import com.sportify.storage.Event;

import java.util.ArrayList;
import java.util.HashMap;

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

    HashMap<Integer, String> getCreator();

    HashMap<Integer, String> getPlaceName();
}
