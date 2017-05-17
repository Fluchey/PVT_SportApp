package com.sportify.createEvent.createEventPageBeforeInviteFriends.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sportify.createEvent.createEventInviteFriends.activity.CreateEventInviteFriendsActivity;
import com.sportify.createEvent.createEventPreview.activity.CreateEventPreviewActivity;

import sportapp.pvt_sportapp.R;

public class CreateEventBeforeInviteFriendsActivity extends AppCompatActivity {

    private int eventID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event_before_invite_friends);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            eventID = extras.getInt("EVENT_ID");
        }
    }

    public void goToPreviewActivity(View v){
        Intent goToPreviewActivityIntent = new Intent(CreateEventBeforeInviteFriendsActivity.this, CreateEventPreviewActivity.class);
        CreateEventBeforeInviteFriendsActivity.this.startActivity(goToPreviewActivityIntent);
    }

    public void goToInviteFriendsActivity(View v){
        Intent goToInviteFriendsActivity = new Intent(CreateEventBeforeInviteFriendsActivity.this, CreateEventInviteFriendsActivity.class);
        CreateEventBeforeInviteFriendsActivity.this.startActivity(goToInviteFriendsActivity);

        goToInviteFriendsActivity.putExtra("EVENT_ID", eventID);
        CreateEventBeforeInviteFriendsActivity.this.startActivity(goToInviteFriendsActivity);
    }
}
