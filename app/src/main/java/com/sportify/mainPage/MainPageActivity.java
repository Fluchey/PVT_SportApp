package com.sportify.mainPage;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

import com.sportify.login.activity.LoginActivity;

import sportapp.pvt_sportapp.R;

public class MainPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
    }

    public void goToLoginActivity(View v){
        Intent goToUserAreaIntent = new Intent(MainPageActivity.this, LoginActivity.class);
        MainPageActivity.this.startActivity(goToUserAreaIntent);

//        Intent goToUserAreaIntent = new Intent(LoginActivity.this, UserAreaActivity.class);
//        LoginActivity.this.startActivity(goToUserAreaIntent);
    }
}
