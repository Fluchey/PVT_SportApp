package com.sportify.storage;

/**
 * Created by antonfluch on 2017-05-02.
 */

public class Place {
    private String name;
    private String category;
    private double lat;
    private double lon;

    public Place(String name, String category, double lat, double lon) {
        this.name = name;
        this.category = category;
        this.lat = lat;
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
        return "Name: " + name + " Category: " + category + " Lat: " + lat + " Lon: " + lon;
    }
}
