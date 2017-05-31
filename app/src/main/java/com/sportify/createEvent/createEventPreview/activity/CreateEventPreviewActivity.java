package com.sportify.createEvent.createEventPreview.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sportify.arrayAdapters.MyArrayAdapterShowFriends;
import com.sportify.createEvent.createEventPreview.presenter.CreateEventPreviewPresenter;
import com.sportify.createEvent.createEventPreview.presenter.CreateEventPreviewPresenterImpl;
import com.sportify.userArea.activity.UserAreaActivity;
import com.sportify.util.Profile;

import java.util.ArrayList;

import sportapp.pvt_sportapp.R;

public class CreateEventPreviewActivity extends AppCompatActivity implements CreateEventPreviewView{

    CreateEventPreviewPresenter previewPresenter;
    SharedPreferences sharedPref;
    private int eventID;
    private String eventName = "";
    private String eventDescription;
    private ListView invitedFriendsLv;
    private MyArrayAdapterShowFriends myArrayAdapterShowFriends;


    private TextView eventNameTv;
    private TextView eventDescriptionTv;
    private ImageView eventImage;
    private ImageView userProfilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event_preview);

        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        previewPresenter = new CreateEventPreviewPresenterImpl(this, sharedPref);

        eventNameTv = (TextView) findViewById(R.id.tvEventName);
        eventDescriptionTv = (TextView) findViewById(R.id.tvEventDescriptionPreview);
        eventImage = (ImageView) findViewById(R.id.ivEventPicture);
        invitedFriendsLv = (ListView) findViewById(R.id.lvCreateEventFriends);

        ArrayList<com.sportify.showFriends.Profile> invitedFriends = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        String eventImageBase64 = "";
        if(extras != null){
            eventID = extras.getInt("EVENT_ID");
            eventName = extras.getString("EVENT_NAME");
            eventImageBase64 = extras.getString("imageBase64");
            eventDescription = extras.getString("eventDescription");
            invitedFriends = (ArrayList<com.sportify.showFriends.Profile>) getIntent().getSerializableExtra("invitedFriends");
        }

        if(invitedFriends != null) {
            myArrayAdapterShowFriends = new MyArrayAdapterShowFriends(CreateEventPreviewActivity.this, R.layout.create_event_friend_list_item, invitedFriends);
            invitedFriendsLv.setAdapter(myArrayAdapterShowFriends);
        }

        eventNameTv.setText("Eventnamn: " + eventName);
        setEventImage(eventImageBase64);
        eventDescriptionTv.setText("Beskrivning: " + eventDescription);

        String imageBase64 = sharedPref.getString("imageBase64", "");
        userProfilePicture = (ImageView) findViewById(R.id.ivProfilePicture);
        if (!imageBase64.isEmpty()) {
            Bitmap bitmap = Profile.decodeStringToBitmap(imageBase64);
            userProfilePicture.setImageBitmap(bitmap);
        }
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

    private void setEventImage(String imageBase64){
        if(!imageBase64.isEmpty()){
            Bitmap image = Profile.decodeStringToBitmap(imageBase64);
            eventImage.setImageBitmap(image);
        }else{
            eventImage.setImageResource(R.drawable.defaultevent1);
        }
    }
}
