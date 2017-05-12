package com.sportify.settingsAccountSettings.activity;

import android.content.Intent;
import android.media.audiofx.BassBoost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sportify.settings.activity.SettingsActivity;
import com.sportify.settingsChangePass.activity.ChangePassActivity;
import com.sportify.settingsEditProfile.activity.*;

import sportapp.pvt_sportapp.R;

public class SettingsAccountActivity extends AppCompatActivity implements SettingsAccountView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_account);
    }

    public void toSettFromAccSettActivity(View v){
        Intent goToSettingsViewIntent = new Intent(SettingsAccountActivity.this, SettingsActivity.class);
        SettingsAccountActivity.this.startActivity(goToSettingsViewIntent);
    }
}
