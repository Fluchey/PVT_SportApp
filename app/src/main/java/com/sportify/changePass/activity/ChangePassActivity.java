package com.sportify.changePass.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sportify.forgottenPass.activity.*;

import sportapp.pvt_sportapp.R;

public class ChangePassActivity extends AppCompatActivity implements ChangePassView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
    }
}
