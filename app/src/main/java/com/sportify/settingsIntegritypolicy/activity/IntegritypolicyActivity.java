package com.sportify.settingsIntegritypolicy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import sportapp.pvt_sportapp.R;

public class IntegritypolicyActivity extends AppCompatActivity implements IntegritypolicyView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_integritypolicy);
    }
}
