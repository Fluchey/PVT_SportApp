package com.sportify.showFriends.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.sportify.arrayAdapters.MyArrayAdapterShowFriends;
import com.sportify.createEvent.createEventPageOne.activity.CreateEventPageOnePageOneActivity;
import com.sportify.maps.activity.MapsActivity;
import com.sportify.showFriends.Profile;
import com.sportify.showFriends.presenter.ShowFriendsPresenter;
import com.sportify.showFriends.presenter.ShowFriendsPresenterImpl;
import com.sportify.userArea.activity.UserAreaActivity;

import java.util.ArrayList;

import sportapp.pvt_sportapp.R;

public class ShowFriendsActivity extends AppCompatActivity implements ShowFriendsView {

    private ShowFriendsPresenter showFriendsPresenter;
    private ArrayList<Profile> friendArray;
    private SharedPreferences sharedPref;

    /**
     *  ArrayAdapter to friend list
     */
    private MyArrayAdapterShowFriends myArrayAdapterFriendList;
    private ListView friendList;

    /**
     *  ArrayAdapter to friend search in AutoCompleteTextView
     */
    private ArrayAdapter arrayAdapterSearch;
    private AutoCompleteTextView searchFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        showFriendsPresenter = new ShowFriendsPresenterImpl(this, sharedPref);
        friendList = (ListView) findViewById(R.id.lvShowFriends);

        /**
         *  Update friend list
         */
        myArrayAdapterFriendList = new MyArrayAdapterShowFriends(this, R.layout.friend_list_item, null);

        /**
         *  Update search
         */
        searchFriend = (AutoCompleteTextView) findViewById(R.id.searchFrindAcTv);

        searchFriend.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        searchFriend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }

    @Override
    public void showFriends(ArrayList<Profile> friendlist) {
        friendArray = friendlist;
        myArrayAdapterFriendList = new MyArrayAdapterShowFriends(this, R.layout.friend_list_item, friendArray);
        friendList.setAdapter(myArrayAdapterFriendList);
    }

    @Override
    public void updateFriendAdapter(ArrayList<Profile> friendList) {
        arrayAdapterSearch = new MyArrayAdapterShowFriends(this, R.layout.friend_list_item, friendList);
        arrayAdapterSearch = new ArrayAdapter(this, android.R.layout.simple_list_item_activated_1, friendList);
//        arrayAdapterSearch = new ArrayAdapter(this, android.R.layout.activity_list_item, friendList);
        searchFriend.setAdapter(arrayAdapterSearch);
    }

    public void createEventfromFriendsActivity(View v) {
        Toast.makeText(this, "Clicked Create Event", Toast.LENGTH_LONG).show();
        Intent createEventIntent = new Intent(ShowFriendsActivity.this, CreateEventPageOnePageOneActivity.class);
        ShowFriendsActivity.this.startActivity(createEventIntent);
    }

    public void toMapFromFriendsActivity(View view) {
        Intent startMapActivityIntent = new Intent(ShowFriendsActivity.this, MapsActivity.class);
        ShowFriendsActivity.this.startActivity(startMapActivityIntent);
    }


    public void ToUserAreaFromFriendsActivity(View v){
        Intent goToUserAreaIntent = new Intent(ShowFriendsActivity.this, UserAreaActivity.class);
        ShowFriendsActivity.this.startActivity(goToUserAreaIntent);
    }
}