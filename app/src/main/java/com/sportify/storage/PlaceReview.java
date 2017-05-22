package com.sportify.storage;

public class PlaceReview {
    private int placeId;
    private String fullName;
    private String comment;
    private int profileId;
    private float rating;
    private String date;

    public PlaceReview(int placeId, String comment, int profileId, String name, float rating, String date) {
        this.placeId = placeId;
        this.comment = comment;
        this.profileId = profileId;
        this.rating = rating;
        this.date = date;
        fullName = name;
    }

    public int getPlaceId() {
        return placeId;
    }
    public void setPlaceId(int id) { this.placeId = id; }

    public int getProfileId(){return profileId;}
    public void setProfileId(){}

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getRating() {
        return rating;
    }
    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getName() { return fullName ;}
    public void setName(String name) {fullName = name;}

    public String getDate() { return date; }

    @Override
    public String toString() {
        return "Place: " + placeId + " Profile: " + profileId + " Rating: " + rating + " Comment: " + comment;
    }
}
