package com.sportify.readReview.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sportify.placeReview.activity.PlaceReviewActivity;
import com.sportify.settings.activity.SettingsActivity;
import com.sportify.settingsEditProfile.activity.EditProfileActivity;
import com.sportify.storage.PlaceReview;
import com.sportify.userArea.activity.UserAreaActivity;

import sportapp.pvt_sportapp.R;

public class ReadReviewActivity extends AppCompatActivity implements ReadReviewView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_review);
    }

    public void goToWriteReviewActivity(View v){
        Intent goToWriteReviewIntent = new Intent(ReadReviewActivity.this, PlaceReviewActivity.class);
        ReadReviewActivity.this.startActivity(goToWriteReviewIntent);
    }


    public void goToUserAreaFromRead(View v){
        Intent goToUserAreaIntent = new Intent(ReadReviewActivity.this, UserAreaActivity.class);
        ReadReviewActivity.this.startActivity(goToUserAreaIntent);
}
}
