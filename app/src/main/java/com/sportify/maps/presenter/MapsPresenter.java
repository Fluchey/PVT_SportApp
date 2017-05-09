package com.sportify.maps.presenter;

/**
 * Created by fluchey on 2017-04-26.
 */

public interface MapsPresenter {
    void updateSearchResult(String textChange);

    void showCurrentPlacesOnMap(String searchString);

    void showCurrentEventsOnMap(String searchString);

    void goFromListToMap(int id);
}
