package com.sportify.maps.presenter;

import android.content.SharedPreferences;
import android.util.Log;

import com.sportify.maps.activity.MapsView;
import com.sportify.maps.request.MapsRequest;
import com.sportify.maps.request.MapsRequestImpl;
import com.sportify.storage.Event;
import com.sportify.storage.Place;

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
        this.token = sharedPreferences.getString("jwt", "");
        this.mapsRequest = new MapsRequestImpl(this, token);
        updatePlacesAndEvents();
    }

    private void updatePlacesAndEvents() {
        JSONObject json = new JSONObject();
        try {
            json.put("textChange", "Empty");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /**
         * First get all existing places
         */
        mapsRequest.makeApiRequestPut(json.toString(), "getallplaces", "PUT", "updatePlaces");

        /**
         * Then get all existing events
         */
        mapsRequest.makeApiRequestPut(json.toString(), "event/getAllEvents", "PUT", "updateEvents");
    }

    @Override
    public void updatePlaceSearch(String textChange) {
        JSONObject json = new JSONObject();

        try {
            json.put("textChange", textChange);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /**
         * First get all existing places
         */
        mapsRequest.makeApiRequestPut(json.toString(), "map/updateSearch", "PUT", "updatePlaces");

        /**
         * Then get all existing events
         */
        mapsRequest.makeApiRequestPut(json.toString(), "event/getAllEvents", "PUT", "updateEvents");
    }

    @Override
    public void showCurrentPlacesOnMap() {
        mapsView.clearMarkers();
        for (Place p : mapsRequest.getCurrentSearchPlaces()) {
            mapsView.showPlaceMarkerAt(p.getName(), p.getCategory(), p.getLat(), p.getLon());
        }
    }

    @Override
    public void showCurrentEventsOnMap() {
        mapsView.clearMarkers();
        for (Event e : mapsRequest.getEvents()) {
            Place place = null;
            for (Place p : mapsRequest.getCurrentSearchPlaces()){
                Log.d("PlaceName:", p.getName());
                Log.d("EventPlaceName:", e.getPlaceName());
                if (p.getName().equalsIgnoreCase(e.getPlaceName())){
                    place = p;
                }
            }
            mapsView.showEventMarkerAt(e.getEventName(), e.getEventType(), place.getLat(), place.getLon());
        }
    }

    @Override
    public void goFromListToMap(int id) {
        mapsView.hideSoftKeyboard();
        Place p = mapsRequest.getCurrentSearchPlaces().get(id);
        mapsView.switchToMapFragmentFromPresenter(p.getLat(), p.getLon());
        mapsView.setTextSearch(p.getName());
    }

    /**
     * @param command The command for the switch
     * @param params  params [0] = jsonMessage in String format
     *                params [1] = responseCode.. "200" or "300"
     *                params [2] = command for the switch
     *                OPTIONAL (params[0] = timeOut, if connection has timed out)
     */
    @Override
    public void showApiResponse(String command, String... params) {
        if (params[0] == null) {
            return;
        }
        switch (command) {
            case "updatePlaces":
                mapsRequest.updateCurrentPlaces(params[0]);
                if (mapsView.getMap() != null){
                    showCurrentPlacesOnMap();
                }
                break;

            case "updateEvents":
                mapsRequest.updateCurrentEvents(params[0]);
                if (mapsView.getMap() != null){
                    showCurrentEventsOnMap();
                }
                break;
        }
    }

    private void afterApiupdatePlacesOnMap() {
        if (mapsView.getTextSearch().isEmpty()) {
            mapsView.clearPlaces();
            mapsView.clearMarkers();
        } else {
            ArrayList<String> placesName = new ArrayList<>();
            for (Place p : mapsRequest.getCurrentSearchPlaces()) {
                placesName.add(p.getName());
            }
            mapsView.updatePlaceSearch(placesName);
        }
    }
}
