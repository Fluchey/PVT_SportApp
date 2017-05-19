package com.sportify.placeReview.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android .view.View;

import com.sportify.maps.activity.MapsActivity;
import com.sportify.placeReview.presenter.PlaceReviewPresenter;
import com.sportify.placeReview.presenter.PlaceReviewPresenterImpl;
import com.sportify.placearea.activity.PlaceAreaActivity;

import sportapp.pvt_sportapp.R;

public class PlaceReviewActivity extends AppCompatActivity implements PlaceReviewView{
    private PlaceReviewPresenter placeReviewPresenter;

    private SharedPreferences sharedPref;
    private TextView header;
    private EditText reviewText;
    private RatingBar ratingBar;

    int userId;
    int placeId;
    String placeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_review);

        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        header = (TextView) findViewById(R.id.reviewHeader);
        reviewText = (EditText) findViewById(R.id.reviewText);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        Bundle b = getIntent().getExtras();
        if(b != null){
            placeName = b.getString("placeName", "Plats");
            header.setText(placeName);
            userId = b.getInt("userId", -1);
            placeId = b.getInt("placeId", -1);
        }

        placeReviewPresenter = new PlaceReviewPresenterImpl(this, sharedPref);

        placeReviewPresenter.showCurrentReview();
    }

    @Override
    public void submit(View v) { placeReviewPresenter.submitReview(userId, placeId); }

    @Override
    public int getUserId(){ return userId; }

    @Override
    public int getPlaceId(){ return placeId; }

    @Override
    public float getRating() {
        return ratingBar.getRating();
    }

    @Override
    public void setRating(float rating) {
        ratingBar.setRating(rating);
    }

    @Override
    public String getComment() {
        return reviewText.getText().toString();
    }

    @Override
    public void setComment(String comment){reviewText.setText(comment);}

    @Override
    public void returnToPlaceArea(){
        Intent goToPlaceAreaIntent = new Intent(PlaceReviewActivity.this, PlaceAreaActivity.class);
        goToPlaceAreaIntent.putExtra("placeId", "" + placeId);
        PlaceReviewActivity.this.startActivity(goToPlaceAreaIntent);
    }
}
