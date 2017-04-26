package com.sportify.maps.presenter;

import com.sportify.maps.activity.MapsActivity;
import com.sportify.maps.activity.MapsView;

import javax.inject.Inject;

/**
 * Created by fluchey on 2017-04-26.
 */

public class MapsPresenterImpl implements MapsPresenter{
    @Inject
    MapsView mapsView;


    public MapsPresenterImpl() {
    }

    @Override
    public void mark(String eventName, double latitude, double longitude) {
        mapsView.removeMarkers();
        mapsView.showMarkerAt(eventName, latitude, longitude);
    }
}
