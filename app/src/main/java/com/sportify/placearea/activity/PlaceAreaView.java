package com.sportify.placearea.activity;

import android.view.View;

import com.sportify.arrayAdapters.MyArrayAdapterShowPlaceReviews;
import com.sportify.storage.Place;
import com.sportify.storage.PlaceReview;

import java.util.ArrayList;

/**
 * Created by fluchey on 2017-05-18.
 */

public interface PlaceAreaView {
    void setPlaceName(String placeName);
    void setInterests(ArrayList<String> interests);
    void setReviewAverage();
    void goToWriteReviewActivity(View v);
    void goToUserAreaFromRead(View v);
    void showReviews(ArrayList<PlaceReview> reviews);
}
