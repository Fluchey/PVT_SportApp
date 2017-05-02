package com.sportify.placeReview.request;

/**
 * Created by rasmu on 02/05/2017.
 */

public interface placeReviewRequest {
    interface onRequestFinishedListener{
        void showApiResponse(String... params);

        void closeLoadIndicator();

    }

    void makeApiRequest(String jsonMessage, String endURL);
}
