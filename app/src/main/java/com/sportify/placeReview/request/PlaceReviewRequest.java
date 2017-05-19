package com.sportify.placeReview.request;

import com.sportify.storage.PlaceReview;

import java.util.ArrayList;

/**
 * Created by rasmu on 02/05/2017.
 */

public interface PlaceReviewRequest {
    interface onRequestFinishedListener{
        void showApiResponse(String command, String... params);
    }

    void updateAllReviews(String jsonMessage);

    void makeApiRequestPut(String jsonMessage, String endURL, String method, String command);

    void makeApiRequestGet(String method, String endURL, String command);

    void renewAllReviews();
    void renewAllReviews(int placeId, int userId);
    ArrayList<PlaceReview> getAllReviews();

    void submitReview(double rating, String comment, int userId, int placeId);
    void updateReview(double rating, String comment, int userId, int placeId);
}
