package com.sportify.settingsChangePass.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sportify.settings.activity.SettingsActivity;
import com.sportify.userArea.activity.UserAreaActivity;

import sportapp.pvt_sportapp.R;

public class ChangePassActivity extends AppCompatActivity implements ChangePassView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_change_pass);
    }

    public void toSettFromChangeActivity(View v){
        Intent goToSettingsViewIntent = new Intent(ChangePassActivity.this, SettingsActivity.class);
        ChangePassActivity.this.startActivity(goToSettingsViewIntent);
    }

}
