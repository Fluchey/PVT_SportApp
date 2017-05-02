package com.sportify.storage;

import java.util.ArrayList;

/**
 * Created by antonfluch on 2017-05-02.
 */

public class PlaceStorage {
    private ArrayList<Place> places;

    public PlaceStorage(ArrayList<Place> places) {
        this.places = places;
    }

    public PlaceStorage(){
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }

    public void addPlaceToStorage(Place place){
        this.places.add(place);
    }

}
