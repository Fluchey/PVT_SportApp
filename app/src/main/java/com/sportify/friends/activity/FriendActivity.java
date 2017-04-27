package com.sportify.friends.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import sportapp.pvt_sportapp.R;

public class FriendActivity extends AppCompatActivity implements FriendView {

    TextView friends;
    Button showFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        friends = (TextView) findViewById(R.id.tvShowFriends);
        showFriends = (Button) findViewById(R.id.btShowFriends);
    }

    @Override
    public void showFriends(String friendsToShow) {
        friends.setText(friendsToShow);
    }

    @Override
    public void showApiRequestMessage(String apiResponse) {
        Toast.makeText(this, apiResponse, Toast.LENGTH_LONG).show();
    }
}
