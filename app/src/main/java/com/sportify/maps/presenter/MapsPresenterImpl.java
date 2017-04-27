package com.sportify.maps.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.sportify.maps.activity.MapsActivity;
import com.sportify.maps.activity.MapsView;
import com.sportify.maps.request.MapsRequest;
import com.sportify.maps.request.MapsRequestImpl;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

/**
 * Created by fluchey on 2017-04-26.
 */

public class MapsPresenterImpl implements MapsPresenter, MapsRequest.onRequestFinishedListener {
    private MapsView mapsView;

    private MapsRequest mapsRequest;

    private SharedPreferences share;

    private String token = "";

    public MapsPresenterImpl(MapsView mapsView, SharedPreferences sharedPreferences) {
        this.mapsView = mapsView;
        this.share = sharedPreferences;
        this.token = sharedPreferences.getString("Token", "");
        this.mapsRequest = new MapsRequestImpl(this, token);
    }

    @Override
    public void mark(String eventName, String description, double latitude, double longitude) {
        mapsView.removeMarkers();
        mapsView.showMarkerAt(eventName, description, latitude, longitude);
    }

    @Override
    public void markCategoriesOnMap(String responseBody) {
        Toast.makeText((Context) mapsView, responseBody, Toast.LENGTH_LONG).show();
    }

    @Override
    public void getMarkersForCategory() {
        String category = mapsView.getCategory();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("category", category);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mapsRequest.makeApiRequest(jsonObject.toString(), "showCategoryOnMap");
    }

    @Override
    public void showApiResponse(String... params) {
        Log.d("Params [0]", params[0]);
        Log.d("Params [1]", params[1]);
        switch (params[2]){
            case "Show category":
                markCategoriesOnMap(params[1]);
                break;
        }
    }
}
