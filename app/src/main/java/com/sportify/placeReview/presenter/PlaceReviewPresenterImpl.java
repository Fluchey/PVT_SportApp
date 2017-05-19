package com.sportify.placeReview.presenter;

import android.content.SharedPreferences;
import android.util.Log;

import com.sportify.placeReview.activity.PlaceReviewView;
import com.sportify.placeReview.request.PlaceReviewRequest;
import com.sportify.placeReview.request.PlaceReviewRequestImpl;
import com.sportify.storage.PlaceReview;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rasmu on 02/05/2017.
 */

public class PlaceReviewPresenterImpl implements PlaceReviewPresenter, PlaceReviewRequest.onRequestFinishedListener {
    PlaceReviewView placeReviewView;
    PlaceReviewRequest placeReviewRequest;
    SharedPreferences sharedPref;

    private String token = "";

    public PlaceReviewPresenterImpl(PlaceReviewView placeReviewView, SharedPreferences sharedPref){
        this.placeReviewView = placeReviewView;
        this.sharedPref = sharedPref;
        this.token = sharedPref.getString("jwt", "");
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
    public void submitReview(int userId, int placeId) {
        placeReviewRequest.submitReview((double)placeReviewView.getRating(),placeReviewView.getComment(), userId, placeId);
    }

    @Override
    public void showApiResponse(String command, String... params) {
        if (params[0] == null) {
            return;
        }
        switch (command) {
            case "updatePlaceReviews":
//                Log.d("GET-request", command + " PARAM0: " + params[0]);
                break;
            case "addReview":
//                Log.d("PUT-request", command + " PARAM0: " + params[0]);
                break;
        }
    }
}
