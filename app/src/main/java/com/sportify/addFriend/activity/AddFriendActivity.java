package com.sportify.addFriend.activity;

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

import com.sportify.addFriend.presenter.AddFriendPresenter;
import com.sportify.addFriend.presenter.AddFriendPresenterImpl;
import com.sportify.arrayAdapters.MyArrayAdapterShowFriends;
import com.sportify.friendprofile.activity.FriendprofileActivity;
import com.sportify.showFriends.Profile;

import java.util.ArrayList;

import sportapp.pvt_sportapp.R;

public class AddFriendActivity extends AppCompatActivity implements AddFriendView {

    private AddFriendPresenter addFriendsPresenter;
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
        setContentView(R.layout.activity_addnewfriends);

        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        addFriendsPresenter = new AddFriendPresenterImpl(this, sharedPref);
        friendList = (ListView) findViewById(R.id.lvShowFriends);
//        friendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Profile p = (Profile) friendList.getItemAtPosition(position);
//                int profileID = p.getProfileID();
//                String name = p.getFirstname();
//                addFriendsPresenter.addFriend(name, profileID);
//
//            }
//        });
        friendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Profile friendProfile = (Profile) parent.getItemAtPosition(position);
                int userId = friendProfile.getProfileID();
                toFriendProfile(userId);
            }
        });

        /**
         *  Update friend list
         */
        myArrayAdapterFriendList = new MyArrayAdapterShowFriends(this, R.layout.placereview_list_item, null);

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

                //TODO: Ful lösning, om tid finns bör ArrayAdaptern göras Filterable istället.

                ArrayList<Profile> arrayTemplist= new ArrayList<>();
                String searchString = searchFriend.getText().toString().toLowerCase();
                if(searchString.length()>0) {
                    for (int i = 0; i < friendArray.size(); i++) {
                        String currentString = friendArray.get(i).getFirstname().toLowerCase();
                        if (currentString.contains(searchString)) {
                            arrayTemplist.add(friendArray.get(i));
                        }
                    }
                    myArrayAdapterFriendList = new MyArrayAdapterShowFriends(AddFriendActivity.this, R.layout.placereview_list_item, arrayTemplist);
                    friendList.setAdapter(myArrayAdapterFriendList);
                }else{
                    myArrayAdapterFriendList = new MyArrayAdapterShowFriends(AddFriendActivity.this, R.layout.placereview_list_item, friendArray);
                    friendList.setAdapter(myArrayAdapterFriendList);
                }
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
        myArrayAdapterFriendList = new MyArrayAdapterShowFriends(this, R.layout.placereview_list_item, friendArray);
        friendList.setAdapter(myArrayAdapterFriendList);
    }

    @Override
    public void updateFriendAdapter(ArrayList<Profile> friendList) {
//        arrayAdapterSearch = new MyArrayAdapterShowFriends(this, R.layout.placereview_list_item, friendList);
        System.out.println("Vänlista: " + friendList.toString());
        arrayAdapterSearch = new ArrayAdapter(this, android.R.layout.simple_list_item_1, friendList);
        searchFriend.setAdapter(arrayAdapterSearch);
    }

    @Override
    public void showToastToUser(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void toFriendProfile(int userId){
        Intent goToFriendProfileIntent = new Intent(AddFriendActivity.this, FriendprofileActivity.class);
        goToFriendProfileIntent.putExtra("friendId", userId);
        AddFriendActivity.this.startActivity(goToFriendProfileIntent);
    }
}