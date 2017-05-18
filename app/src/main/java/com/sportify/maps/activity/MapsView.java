package com.sportify.maps.activity;

import android.widget.EditText;

import com.google.android.gms.maps.GoogleMap;
import com.sportify.storage.Event;
import com.sportify.storage.Place;

import java.util.ArrayList;

/**
 * Created by fluchey on 2017-04-26.
 */

public interface MapsView {
    String getTextSearch();

    void setTextSearch(String text);

    void clearMarkers();

    void showPlaceMarkerAt(Place p);

    void showEventMarkerAt(Event e, Place p);

    void goToLocation(double lat, double lon, float zoom);

    void updatePlaceSearch(ArrayList<String> places);

    void clearPlaces();

    void switchToMapFragmentFromPresenter(double lat, double lon);

    void hideSoftKeyboard();

    boolean placesIsChecked();

    boolean eventsIsChecked();

    GoogleMap getMap();

}
