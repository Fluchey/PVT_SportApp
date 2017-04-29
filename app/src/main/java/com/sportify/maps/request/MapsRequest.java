package com.sportify.maps.request;

/**
 * Created by fluchey on 2017-04-26.
 */

public interface MapsRequest {
    interface onRequestFinishedListener{
        void showApiResponse(String... params);

        void closeLoadIndicator();

    }

    void makeApiRequest(String jsonMessage, String endURL);
}
