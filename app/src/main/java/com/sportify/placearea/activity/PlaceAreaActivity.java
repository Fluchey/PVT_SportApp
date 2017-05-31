package com.sportify.placearea.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import com.sportify.arrayAdapters.MyArrayAdapterShowFriends;
import com.sportify.arrayAdapters.MyArrayAdapterShowPlaceReviews;
import com.sportify.createEvent.createEventPageOne.activity.CreateEventPageOneActivity;
import com.sportify.maps.activity.MapsActivity;
import com.sportify.notifications.activity.NotificationActivity;
import com.sportify.placeReview.activity.PlaceReviewActivity;
import com.sportify.placearea.presenter.PlaceAreaPresenter;
import com.sportify.placearea.presenter.PlaceAreaPresenterImpl;
import com.sportify.showFriends.Profile;
import com.sportify.showFriends.activity.ShowFriendsActivity;
import com.sportify.storage.PlaceReview;
import com.sportify.userArea.activity.UserAreaActivity;

import java.sql.Date;
import java.util.ArrayList;

import sportapp.pvt_sportapp.R;

public class PlaceAreaActivity extends AppCompatActivity implements PlaceAreaView {
    private SharedPreferences sharedPref;
    private PlaceAreaPresenter presenter;
    private String placeId;

    private TextView placeName;
    private TextView interests;
    private TextView numberOfReviews;

    private RatingBar averageRating;

    private ListView reviewsList;
    private ArrayList<PlaceReview> allReviews;

    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_area);

        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        presenter = new PlaceAreaPresenterImpl(this, sharedPref);

        Bundle bundle = getIntent().getExtras();
        placeId = (bundle.getString("placeId"));
        presenter.getPlaceFromDb(placeId);
        presenter.updateReviews(Integer.parseInt(placeId));

        placeName = (TextView) findViewById(R.id.readReviewHeader);
        interests = (TextView) findViewById(R.id.interestsAreaText);
        numberOfReviews = (TextView) findViewById(R.id.numberOfReviews);
        averageRating = (RatingBar) findViewById(R.id.staticRatingBar);

        reviewsList = (ListView) findViewById(R.id.placereviewsListView);
        arrayAdapter = new MyArrayAdapterShowPlaceReviews(this, R.layout.placereview_list_item, null);
    }

    @Override
    public void setPlaceName(String placeName) {
        this.placeName.setText(placeName);
    }

    @Override
    public void setInterests(ArrayList<String> interests) {
        this.interests.setText(interests.toString());
    }

    @Override
    public void setReviewAverage(){
        float average = presenter.getRatingAverage();
        int amount = presenter.getNumberOfReviews();

        numberOfReviews.setText("Recensioner (" + amount + ")");
        averageRating.setRating(average);
    }

    @Override
    public void showReviews(ArrayList<PlaceReview> reviews) {
        allReviews = reviews;
        arrayAdapter = new MyArrayAdapterShowPlaceReviews(this, R.layout.placereview_list_item, allReviews);
        reviewsList.setAdapter(arrayAdapter);
    }



    @Override
    public void goToWriteReviewActivity(View v){
        Intent goToWriteReviewIntent = new Intent(PlaceAreaActivity.this, PlaceReviewActivity.class);

        Bundle b = new Bundle();
        b.putString("placeName", (String)placeName.getText());
        b.putInt("placeId", Integer.parseInt(placeId));
        b.putInt("userId", sharedPref.getInt("profileID", -1));
        goToWriteReviewIntent.putExtras(b);

        PlaceAreaActivity.this.startActivity(goToWriteReviewIntent);
    }

    @Override
    public void goToUserAreaFromRead(View v){
        Intent goToUserAreaIntent = new Intent(PlaceAreaActivity.this, UserAreaActivity.class);
        PlaceAreaActivity.this.startActivity(goToUserAreaIntent);
    }

    public void createEventHere(View view) {
        Intent createEventIntent = new Intent(PlaceAreaActivity.this, CreateEventPageOneActivity.class);
        createEventIntent.putExtra("placeId", placeId);
        PlaceAreaActivity.this.startActivity(createEventIntent);
    }

    public void ToNoteActivityFromPA(View v) {
        Intent goToNotificationsViewIntent = new Intent(PlaceAreaActivity.this, NotificationActivity.class);
        PlaceAreaActivity.this.startActivity(goToNotificationsViewIntent);
    }

    public void createEventButtonClickFromPA(View v) {
        Intent createEventIntent = new Intent(PlaceAreaActivity.this, CreateEventPageOneActivity.class);
        PlaceAreaActivity.this.startActivity(createEventIntent);
    }

    public void startMapActivityFromPA(View view) {
        Intent startMapActivityIntent = new Intent(PlaceAreaActivity.this, MapsActivity.class);
        PlaceAreaActivity.this.startActivity(startMapActivityIntent);
    }

    public void goToUserAreaActivityFromPA(View v) {
        Intent goToUserAreaViewIntent = new Intent(PlaceAreaActivity.this, UserAreaActivity.class);
        PlaceAreaActivity.this.startActivity(goToUserAreaViewIntent);
    }

    public void goToFriendListButtonClickFromPA(View v) {
        Intent goToFriendListIntent = new Intent(PlaceAreaActivity.this, ShowFriendsActivity.class);
        PlaceAreaActivity.this.startActivity(goToFriendListIntent);
    }

}
