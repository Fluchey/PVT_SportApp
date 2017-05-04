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
        this.token = sharedPreferences.getString("jwt", "");
        this.mapsRequest = new MapsRequestImpl(this, token);
        mapsRequest.makeApiRequestGet("GET", "getallplaces", "getAllPlaces");
    }

    @Override
    public void mark(String eventName, String description, double latitude, double longitude) {
        mapsView.clearMarkers();
        mapsView.showMarkerAt(eventName, description, latitude, longitude);
    }


    @Override
    public void markCategoriesOnMap(String jsonMessage) {
        mapsView.clearMarkers();
        JSONObject json = null;
        JSONArray array = null;
        try {
            json = new JSONObject(jsonMessage);
            array = json.getJSONArray("instance");
            Log.d("JsonArr: ", array.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (json == null || array == null) {
            return;
        }


        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                mapsView.showMarkerAt(json.getString("category"), jsonObject.getString("name"), Double.parseDouble(jsonObject.getString("lat")), Double.parseDouble(jsonObject.getString("lon")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


//        Log.d("Json:", json.toString());

//        Log.d("params[3]", jsonMessage);
//        Toast.makeText((Context) mapsView, responseBody, Toast.LENGTH_LONG).show();
    }

    @Override
    public void getMarkersForCategory() {
        mapsView.clearMarkers();
        JSONObject json = new JSONObject();

        try {
            json.put("category", mapsView.getCategory());
        }catch (JSONException e){
            e.printStackTrace();
        }
        mapsRequest.makeApiRequestPut(json.toString(), "showCategoryOnMap", "PUT", "Show category");
//        for (Place p: mapsRequest.getPlaces()){
//            if(p.getCategory().equals(mapsView.getCategory())){
//                mapsView.showMarkerAt(p.getName(), p.getCategory(), p.getLat(), p.getLon());
//            }
//        }
    }

    @Override
    public void showPlaceByName() {
        mapsView.clearMarkers();
        for (Place p: mapsRequest.getPlaces()){
            if(p.getName().equals(mapsView.getPlaceName())){
                mapsView.showMarkerAt(p.getName(), p.getCategory(), p.getLat(), p.getLon());
                mapsView.goToLocation(p.getLat(), p.getLon(), 15);
            }
        }
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
    public void closeLoadIndicator() {
        mapsView.closeLoadIndicator();
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
            case "Show category":
                markCategoriesOnMap(params[0]);
                break;

            case "getAllPlaces":
                mapsRequest.updateAllPlaces(params[0]);
                mapsView.updatePlaceSearch(mapsRequest.getPlacesName());
                break;

            case "updateSearch":
                mapsRequest.updateCurrentSearch(params[0]);
                mapsView.updatePlaceSearch(mapsRequest.getPlacesName());
                Log.d("Ja", "JA");
                break;
        }
    }
}
