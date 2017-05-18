package com.sportify.placeReview.activity;

import android.view.View;

/**
 * Created by rasmu on 02/05/2017.
 */

public interface PlaceReviewView {

    void submit(View v);

    float getRating();
    void setRating(float rating);
    String getComment();
    void setComment(String comment);
}
