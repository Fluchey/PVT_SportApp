package com.sportify.editEvent.activity.request;

import com.sportify.storage.Place;

import java.util.ArrayList;

/**
 * Created by Maja on 2017-05-22.
 */

public interface EditEventRequest {

    void upDatePlaces(String jsonMessage);

    ArrayList<Place> getPlaces();

    interface OnEditEventFinishedListener {
        void showApiResponse(String apiResponse, String command);
    }

    void makeApiRequestPut(String method, String endUrl, String jsonMessage, String command);
}
