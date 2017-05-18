package com.sportify.placearea.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.sportify.eventArea.presenter.EventAreaPresenterImpl;
import com.sportify.placearea.presenter.PlaceAreaPresenter;
import com.sportify.placearea.presenter.PlaceAreaPresenterImpl;

import java.util.ArrayList;

import sportapp.pvt_sportapp.R;

public class PlaceAreaActivity extends AppCompatActivity implements PlaceAreaView {
    private SharedPreferences sharedPref;
    private PlaceAreaPresenter presenter;
    private String placeId;

    private TextView placeName;
    private TextView interests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_review);

        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        presenter = new PlaceAreaPresenterImpl(this, sharedPref);

        Bundle bundle = getIntent().getExtras();
        placeId = (bundle.getString("placeId"));
        presenter.getPlaceFromDb(placeId);

        placeName = (TextView) findViewById(R.id.readReviewHeader);
        interests = (TextView) findViewById(R.id.interestsAreaText);
    }

    @Override
    public void setPlaceName(String placeName) {
        this.placeName.setText(placeName);
    }

    @Override
    public void setInterests(ArrayList<String> interests) {
        this.interests.setText(interests.toString());
    }
}
