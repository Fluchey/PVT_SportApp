package com.sportify.settings.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import sportapp.pvt_sportapp.R;

public class SettingsActivity extends AppCompatActivity implements SettingsView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

}
