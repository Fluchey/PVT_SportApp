package com.sportify.forgottenPass.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sportify.login.activity.LoginActivity;
import com.sportify.mainPage.activity.MainPageActivity;

import sportapp.pvt_sportapp.R;

public class ForgottenPassActivity extends AppCompatActivity implements ForgottenPassView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten_pass);
    }


    public void toLoginFromForgottenActivity(View v){
        Intent goToLoginViewIntent = new Intent(ForgottenPassActivity.this, LoginActivity.class);
        ForgottenPassActivity.this.startActivity(goToLoginViewIntent);
    }
}
