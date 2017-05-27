package com.sportify.createEvent.createEventPreview.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sportify.createEvent.createEventPreview.presenter.CreateEventPreviewPresenter;
import com.sportify.createEvent.createEventPreview.presenter.CreateEventPreviewPresenterImpl;
import com.sportify.userArea.activity.UserAreaActivity;

import sportapp.pvt_sportapp.R;

public class CreateEventPreviewActivity extends AppCompatActivity implements CreateEventPreviewView{

    CreateEventPreviewPresenter previewPresenter;
    SharedPreferences sharedPref;
    private int eventID;
    private String eventName = "";

    private TextView eventNameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event_preview);

        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        previewPresenter = new CreateEventPreviewPresenterImpl(this, sharedPref);

        eventNameTv = (TextView) findViewById(R.id.tvEventName);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            eventID = extras.getInt("EVENT_ID");
            eventName = extras.getString("EVENT_NAME");
        }

        eventNameTv.setText(eventName);
    }

    @Override
    public void goToUserArea(String message) {
        showToastToUser(message);
        Intent goToUserAreaIntent = new Intent(CreateEventPreviewActivity.this, UserAreaActivity.class);
        CreateEventPreviewActivity.this.startActivity(goToUserAreaIntent);
    }

    public void deleteEvent(View v){
        previewPresenter.deleteEvent(eventID);
    }

    public void createEvent(View v){
        goToUserArea("Eventet är nu skapat");
    }

    @Override
    public void showToastToUser(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
