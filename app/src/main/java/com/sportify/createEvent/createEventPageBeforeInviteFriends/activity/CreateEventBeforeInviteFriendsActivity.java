package com.sportify.createEvent.createEventPageBeforeInviteFriends.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sportify.createEvent.createEventInviteFriends.activity.CreateEventInviteFriendsActivity;
import com.sportify.createEvent.createEventPageOne.activity.CreateEventPageOneActivity;
import com.sportify.createEvent.createEventPreview.activity.CreateEventPreviewActivity;

import sportapp.pvt_sportapp.R;

public class CreateEventBeforeInviteFriendsActivity extends AppCompatActivity {

    private int eventID;
    private String eventName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event_before_invite_friends);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            eventID = extras.getInt("EVENT_ID");
            eventName = extras.getString("EVENT_NAME");
        }
    }

    public void goToPreviewActivity(View v){
        Intent goToPreviewActivityIntent = new Intent(CreateEventBeforeInviteFriendsActivity.this, CreateEventPreviewActivity.class);
        goToPreviewActivityIntent.putExtra("EVENT_ID", eventID);
        goToPreviewActivityIntent.putExtra("EVENT_NAME", eventName);
        CreateEventBeforeInviteFriendsActivity.this.startActivity(goToPreviewActivityIntent);
    }

    public void goToInviteFriendsActivity(View v){
        Intent goToInviteFriendsActivity = new Intent(CreateEventBeforeInviteFriendsActivity.this, CreateEventInviteFriendsActivity.class);
        CreateEventBeforeInviteFriendsActivity.this.startActivity(goToInviteFriendsActivity);

        goToInviteFriendsActivity.putExtra("EVENT_ID", eventID);
        goToInviteFriendsActivity.putExtra("EVENT_NAME", eventName);
        CreateEventBeforeInviteFriendsActivity.this.startActivity(goToInviteFriendsActivity);
    }

    public void goToCreateEventFromAdd(View v){
        Intent goToCreateActivity = new Intent(CreateEventBeforeInviteFriendsActivity.this, CreateEventPageOneActivity.class);
        CreateEventBeforeInviteFriendsActivity.this.startActivity(goToCreateActivity);

        goToCreateActivity.putExtra("EVENT_ID", eventID);
        CreateEventBeforeInviteFriendsActivity.this.startActivity(goToCreateActivity);
    }
}
