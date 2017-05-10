package com.sportify.addFriend.presenter;

import android.content.SharedPreferences;
import android.util.Log;

import com.sportify.addFriend.activity.AddFriendView;
import com.sportify.addFriend.request.AddFriendRequest;
import com.sportify.addFriend.request.AddFriendRequestImpl;
import com.sportify.showFriends.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import sportapp.pvt_sportapp.R;

/**
 * Created by Maja on 2017-05-05.
 */

public class AddFriendPresenterImpl implements AddFriendPresenter, AddFriendRequest.OnShowFriendsFinishedListener {

    private AddFriendView addFriendView;
    private AddFriendRequest addFriendRequest;
    private SharedPreferences sharedPref;
    private String token = "";
    private ArrayList<Profile> friends;

    public AddFriendPresenterImpl(AddFriendView addFriendView, SharedPreferences sharedPref){
        this.addFriendView = addFriendView;
        this.sharedPref = sharedPref;
        this.token = sharedPref.getString("jwt", "");
        this.addFriendRequest = new AddFriendRequestImpl(this, token);
        getFriendsMakeApiRequest();
        System.out.println("Nu har vi kört");
    }

    @Override
    public void addFriend(int profileID) {
        JSONObject jsonObject = new JSONObject();
//        int friendID = 4;
        try{
            jsonObject.put("friendID", "" + profileID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("Add friend: " + jsonObject.toString());
        addFriendRequest.makeApiRequestAddFriend("PUT", "addfriend", jsonObject.toString());
    }

    @Override
    public void getFriendsMakeApiRequest() {
        addFriendRequest.makeApiRequestGetUsers("POST", "findfriends", "{}");
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
                String firstName = jsonObject.getString("firstname");
                String lastName = jsonObject.getString("lastname");
                int profileID = jsonObject.getInt("profileID");

                Profile friend = new Profile(firstName, lastName, R.drawable.userprofileimage, profileID);

                friends.add(friend);
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void showFriends() {
        addFriendView.showFriends(friends);
    }

    @Override
    public void updateFriendSearchView() {
        addFriendView.updateFriendAdapter(friends);
    }

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
