package com.sportify.placearea.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import sportapp.pvt_sportapp.R;

public class PlaceAreaActivity extends AppCompatActivity implements PlaceAreaView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_area);
    }
}
