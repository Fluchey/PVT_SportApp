package com.sportify.maps.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.sportify.maps.activity.MapsView;
import com.sportify.maps.request.MapsRequest;
import com.sportify.maps.request.MapsRequestImpl;
import com.sportify.storage.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
        mapsRequest.makeApiRequestGet("GET", "getallplaces", "getAllPlaces");
    }

    @Override
    public void updatePlaceSearch(String textChange) {
        JSONObject json = new JSONObject();

        try {
            json.put("textChange", textChange);
        }catch (JSONException e){
            e.printStackTrace();
        }
        mapsRequest.makeApiRequestPut(json.toString(), "map/updateSearch", "PUT", "updateSearch");
    }

    @Override
    public void showCurrentPlacesOnMap() {
        mapsView.clearMarkers();
        for (Place p: mapsRequest.getCurrentSearchPlaces()) {
            mapsView.showMarkerAt(p.getName(), p.getCategory(), p.getLat(), p.getLon());
        }
    }

    /**
     * @param command The command for the switch
     * @param params params [0] = jsonMessage in String format
     *               params [1] = responseCode.. "200" or "300"
     *               params [2] = command for the switch
     *               OPTIONAL (params[0] = timeOut, if connection has timed out)
     */
    @Override
    public void showApiResponse(String command, String... params) {
        if (params[0] == null) {
            return;
        }
        Log.d("Command", command);
        Log.d("Params [0]", params[0]);
//        Log.d("Params [1]", params[1]);
//        Log.d("Params [2]", params[2]);
        switch (command) {
            case "updateSearch":
                mapsRequest.updateCurrentSearchPlaces(params[0]);
                afterApiupdatePlaceSearch();
                Log.d("Ja", "JA");
                break;
        }
    }

    private void afterApiupdatePlaceSearch() {
        if(mapsRequest.getCurrentSearchPlaces().isEmpty()){
            mapsView.clearPlaces();
        }else {
            ArrayList<String> placesName = new ArrayList<>();
            for (Place p : mapsRequest.getCurrentSearchPlaces()) {
                placesName.add(p.getName());
            }
            mapsView.updatePlaceSearch(placesName);
            showCurrentPlacesOnMap();
        }
    }
}
