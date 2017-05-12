package com.sportify.settingsIntegritypolicy.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sportify.settings.activity.SettingsActivity;
import com.sportify.settingsChangePass.activity.ChangePassActivity;

import sportapp.pvt_sportapp.R;

public class IntegritypolicyActivity extends AppCompatActivity implements IntegritypolicyView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_integritypolicy);
    }

    public void toSettFromIntegrityActivity(View v){
        Intent goToSettingsViewIntent = new Intent(IntegritypolicyActivity.this, SettingsActivity.class);
        IntegritypolicyActivity.this.startActivity(goToSettingsViewIntent);
    }
}
