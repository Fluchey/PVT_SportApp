package com.sportify.storage;

public class PlaceReview {
    private int placeId;
    private String comment;
    private int profileId;
    private float rating;

    public PlaceReview(int placeId, String comment, int profileId, float rating) {
        this.placeId = placeId;
        this.comment = comment;
        this.profileId = profileId;
        this.rating = rating;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int id) {
        this.placeId = id;
    }

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

    @Override
    public String toString() {
        return "Place: " + placeId + " Profile: " + profileId + " Rating: " + rating + " Comment: " + comment;
    }
}
