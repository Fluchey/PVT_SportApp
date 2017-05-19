package com.sportify.placearea.presenter;

import com.sportify.storage.PlaceReview;

import java.util.ArrayList;

/**
 * Created by fluchey on 2017-05-18.
 */

public interface PlaceAreaPresenter {
    void getPlaceFromDb(String placeId);
    void updateReviews(int placeId);
    ArrayList<PlaceReview> getReviews();
    float getRatingAverage();
    int getNumberOfReviews();
}
