package com.sportify.maps.request;

import com.sportify.storage.Event;
import com.sportify.storage.Place;

import java.util.ArrayList;

/**
 * Created by fluchey on 2017-04-26.
 */

public interface MapsRequest {

    interface onRequestFinishedListener{
        void showApiResponse(String command, String... params);
    }

    void makeApiRequestPut(String jsonMessage, String endURL, String method, String command);

    void makeApiRequestGet(String method, String endURL, String command);

    void updateCurrentPlaces(String jsonMessage);

    void updateCurrentEvents(String jsonMessage);

    ArrayList<Place> getCurrentSearchPlaces();

    ArrayList<Event> getEvents();

}
