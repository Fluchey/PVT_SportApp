package com.sportify.createEvent.createEventPreview.request;

/**
 * Created by Maja on 2017-05-12.
 */

public interface CreateEventPreviewRequest {

    interface OnCreateEventPreviewFinishedListener {
        void showApiResponse(String apiResponse);
    }

    void makeApiRequest(String method, String endUrl, String jsonMessage);
}
