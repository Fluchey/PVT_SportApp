package com.sportify.calendar.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sportify.createEvent.createEventPageOne.activity.CreateEventPageOnePageOneActivity;
import com.sportify.maps.activity.MapsActivity;
import com.sportify.notifications.activity.NotificationActivity;
import com.sportify.showFriends.activity.ShowFriendsActivity;
import com.sportify.userArea.activity.UserAreaActivity;

import sportapp.pvt_sportapp.R;

public class CalendarActivity extends AppCompatActivity implements CalendarView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
    }

    public void toUserAreaFromCalActivity(View v){
        Intent goToUserAreaViewIntent = new Intent(CalendarActivity.this, UserAreaActivity.class);
        CalendarActivity.this.startActivity(goToUserAreaViewIntent);
    }

    public void createEventButtonClickFromCalAct(View v) {
        Toast.makeText(this, "Clicked Create Event", Toast.LENGTH_LONG).show();
        Intent createEventIntent = new Intent(CalendarActivity.this, CreateEventPageOnePageOneActivity.class);
        CalendarActivity.this.startActivity(createEventIntent);
    }

    public void goToMapFromCalActivity(View view) {
        Intent startMapActivityIntent = new Intent(CalendarActivity.this, MapsActivity.class);
        CalendarActivity.this.startActivity(startMapActivityIntent);
    }

    public void toNoteFromCalActivity(View view) {
        Intent notificationViewIntent = new Intent(CalendarActivity.this, NotificationActivity.class);
        CalendarActivity.this.startActivity(notificationViewIntent);
    }

    public void toFriendsListFromCalActivity(View v){
        Intent goToFriendListIntent = new Intent(CalendarActivity.this, ShowFriendsActivity.class);
        CalendarActivity.this.startActivity(goToFriendListIntent);
    }

}
