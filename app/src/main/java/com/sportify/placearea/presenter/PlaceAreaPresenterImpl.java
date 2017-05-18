package com.sportify.placearea.presenter;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;

import com.sportify.placearea.activity.PlaceAreaView;
import com.sportify.placearea.request.PlaceAreaRequest;
import com.sportify.placearea.request.PlaceAreaRequestImpl;

import org.json.JSONException;
import org.json.JSONObject;

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
}
