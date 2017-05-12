package com.sportify.notifications.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sportify.createEvent.createEventPageOne.activity.CreateEventPageOnePageOneActivity;
import com.sportify.maps.activity.MapsActivity;
import com.sportify.settings.activity.SettingsActivity;
import com.sportify.showFriends.activity.ShowFriendsActivity;
import com.sportify.userArea.activity.UserAreaActivity;

import sportapp.pvt_sportapp.R;

public class NotificationsActivity extends AppCompatActivity implements NotificationsView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
    }


    public void ToUserAreaFromNoteActivity(View v){
        Intent goToUserAreaViewIntent = new Intent(NotificationsActivity.this, UserAreaActivity.class);
        NotificationsActivity.this.startActivity(goToUserAreaViewIntent);
    }

    public void createEventfromNoteActivity(View v) {
        Toast.makeText(this, "Clicked Create Event", Toast.LENGTH_LONG).show();
        Intent createEventIntent = new Intent(NotificationsActivity.this, CreateEventPageOnePageOneActivity.class);
        NotificationsActivity.this.startActivity(createEventIntent);
    }

    public void toMapFromNoteActivity(View view) {
        Intent startMapActivityIntent = new Intent(NotificationsActivity.this, MapsActivity.class);
        NotificationsActivity.this.startActivity(startMapActivityIntent);
    }

    public void toFriendsListfromNoteActivity(View v){
        Intent goToFriendListIntent = new Intent(NotificationsActivity.this, ShowFriendsActivity.class);
        NotificationsActivity.this.startActivity(goToFriendListIntent);
    }

}
