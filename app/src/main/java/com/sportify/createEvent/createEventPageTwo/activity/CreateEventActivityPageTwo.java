package com.sportify.createEvent.createEventPageTwo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sportify.createEvent.createEventPageFive.CreateEventPreview;
import com.sportify.createEvent.createEventPageThree.activity.CreateEventInviteFriendsActivity;
import com.sportify.mainPage.activity.MainPageActivity;
import com.sportify.register.activity.RegisterActivity;

import sportapp.pvt_sportapp.R;

public class CreateEventActivityPageTwo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event2);
    }

    public void goToPreviewActivity(View v){
        Intent goToPreviewActivityIntent = new Intent(CreateEventActivityPageTwo.this, CreateEventPreview.class);
        CreateEventActivityPageTwo.this.startActivity(goToPreviewActivityIntent);
    }
}
