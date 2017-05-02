package com.sportify.placeReview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import sportapp.pvt_sportapp.R;

public class placeReviewActivity extends AppCompatActivity {
    EditText placeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_review);

        Bundle b = getIntent().getExtras();
        String place = "";
        if(b != null){
            place = b.getString("place");
        }
        placeName = (EditText) findViewById(R.id.placeName);
        placeName.setText(place);
    }
}
