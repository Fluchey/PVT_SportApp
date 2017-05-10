package com.sportify.placeReview.presenter;

import com.sportify.placeReview.activity.PlaceReviewView;

/**
 * Created by rasmu on 02/05/2017.
 */

public class PlaceReviewPresenterImpl implements PlaceReviewPresenter {
    PlaceReviewView placeReviewView;

    public PlaceReviewPresenterImpl(PlaceReviewView placeReviewView){
        this.placeReviewView = placeReviewView;
    }

    @Override
    public void submitReview() {
        placeReviewView.getRating();
    }
}
