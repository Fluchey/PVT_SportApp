package com.sportify.maps.activity;

import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by fluchey on 2017-04-26.
 */

public interface MapsView {
    void clearMarkers();

    void showMarkerAt(String eventName, String description, double latitude, double longitude);

    void goToLocation(double lat, double lon, float zoom);

    String getCategory();

    void showLoadIndicator();

    void closeLoadIndicator();

    void updatePlaceSearch(ArrayList<String> places);

}
