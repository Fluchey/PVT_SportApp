package com.sportify.maps.activity;

/**
 * Created by fluchey on 2017-04-26.
 */

public interface MapsView {
    void removeMarkers();

    void showMarkerAt(String eventName, double latitude, double longitude);
}
