package com.sportify.placeReview.presenter;

import com.sportify.placeReview.activity.PlaceReviewView;
import com.sportify.placeReview.request.PlaceReviewRequest;
import com.sportify.placeReview.request.PlaceReviewRequestImpl;
import com.sportify.storage.PlaceReview;

/**
 * Created by rasmu on 02/05/2017.
 */

public class PlaceReviewPresenterImpl implements PlaceReviewPresenter, PlaceReviewRequest.onRequestFinishedListener {
    PlaceReviewView placeReviewView;
    PlaceReviewRequest placeReviewRequest;

    private String token = "";

    public PlaceReviewPresenterImpl(PlaceReviewView placeReviewView){
        this.placeReviewView = placeReviewView;
        placeReviewRequest = new PlaceReviewRequestImpl(this, token);
    }

    @Override
    public void showCurrentRating(int userId){
        for(PlaceReview review : placeReviewRequest.getAllReviews()){
            if(review.getProfileId() == userId){
                placeReviewView.setRating(review.getRating());
                return;
            }
        }
    }

    @Override
    public void submitReview() {
        placeReviewView.getRating();
        placeReviewRequest.submitReview();
    }

    @Override
    public void showApiResponse(String command, String... params) {
        if (params[0] == null) {
            return;
        }
        switch (command) {
            case "updatePlaceReviews":
                placeReviewRequest.updateAllReviews(params[0]);

                break;
        }
    }
}
