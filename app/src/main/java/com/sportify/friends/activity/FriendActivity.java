package com.sportify.friends.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.sportify.friends.presenter.FriendPresenter;
import com.sportify.friends.presenter.FriendPresenterImpl;

import sportapp.pvt_sportapp.R;

public class FriendActivity extends AppCompatActivity implements FriendView {

    TextView friends;
    Button showFriends;
    ListView friendList;
    FriendPresenter friendPresenter;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        friendPresenter = new FriendPresenterImpl(this, sharedPref);
        friends = (TextView) findViewById(R.id.tvShowFriends);
        showFriends = (Button) findViewById(R.id.btShowFriends);
        friendList = (ListView) findViewById(R.id.lvFriends);

    }

    public void findFriendClick(View v){
        friendPresenter.showFriends();
    }

    @Override
    public void showFriends(ArrayAdapter myAdapter) {
//        friends.setText(friendsToShow);
        friendList.setAdapter(myAdapter);

    }

}
