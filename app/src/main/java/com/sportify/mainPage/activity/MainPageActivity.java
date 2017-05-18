package com.sportify.mainPage.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sportify.addFriend.activity.AddFriendActivity;
import com.sportify.login.activity.LoginActivity;
import com.sportify.register.activity.RegisterActivity;
import com.sportify.userArea.activity.UserAreaActivity;
import com.sportify.placeReview.activity.PlaceReviewActivity;

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

    public void testGoToPlaceReviewActivity(View v){
        Intent testGoToPlaceReviewViewIntent = new Intent(MainPageActivity.this, PlaceReviewActivity.class);
        Bundle b = new Bundle();
        b.putString("place", "Abrahamsbergs bollplan");
        b.putInt("userID", 232);
        testGoToPlaceReviewViewIntent.putExtras(b);

        MainPageActivity.this.startActivity(testGoToPlaceReviewViewIntent);
    }


    public void testAddFriend(View v){
        Intent testAddFriendIntent = new Intent(MainPageActivity.this, AddFriendActivity.class);
        MainPageActivity.this.startActivity(testAddFriendIntent);
    }
}
