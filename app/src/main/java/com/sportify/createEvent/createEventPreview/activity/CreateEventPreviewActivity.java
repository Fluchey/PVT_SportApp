package com.sportify.createEvent.createEventPreview.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sportify.editEvent.activity.activity.EditEventActivity;
import com.sportify.userArea.activity.UserAreaActivity;

import sportapp.pvt_sportapp.R;

public class CreateEventPreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event_preview);
    }

    public void goToUserArea() {
        Intent goToUserAreaIntent = new Intent(CreateEventPreviewActivity.this, UserAreaActivity.class);
        CreateEventPreviewActivity.this.startActivity(goToUserAreaIntent);
    }
}
