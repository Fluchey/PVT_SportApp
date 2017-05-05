package com.sportify.profile.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import sportapp.pvt_sportapp.R;

public class ProfileActivity extends AppCompatActivity implements ProfileView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDateOfBirth() {
        return null;
    }

    @Override
    public String[] getInterests() {
        return new String[0];
    }

    @Override
    public void showNameEmptyError(int resId) {

    }

    @Override
    public void showDateOfBirthEmptyError(int resId) {

    }

    @Override
    public void showDateOfBirthWrongFormatError(int resId) {

    }

    @Override
    public void launchUserActivity() {

    }
}
