package com.sportify.maps.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.sportify.maps.activity.MapsView;
import com.sportify.maps.request.MapsRequest;
import com.sportify.maps.request.MapsRequestImpl;

import org.json.JSONException;
import org.json.JSONObject;

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
        mapsView.clearMarkers();
        mapsView.showMarkerAt(eventName, description, latitude, longitude);
    }



    @Override
    public void markCategoriesOnMap(String jsonMessage) {
        mapsView.clearMarkers();
        mapsView.showMarkerAt("Eriksdalsbadet", "Simhall", 59.30449679409284, 18.07552995325442);
//        Log.d("params[3]", jsonMessage);
//        Toast.makeText((Context) mapsView, responseBody, Toast.LENGTH_LONG).show();
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


    /**
     * @param params
     * params [0] = responseCode.. "200" or "300"
     * params [1] = jsonMessage in String format
     * params [2] = command for the switch
     */
    @Override
    public void showApiResponse(String... params) {
        Log.d("Params [0]", params[0]);
        Log.d("Params [1]", params[1]);
        Log.d("Params [2]", params[2]);
        switch (params[2]){
            case "Show category":
                markCategoriesOnMap(params[0]);
                break;
        }
    }
}
