package com.sportify.maps.request;

import com.sportify.storage.Place;

import java.util.ArrayList;

/**
 * Created by fluchey on 2017-04-26.
 */

public interface MapsRequest {
    void updateCurrentSearch(String jsonMessage);

    interface onRequestFinishedListener{
        void showApiResponse(String command, String... params);
    }

    void makeApiRequestPut(String jsonMessage, String endURL, String method, String command);

    void makeApiRequestGet(String method, String endURL, String command);

    void updateAllPlaces(String jsonMessage);

    ArrayList<String> getPlacesName();

    ArrayList<Place> getPlaces();

}
