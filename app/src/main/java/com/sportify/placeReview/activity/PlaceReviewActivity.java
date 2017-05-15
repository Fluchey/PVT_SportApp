package com.sportify.placeReview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android .view.View;

import com.sportify.placeReview.presenter.PlaceReviewPresenter;
import com.sportify.placeReview.presenter.PlaceReviewPresenterImpl;

import sportapp.pvt_sportapp.R;

public class PlaceReviewActivity extends AppCompatActivity implements PlaceReviewView{
    PlaceReviewPresenter placeReviewPresenter;

    TextView placeName;
    EditText reviewText;
    RatingBar ratingBar;

    int userId;
    String placeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_review);

        Bundle b = getIntent().getExtras();
        String place = "";
        if(b != null){
            place = b.getString("place");
        }
        placeName = (TextView) findViewById(R.id.reviewHeader);
        placeName.setText(place);
        reviewText = (EditText) findViewById(R.id.reviewText);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        userId = b.getInt("userID", -1);

        placeReviewPresenter = new PlaceReviewPresenterImpl(this);
    }

    @Override
    public void submit(View v) { placeReviewPresenter.submitReview(getRating(), getComment(), userId, placeId); }

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
}
