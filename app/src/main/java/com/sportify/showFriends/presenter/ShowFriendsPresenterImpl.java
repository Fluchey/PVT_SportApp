package com.sportify.showFriends.presenter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.sportify.friendprofile.activity.FriendprofileActivity;
import com.sportify.showFriends.Profile;
import com.sportify.showFriends.activity.ShowFriendsActivity;
import com.sportify.showFriends.activity.ShowFriendsView;
import com.sportify.showFriends.request.ShowFriendsRequest;
import com.sportify.showFriends.request.ShowFriendsRequestImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Maja on 2017-05-05.
 */

public class ShowFriendsPresenterImpl implements ShowFriendsPresenter, ShowFriendsRequest.OnShowFriendsFinishedListener {

    private ShowFriendsView showFriendsView;
    private ShowFriendsRequest showFriendsRequest;
    private SharedPreferences sharedPref;
    private String token = "";
    private ArrayList<Profile> friends;

    public ShowFriendsPresenterImpl(ShowFriendsView showFriendsView, SharedPreferences sharedPref){
        this.showFriendsView = showFriendsView;
        this.sharedPref = sharedPref;
        this.token = sharedPref.getString("jwt", "");
        this.showFriendsRequest = new ShowFriendsRequestImpl(this, token);
        getFriendsMakeApiRequest();
    }

    @Override
    public void getFriendsMakeApiRequest() {
        showFriendsRequest.makeApiRequest("{}");
    }

    @Override
    public void getFriendsFromApiResponse(String jsonMessage) {
        JSONObject json = null;
        JSONArray array = null;

        if(jsonMessage == null){
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
                JSONObject jsonObject = array.getJSONObject(i);
                String firstName = jsonObject.getString("firstname");
                String lastName = jsonObject.getString("lastname");
                int profileID = jsonObject.getInt("profileID");

                String imageBase64 = jsonObject.getString("imageBase64");
                if(imageBase64 == null || imageBase64.isEmpty()){
                    imageBase64 = "";
                }

                Profile friend = new Profile(firstName, lastName, imageBase64, profileID);

                friends.add(friend);
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void showFriends() {
        showFriendsView.showFriends(friends);
    }

    @Override
    public void updateFriendSearchView() {
        showFriendsView.updateFriendAdapter(friends);
    }

    //TODO: Fixa denna metod
    @Override
    public void goToFriendsProfile(String userID) {

    }

    @Override
    public void showApiResponse(String... params) {

        getFriendsFromApiResponse(params[0]);
        showFriends();
        updateFriendSearchView();
    }
}
