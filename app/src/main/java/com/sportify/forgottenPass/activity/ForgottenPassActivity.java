package com.sportify.forgottenPass.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import sportapp.pvt_sportapp.R;

public class ForgottenPassActivity extends AppCompatActivity implements ForgottenPassView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten_pass);
    }
}
