package com.sportify.settings.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.sportify.changePass.activity.ChangePassActivity;
import com.sportify.createEvent.createEventPageOne.activity.CreateEventActivity;
import com.sportify.maps.activity.MapsActivity;
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
        Intent createEventIntent = new Intent(SettingsActivity.this, CreateEventActivity.class);
        SettingsActivity.this.startActivity(createEventIntent);
    }

    public void toMapFromSettActivity(View view) {
        Intent startMapActivityIntent = new Intent(SettingsActivity.this, MapsActivity.class);
        SettingsActivity.this.startActivity(startMapActivityIntent);
    }

    public void toFriendsListfromSettActivity(View v){
        Intent goToFriendListIntent = new Intent(SettingsActivity.this, ShowFriendsActivity.class);
        SettingsActivity.this.startActivity(goToFriendListIntent);
    }

    public void toChangePassActivity(View v){
        Intent goToChangePassIntent = new Intent(SettingsActivity.this, ChangePassActivity.class);
        SettingsActivity.this.startActivity(goToChangePassIntent);
    }
}
