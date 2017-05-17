package com.sportify.settingsEditProfile.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sportify.settings.activity.SettingsActivity;

import sportapp.pvt_sportapp.R;

public class EditProfileActivity extends AppCompatActivity implements EditProfileView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_edit_profile);
    }

    public void toSettFromEditProActivity(View v){
        Intent goToSettingsViewIntent = new Intent(EditProfileActivity.this, SettingsActivity.class);
        EditProfileActivity.this.startActivity(goToSettingsViewIntent);
    }

}
