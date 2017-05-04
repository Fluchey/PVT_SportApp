package com.sportify.mainPage.activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

import com.sportify.login.activity.LoginActivity;
import com.sportify.register.activity.RegisterActivity;
import com.sportify.userArea.activity.UserAreaActivity;

import sportapp.pvt_sportapp.R;

public class MainPageActivity extends AppCompatActivity implements MainPageView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
    }

    public void goToLoginActivity(View v){
        Intent goToLoginViewIntent = new Intent(MainPageActivity.this, LoginActivity.class);
        MainPageActivity.this.startActivity(goToLoginViewIntent);
    }

    public void goToRegisterActivity(View v){
        Intent goToRegisterViewIntent = new Intent(MainPageActivity.this, RegisterActivity.class);
        MainPageActivity.this.startActivity(goToRegisterViewIntent);
    }

    public void goToUserAreaActivity(View v){
        Intent goToUserAreaViewIntent = new Intent(MainPageActivity.this, UserAreaActivity.class);
        MainPageActivity.this.startActivity(goToUserAreaViewIntent);
    }

}
