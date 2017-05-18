package com.sportify.placearea.request;

/**
 * Created by fluchey on 2017-05-18.
 */

public interface PlaceAreaRequest {
    interface OnRequestFinishedListener{
        void showApiResponse(String response);
    }
}
