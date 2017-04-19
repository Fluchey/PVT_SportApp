package com.sportify.event.request;

/**
 * Created by Maja on 2017-04-18.
 */

public interface CreateEventRequest {

    interface OnCreateEventFinishedListener{
        void showApiResponse(String apiResponse);
    }

    void makeApiRequest(String jsonMessage);
}
