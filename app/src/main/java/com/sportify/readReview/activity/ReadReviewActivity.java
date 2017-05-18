package com.sportify.readReview.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sportify.placeReview.activity.PlaceReviewActivity;
import com.sportify.userArea.activity.UserAreaActivity;

import sportapp.pvt_sportapp.R;

public class ReadReviewActivity extends AppCompatActivity implements ReadReviewView{
    TextView header;
    String place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_review);

        header = (TextView) findViewById(R.id.readReviewHeader);

        Bundle b = getIntent().getExtras();
        if(b != null){
            place = b.getString("placeId");
            header.setText(place);
        }
    }
}
