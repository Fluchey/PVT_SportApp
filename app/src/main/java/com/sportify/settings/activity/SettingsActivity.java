package com.sportify.settings.activity;

import android.media.audiofx.BassBoost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.sportify.createEvent.createEventPageOne.activity.CreateEventPageOnePageOneActivity;
import com.sportify.notifications.activity.NotificationsActivity;
import com.sportify.settingsChangePass.activity.ChangePassActivity;
import com.sportify.maps.activity.MapsActivity;
import com.sportify.settingsIntegritypolicy.activity.IntegritypolicyActivity;
import com.sportify.showFriends.activity.ShowFriendsActivity;
import com.sportify.userArea.activity.UserAreaActivity;

import sportapp.pvt_sportapp.R;

public class SettingsActivity extends AppCompatActivity implements SettingsView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void ToUserAreaFromSettActivity(View v){
        Intent goToUserAreaViewIntent = new Intent(SettingsActivity.this, UserAreaActivity.class);
        SettingsActivity.this.startActivity(goToUserAreaViewIntent);
    }

    public void createEventfromSettActivity(View v) {
        Toast.makeText(this, "Clicked Create Event", Toast.LENGTH_LONG).show();
        Intent createEventIntent = new Intent(SettingsActivity.this, CreateEventPageOnePageOneActivity.class);
        SettingsActivity.this.startActivity(createEventIntent);
    }

    public void toMapFromSettActivity(View view) {
        Intent startMapActivityIntent = new Intent(SettingsActivity.this, MapsActivity.class);
        SettingsActivity.this.startActivity(startMapActivityIntent);
    }

    public void toNoteFromSettActivity(View view) {
        Intent notificationViewIntent = new Intent(SettingsActivity.this, NotificationsActivity.class);
        SettingsActivity.this.startActivity(notificationViewIntent);
    }

    public void toFriendsListfromSettActivity(View v){
        Intent goToFriendListIntent = new Intent(SettingsActivity.this, ShowFriendsActivity.class);
        SettingsActivity.this.startActivity(goToFriendListIntent);
    }

    public void toChangePassActivity(View v){
        Intent goToChangePassIntent = new Intent(SettingsActivity.this, ChangePassActivity.class);
        SettingsActivity.this.startActivity(goToChangePassIntent);
    }

    public void toIntegritypolicyFromSett(View v){
        Intent goToIntegritypolicyIntent = new Intent(SettingsActivity.this, IntegritypolicyActivity.class);
        SettingsActivity.this.startActivity(goToIntegritypolicyIntent);
    }


}
