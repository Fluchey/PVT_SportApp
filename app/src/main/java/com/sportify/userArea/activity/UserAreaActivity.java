package com.sportify.userArea.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sportify.createEvent.createEventPageOne.activity.CreateEventActivity;
import com.sportify.maps.activity.MapsActivity;
import com.sportify.userArea.presenter.UserAreaPresenter;
import com.sportify.userArea.presenter.UserAreaPresenterImpl;

import sportapp.pvt_sportapp.R;

public class UserAreaActivity extends AppCompatActivity implements UserAreaView {
    private UserAreaPresenter userAreaPresenter;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);
        sharedPref = getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
        userAreaPresenter = new UserAreaPresenterImpl(this, sharedPref);
    }


    public void findEventButtonClick(View v) {
        Toast.makeText(this, "I do nothing, ask my developers why", Toast.LENGTH_LONG).show();
    }

    public void createEventButtonClick(View v) {
        Toast.makeText(this, "Clicked Create Event", Toast.LENGTH_LONG).show();
        Intent createEventIntent = new Intent(UserAreaActivity.this, CreateEventActivity.class);
        UserAreaActivity.this.startActivity(createEventIntent);
    }

    public void startMapActivity(View view) {
        Intent startMapAcitivityIntent = new Intent(UserAreaActivity.this, MapsActivity.class);
        UserAreaActivity.this.startActivity(startMapAcitivityIntent);
    }
}
