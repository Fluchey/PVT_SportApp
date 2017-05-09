package com.sportify.maps.presenter;

/**
 * Created by fluchey on 2017-04-26.
 */

public interface MapsPresenter {
    void updatePlaceSearch(String textChange);

    void showCurrentPlacesOnMap();

    void showCurrentEventsOnMap();

    void goFromListToMap(int id);
}
