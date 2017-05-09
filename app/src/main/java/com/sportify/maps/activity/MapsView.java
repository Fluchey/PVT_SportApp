package com.sportify.maps.activity;

import android.widget.EditText;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;

/**
 * Created by fluchey on 2017-04-26.
 */

public interface MapsView {
    String getTextSearch();

    void setTextSearch(String text);

    void clearMarkers();

    void showPlaceMarkerAt(String placeName, String description, double latitude, double longitude);

    void showEventMarkerAt(String eventName, String category, double latitude, double longitude);

    void goToLocation(double lat, double lon, float zoom);

    void updatePlaceSearch(ArrayList<String> places);

    void clearPlaces();

    void switchToMapFragmentFromPresenter(double lat, double lon);

    void hideSoftKeyboard();

    boolean placesIsChecked();

    boolean eventsIsChecked();

    GoogleMap getMap();

}
