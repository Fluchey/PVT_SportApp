package com.sportify.createEvent.createEventInviteFriends.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.sportify.arrayAdapters.MyArrayAdapterInviteFriends;
import com.sportify.createEvent.createEventInviteFriends.presenter.CreateEventInviteFriendsPresenter;
import com.sportify.createEvent.createEventInviteFriends.presenter.CreateEventInviteFriendsPresenterImpl;
import com.sportify.createEvent.createEventPreview.activity.CreateEventPreviewActivity;
import com.sportify.showFriends.Profile;

import java.util.ArrayList;

import sportapp.pvt_sportapp.R;

public class CreateEventInviteFriendsActivity extends AppCompatActivity implements CreateEventInviteFriendsView {

    private CreateEventInviteFriendsPresenter createEventInviteFriendsPresenter;
    private ArrayList<Profile> friendArray;
    private SharedPreferences sharedPref;
    private int eventID;
    private String eventName;
    String imageBase64;
    String eventDescription;

    /**
     *  ArrayAdapter to friend list
     */
    private MyArrayAdapterInviteFriends myArrayAdapter;
    private ListView friendList;

    /**
     *  ArrayAdapter to friend search in AutoCompleteTextView
     */
    private ArrayAdapter arrayAdapterSearch;
    private AutoCompleteTextView searchFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event_invite_friends);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            eventID = extras.getInt("EVENT_ID");
            eventName = extras.getString("EVENT_NAME");
            imageBase64 = extras.getString("imageBase64");
            eventDescription = extras.getString("eventDescription");

        }

        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        createEventInviteFriendsPresenter = new CreateEventInviteFriendsPresenterImpl(this, sharedPref);
        friendList = (ListView) findViewById(R.id.lvCreateEventFriends);
        friendList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        /**
         *  Update friend list
         */
        myArrayAdapter = new MyArrayAdapterInviteFriends(this, R.layout.create_event_friend_list_item, null, this);

        /**
         *  Update search
         */
        searchFriend = (AutoCompleteTextView) findViewById(R.id.svFriendsEventActivity3);

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
                    myArrayAdapter = new MyArrayAdapterInviteFriends(CreateEventInviteFriendsActivity.this,
                            R.layout.create_event_friend_list_item,
                            arrayTemplist,
                            CreateEventInviteFriendsActivity.this);
                    friendList.setAdapter(myArrayAdapter);
                }else{
                    myArrayAdapter = new MyArrayAdapterInviteFriends(CreateEventInviteFriendsActivity.this,
                            R.layout.create_event_friend_list_item,
                            friendArray,
                            CreateEventInviteFriendsActivity.this);
                    friendList.setAdapter(myArrayAdapter);
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
    public void showFriends(ArrayList friends) {
        friendArray = friends;
        myArrayAdapter = new MyArrayAdapterInviteFriends(this, R.layout.create_event_friend_list_item, friends, this);
        friendList.setAdapter(myArrayAdapter);
    }

    @Override
    public void updateFriendAdapter(ArrayList<Profile> friendList) {
        arrayAdapterSearch = new ArrayAdapter(this, android.R.layout.simple_list_item_1, friendList);
        searchFriend.setAdapter(arrayAdapterSearch);
    }

    @Override
    public ArrayList<Profile> getMarkedFriends() {
        return myArrayAdapter.getMarkedFriends();
    }

    @Override
    public void showToastToUser(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void sendInvites(View view) {
        ArrayList<Profile> markedFriends = getMarkedFriends();
        createEventInviteFriendsPresenter.sendInvites(markedFriends, eventID);

        goToPreviewAgainActivity(markedFriends);
    }

    public void goToPreviewAgainActivity(ArrayList<Profile> invitedFriends){
        Intent goToPreviewAgainActivityIntent = new Intent(CreateEventInviteFriendsActivity.this, CreateEventPreviewActivity.class);

        goToPreviewAgainActivityIntent.putExtra("EVENT_ID", eventID);
        goToPreviewAgainActivityIntent.putExtra("EVENT_NAME", eventName);
        goToPreviewAgainActivityIntent.putExtra("imageBase64", imageBase64);
        goToPreviewAgainActivityIntent.putExtra("eventDescription", eventDescription);
        goToPreviewAgainActivityIntent.putExtra("invitedFriends", invitedFriends);
        CreateEventInviteFriendsActivity.this.startActivity(goToPreviewAgainActivityIntent);


}

}