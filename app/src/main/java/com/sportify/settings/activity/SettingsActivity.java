package com.sportify.settings.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

import com.sportify.userArea.activity.UserAreaActivity;

import sportapp.pvt_sportapp.R;

public class SettingsActivity extends AppCompatActivity implements SettingsView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void goToUserAreaActivity(View v){
        Intent goToUserAreaViewIntent = new Intent(SettingsActivity.this, UserAreaActivity.class);
        SettingsActivity.this.startActivity(goToUserAreaViewIntent);
    }


}
