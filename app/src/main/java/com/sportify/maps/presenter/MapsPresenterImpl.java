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
    public void updateSearchResult(String textChange) {
        String searchInLowCase = textChange.toLowerCase();
        mapsView.clearMarkers();
        if(mapsView.placesIsChecked()){
            mapsRequest.updateCurrentSearchPlaces(searchInLowCase);
            showCurrentPlacesOnMap(searchInLowCase);
        }else if(mapsView.eventsIsChecked()){
            mapsRequest.updateCurrentSearchEvents(searchInLowCase);
            showCurrentEventsOnMap(searchInLowCase);
        }
    }

    @Override
    public void showCurrentPlacesOnMap(String search) {
        ArrayList<String> placesName = new ArrayList<>();
        for (Place p : mapsRequest.getCurrentSearchPlaces()) {
            if (p.getName().toLowerCase().startsWith(search)) {
                placesName.add(p.getName());
                mapsView.showPlaceMarkerAt(p.getName(), p.getCategory(), p.getLat(), p.getLon());
            }
        }
        mapsView.updatePlaceSearch(placesName);
    }

    @Override
    public void showCurrentEventsOnMap(String search) {
        ArrayList<String> eventNames = new ArrayList<>();
        for (Event e : mapsRequest.getCurrentSearchEvents()) {
                eventNames.add(e.getEventName());
                for (Place p : mapsRequest.getAllPlaces()) {
                    if (p.getName().equalsIgnoreCase(e.getPlaceName())) {
                        mapsView.showEventMarkerAt(e.getEventName(), e.getEventType(), p.getLat(), p.getLon());
                    }
                }
        }
        mapsView.updatePlaceSearch(eventNames);
    }

    @Override
    public void goFromListToMap(int id) {
        mapsView.hideSoftKeyboard();
        if(mapsView.placesIsChecked()){
            Place p = mapsRequest.getCurrentSearchPlaces().get(id);
            mapsView.switchToMapFragmentFromPresenter(p.getLat(), p.getLon());
            mapsView.setTextSearch(p.getName());
        }else{
            Event e = mapsRequest.getCurrentSearchEvents().get(id);
            for (Place p : mapsRequest.getAllPlaces()) {
                if (p.getName().equalsIgnoreCase(e.getPlaceName())) {
                    mapsView.switchToMapFragmentFromPresenter(p.getLat(), p.getLon());
                    mapsView.setTextSearch(e.getEventName());
                }
            }
        }

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
                mapsRequest.updateAllPlaces(params[0]);
                if (mapsView.getMap() != null){
                    showCurrentPlacesOnMap("");
                }
                break;

            case "updateEvents":
                mapsRequest.updateAllEvents(params[0]);
                break;
        }
    }
}
