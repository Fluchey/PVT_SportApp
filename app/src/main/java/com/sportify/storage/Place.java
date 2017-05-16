package com.sportify.storage;

import java.util.ArrayList;

/**
 * Created by antonfluch on 2017-05-02.
 */

public class Place {
    private String id;
    private String name;
    private ArrayList<String> categories;
    private double lat;
    private double lon;

    public Place(String id, String name, ArrayList<String> categories, double lat, double lon) {
        this.id = id;
        this.name = name;
        this.categories = categories;
        this.lat = lat;
        this.lon = lon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "Name: " + name + " Category: " + categories.toString() + " Lat: " + lat + " Lon: " + lon;
    }
}
