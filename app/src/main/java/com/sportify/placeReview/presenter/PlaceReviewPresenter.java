package com.sportify.placeReview.presenter;

import com.sportify.placeReview.activity.PlaceReviewView;

/**
 * Created by rasmu on 02/05/2017.
 */

public interface PlaceReviewPresenter {
    void showCurrentRating(int userId);
    void submitReview(float rating, String comment, int userId, String placeId);
}
