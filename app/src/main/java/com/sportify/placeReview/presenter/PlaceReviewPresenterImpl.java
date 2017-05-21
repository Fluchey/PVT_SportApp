package com.sportify.placeReview.presenter;

import android.content.SharedPreferences;
import android.util.Log;

import com.sportify.placeReview.activity.PlaceReviewView;
import com.sportify.placeReview.request.PlaceReviewRequest;
import com.sportify.placeReview.request.PlaceReviewRequestImpl;
import com.sportify.storage.Place;
import com.sportify.storage.PlaceReview;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by rasmu on 02/05/2017.
 */

public class PlaceReviewPresenterImpl implements PlaceReviewPresenter, PlaceReviewRequest.onRequestFinishedListener {
    PlaceReviewView placeReviewView;
    PlaceReviewRequest placeReviewRequest;
    SharedPreferences sharedPref;

    private String token = "";
    private boolean update;

    public PlaceReviewPresenterImpl(PlaceReviewView placeReviewView, SharedPreferences sharedPref){
        this.placeReviewView = placeReviewView;
        this.sharedPref = sharedPref;
        this.token = sharedPref.getString("jwt", "");
        placeReviewRequest = new PlaceReviewRequestImpl(this, token);
    }

    @Override
    public void showCurrentReview(){
        int userId = placeReviewView.getUserId();
        int placeId = placeReviewView.getPlaceId();
        placeReviewRequest.renewAllReviews(userId, placeId);
    }

    @Override
    public void setCurrentReview(){
        int userId = placeReviewView.getUserId();
        ArrayList<PlaceReview> reviews = placeReviewRequest.getAllReviews();
        for(PlaceReview review : reviews){
            if(review.getProfileId() == userId){
                placeReviewView.setRating(review.getRating());
                placeReviewView.setComment(review.getComment());
                update = true;
                return;
            }
        }
    }

    @Override
    public void submitReview(int userId, int placeId) {
        if(!update)
            placeReviewRequest.submitReview((double)placeReviewView.getRating(),placeReviewView.getComment(), userId, placeId);
        else
            placeReviewRequest.updateReview((double)placeReviewView.getRating(),placeReviewView.getComment(), userId, placeId);
    }

    @Override
    public void showApiResponse(String command, String... params) {
        if (params[0] == null) {
            return;
        }
        switch (command) {
            case "updateReviews":
                placeReviewRequest.updateAllReviews(params[0]);
                ArrayList<PlaceReview> reviews = placeReviewRequest.getAllReviews();
                setCurrentReview();
                break;
            case "addReview":
                if(params[0].contains("The review is created"))
                    placeReviewView.returnToPlaceArea();
                break;
        }
    }
}
