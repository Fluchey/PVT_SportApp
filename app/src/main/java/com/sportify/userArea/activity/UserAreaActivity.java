package com.sportify.userArea.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.sportify.calendar.activity.CalendarActivity;
import com.sportify.createEvent.createEventPageOne.activity.CreateEventPageOnePageOneActivity;
import com.sportify.eventArea.activity.EventAreaActivity;
import com.sportify.maps.activity.MapsActivity;
import com.sportify.notifications.activity.NotificationsActivity;
import com.sportify.readReview.activity.ReadReviewActivity;
import com.sportify.settings.activity.SettingsActivity;
import com.sportify.settingsEditProfile.activity.EditProfileActivity;
import com.sportify.showFriends.activity.ShowFriendsActivity;
import com.sportify.userArea.presenter.UserAreaPresenter;
import com.sportify.userArea.presenter.UserAreaPresenterImpl;

import sportapp.pvt_sportapp.R;

//import com.sportify.showFriends.activity.ShowFriendsActivity;

public class UserAreaActivity extends AppCompatActivity implements UserAreaView {
    private UserAreaPresenter userAreaPresenter;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);
        sharedPref = getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
        userAreaPresenter = new UserAreaPresenterImpl(this, sharedPref);
    }


    public void findEventButtonClick(View v) {
        Toast.makeText(this, "I do nothing, ask my developers why", Toast.LENGTH_LONG).show();
    }

    public void ToNoteFromUserAreaActivity(View v){
        Intent goToNotificationsViewIntent = new Intent(UserAreaActivity.this, NotificationsActivity.class);
        UserAreaActivity.this.startActivity(goToNotificationsViewIntent);
    }


    public void createEventButtonClick(View v) {
        Toast.makeText(this, "Clicked Create Event", Toast.LENGTH_LONG).show();
        Intent createEventIntent = new Intent(UserAreaActivity.this, CreateEventPageOnePageOneActivity.class);
        UserAreaActivity.this.startActivity(createEventIntent);
    }

    public void startMapActivity(View view) {
        Intent startMapActivityIntent = new Intent(UserAreaActivity.this, MapsActivity.class);
        UserAreaActivity.this.startActivity(startMapActivityIntent);
    }

    public void goToSettingsActivity(View v){
        Intent goToSettingsViewIntent = new Intent(UserAreaActivity.this, SettingsActivity.class);
        UserAreaActivity.this.startActivity(goToSettingsViewIntent);
    }

    public void goToFriendListButtonClick(View v){
        Intent goToFriendListIntent = new Intent(UserAreaActivity.this, ShowFriendsActivity.class);
        UserAreaActivity.this.startActivity(goToFriendListIntent);
    }


    public void goToCalendarActivity(View v){
        Intent goToCalendarIntent = new Intent(UserAreaActivity.this, CalendarActivity.class);
        UserAreaActivity.this.startActivity(goToCalendarIntent);
    }

    public void goToEditProfileFromUser(View v){
        Intent goToEditProfileIntent = new Intent(UserAreaActivity.this, EditProfileActivity.class);
        UserAreaActivity.this.startActivity(goToEditProfileIntent);
    }

    public void goToEventAreaActivity(View v){
        Intent goToEventAreaIntent = new Intent(UserAreaActivity.this, EventAreaActivity.class);
        UserAreaActivity.this.startActivity(goToEventAreaIntent);
    }

    public void goToReadReviewActivity(View v){
        Intent goToReadReviewIntent = new Intent(UserAreaActivity.this, ReadReviewActivity.class);
        UserAreaActivity.this.startActivity(goToReadReviewIntent);
    }
}
