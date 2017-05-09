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

    void updateAllPlaces(String jsonMessage);

    void updateAllEvents(String jsonMessage);

    void updateCurrentSearchPlaces(String search);

    void updateCurrentSearchEvents(String search);

    ArrayList<Place> getAllPlaces();

    ArrayList<Place> getCurrentSearchPlaces();

    ArrayList<Event> getAllEvents();

    ArrayList<Event> getCurrentSearchEvents();

}
