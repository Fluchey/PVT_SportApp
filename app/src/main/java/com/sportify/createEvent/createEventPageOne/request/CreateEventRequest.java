package com.sportify.createEvent.createEventPageOne.request;

import com.sportify.storage.Place;

import java.util.ArrayList;

/**
 * Created by Maja on 2017-04-18.
 */

public interface CreateEventRequest {

    void upDatePlaces(String jsonMessage);

    ArrayList<Place> getPlaces();

    interface OnCreateEventFinishedListener{
        void showApiResponse(String apiResponse, String command);
    }

    void makeApiRequestPut(String method, String endUrl, String jsonMessage, String command);

    void makeApiRequestGet(String method, String endUrl, String command);
}
