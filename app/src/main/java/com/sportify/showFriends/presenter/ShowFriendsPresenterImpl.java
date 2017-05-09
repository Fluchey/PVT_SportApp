package com.sportify.showFriends.presenter;

import android.content.SharedPreferences;
import android.util.Log;

import com.sportify.showFriends.Profile;
import com.sportify.showFriends.activity.ShowFriendsView;
import com.sportify.showFriends.request.ShowFriendsRequest;
import com.sportify.showFriends.request.ShowFriendsRequestImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import sportapp.pvt_sportapp.R;

/**
 * Created by Maja on 2017-05-05.
 */

public class ShowFriendsPresenterImpl implements ShowFriendsPresenter, ShowFriendsRequest.OnShowFriendsFinishedListener {

    private ShowFriendsView showFriendsView;
    private ShowFriendsRequest showFriendsRequest;
    private SharedPreferences sharedPref;
    private String token = "";
    private String command;
    private ArrayList<Profile> friends;

    public ShowFriendsPresenterImpl(ShowFriendsView showFriendsView, SharedPreferences sharedPref){
        this.showFriendsView = showFriendsView;
        this.sharedPref = sharedPref;
        this.token = sharedPref.getString("jwt", "");
        this.showFriendsRequest = new ShowFriendsRequestImpl(this, token);
        this.command = "Show Friends";
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
                //TODO: Byt ut facebook icon till profilbild
                String firstname = jsonObject.getString("firstname");

                Profile friend = new Profile(firstname, firstname, R.drawable.com_facebook_button_icon_blue);

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
    public void updateFriends() {

    }

    @Override
    public void goToFriendsProfile(String userID) {

    }

    @Override
    public void showApiResponse(String... params) {

        getFriendsFromApiResponse(params[0]);

        if(command.equalsIgnoreCase("Show Friends")){
            showFriends();
        }else{
            System.out.println("Funkade inte");
        }

    }
}
