package com.sportify.maps.presenter;

/**
 * Created by fluchey on 2017-04-26.
 */

public interface MapsPresenter {
    void mark(String eventName, String description, double latitude, double longitude);

    void markCategoriesOnMap(String jsonMessage);

    void getMarkersForCategory();

    void showPlaceByName();

    void updatePlaceSearch(String textChange);
}
