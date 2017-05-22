package com.sportify.placearea.presenter;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;

import com.sportify.placeReview.activity.PlaceReviewActivity;
import com.sportify.placeReview.activity.PlaceReviewView;
import com.sportify.placeReview.presenter.PlaceReviewPresenter;
import com.sportify.placeReview.presenter.PlaceReviewPresenterImpl;
import com.sportify.placearea.activity.PlaceAreaView;
import com.sportify.placearea.request.PlaceAreaRequest;
import com.sportify.placearea.request.PlaceAreaRequestImpl;
import com.sportify.storage.PlaceReview;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by fluchey on 2017-05-18.
 */

public class PlaceAreaPresenterImpl implements PlaceAreaPresenter, PlaceAreaRequest.OnRequestFinishedListener {
    private final String token;
    private PlaceAreaView activity;
    private PlaceAreaRequest request;
    private SharedPreferences sharedPref;


    public PlaceAreaPresenterImpl(PlaceAreaView placeAreaActivity, SharedPreferences sharedPref) {
        this.activity = placeAreaActivity;
        this.sharedPref = sharedPref;
        this.token = sharedPref.getString("jwt", "");
        this.request = new PlaceAreaRequestImpl(this, token);
    }

    @Override
    public void showApiResponse(String command, String[] result) {
        switch (command){
            case "getplacebyid":
                request.loadPlaceData(result[0]);
                presentDataOnView();
                break;
            case "updateReviews":
                request.updateAllReviews(result[0]);
                activity.setReviewAverage();
                activity.showReviews(request.getAllReviews());
                break;
        }

    }

    private void presentDataOnView() {
        activity.setPlaceName(request.getPlace().getName());
        activity.setInterests(request.getPlace().getCategories());
    }

    @Override
    public void getPlaceFromDb(String placeId) {
        JSONObject json = new JSONObject();
        try {
            json.put("placeId", placeId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.makeApiRequestPut(json.toString(), "getplacebyid", "PUT", "getplacebyid");
    }

    @Override
    public void updateReviews(int placeId){
        request.renewAllReviews(placeId);
    }

    @Override
    public ArrayList<PlaceReview> getReviews(){
        ArrayList<PlaceReview> reviews = request.getAllReviews();
        return reviews;
    }

    @Override
    public float getRatingAverage(){
        int amount = 0;
        float total = 0;
        for (PlaceReview review : getReviews()){
            amount++;
            total += review.getRating();
        }
        return ((float)(total/amount));
    }

    @Override
    public int getNumberOfReviews(){
        int amount = 0;
        for (PlaceReview review : getReviews()){
            amount++;
        }
        return amount;
    }
}
