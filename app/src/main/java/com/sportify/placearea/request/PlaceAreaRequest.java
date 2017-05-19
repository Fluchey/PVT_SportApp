package com.sportify.placearea.request;

import com.sportify.storage.Place;
import com.sportify.storage.PlaceReview;

import java.util.ArrayList;

/**
 * Created by fluchey on 2017-05-18.
 */

public interface PlaceAreaRequest {
    void loadPlaceData(String json);

    interface OnRequestFinishedListener{
        void showApiResponse(String command, String[] result);
    }

    void makeApiRequestPut(String jsonMessage, String endURL, String method, String command);

    Place getPlace();

    void updateAllReviews(String jsonMessage);
    void renewAllReviews(int placeId);
    ArrayList<PlaceReview> getAllReviews();
}
