package com.sportify.createEvent.createEventInviteFriends.presenter;

import android.content.SharedPreferences;
import android.util.Log;

import com.sportify.showFriends.Profile;
import com.sportify.createEvent.createEventInviteFriends.activity.CreateEventInviteFriendsView;
import com.sportify.createEvent.createEventInviteFriends.request.CreateEventInviteFriendsRequest;
import com.sportify.createEvent.createEventInviteFriends.request.CreateEventInviteFriendsRequestImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import sportapp.pvt_sportapp.R;

/**
 * Created by Maja on 2017-04-27.
 */

public class CreateEventInviteFriendsPresenterImpl implements CreateEventInviteFriendsPresenter, CreateEventInviteFriendsRequest.OnShowFriendsFinishedListener{

    private CreateEventInviteFriendsView createEventInviteFriendsView;
    private CreateEventInviteFriendsRequest createEventInviteFriendsRequest;
    private SharedPreferences sharedPref;
    private String token = "";
    private ArrayList<Profile> friends;

    public CreateEventInviteFriendsPresenterImpl(CreateEventInviteFriendsView createEventInviteFriendsView, SharedPreferences sharedPref){
        this.createEventInviteFriendsView = createEventInviteFriendsView;
        this.sharedPref = sharedPref;
        this.token = sharedPref.getString("jwt", "");
        this.createEventInviteFriendsRequest = new CreateEventInviteFriendsRequestImpl(this, token);
        getFriendsMakeApiRequest();
    }

    @Override
    public void getFriendsMakeApiRequest() {
        //TODO: Skickar nu bara tomt Json för att jag inte får GET att funka, gör fortf med POST i Heroku.
        createEventInviteFriendsRequest.makeApiRequest("{}");
    }

    @Override
    public void getFriendsFromApiResponse(String jsonMessage) {

        JSONObject json = null;
        JSONArray array = null;

        if(jsonMessage == null){
            System.out.println("Fel format på Json");
            return;
        }
        try {
            json = new JSONObject(jsonMessage);
            array = json.getJSONArray("friendList");
            Log.d("JsonArr: ", array.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(json == null || array == null){
            return;
        }

        try{
            friends = new ArrayList<>();

            for(int i=0; i < array.length(); i++){
                //TODO: Byt ut facebook icon till profilbild

                JSONObject jsonObject = array.getJSONObject(i);
                String firstname = jsonObject.getString("firstname");
                String lastname = jsonObject.getString("lastname");
                int profileID = jsonObject.getInt("profileID");

                Profile friend = new Profile(firstname, lastname, R.drawable.userprofileimage, profileID);

                friends.add(friend);
            }

        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void showFriends() {
        createEventInviteFriendsView.showFriends(friends);
    }

    @Override
    public void updateFriendSearchView() {
        createEventInviteFriendsView.updateFriendAdapter(friends);
    }

    @Override
    public void sendInvites(ArrayList<Profile> markedFriends) {
        System.out.println(markedFriends);



    }

    @Override
    public void showApiResponse(String... params) {
        getFriendsFromApiResponse(params[0]);
        showFriends();
        updateFriendSearchView();
    }
}
