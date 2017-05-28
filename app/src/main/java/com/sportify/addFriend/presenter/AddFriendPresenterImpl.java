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

/**
 * Created by Maja on 2017-05-05.
 */

public class AddFriendPresenterImpl implements AddFriendPresenter, AddFriendRequest.OnShowFriendsFinishedListener {

    private AddFriendView addFriendView;
    private AddFriendRequest addFriendRequest;
    private SharedPreferences sharedPref;
    private String token = "";
    private ArrayList<Profile> friends;
    private String method;

    public AddFriendPresenterImpl(AddFriendView addFriendView, SharedPreferences sharedPref){
        this.addFriendView = addFriendView;
        this.sharedPref = sharedPref;
        this.token = sharedPref.getString("jwt", "");
        this.addFriendRequest = new AddFriendRequestImpl(this, token);
        getAllUsersMakeApiRequest();
    }

    @Override
    public void addFriend(String name, int profileID) {
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("friendID", "" + profileID);
            jsonObject.put("friendName", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        method = "PUT";
        addFriendRequest.makeApiRequestAddFriend(method, "friendrequest", jsonObject.toString());
    }

    @Override
    public void getAllUsersMakeApiRequest() {
        method = "GET";
        addFriendRequest.makeApiRequestGetUsers(method, "getusers");
    }

    @Override
    public void getAllUsersFromApiResponse(String jsonMessage) {
        JSONObject json = null;
        JSONArray array = null;

        if(jsonMessage == null){
            return;
        }

        try {
            json = new JSONObject(jsonMessage);
            array = json.getJSONArray("users");
            Log.d("JsonArr: ", array.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(json == null || array == null){
            return;
        }

        try{
            friends = new ArrayList<>();

            System.out.println("Arraylängd " + array.length());
            for(int i=0; i < array.length(); i++){
                //TODO: Funkar bara om namn+efternamn inte är null
                JSONObject jsonObject = array.getJSONObject(i);
                //TODO: Byt ut facebook icon till profilbild

                String firstName = jsonObject.getString("firstname");
                int profileID = jsonObject.getInt("profileID");
                String lastName = jsonObject.getString("lastname");

                String imageBase64 = jsonObject.getString("imageBase64");
                if(imageBase64 == null || imageBase64.isEmpty()){
                    imageBase64 = "";
                }

                Profile friend = new Profile(firstName, lastName, imageBase64, profileID);
                System.out.println("Vän läggs till ");
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

        String apiResponseBody = params [0];
        if(method.equalsIgnoreCase("GET")){
            getAllUsersFromApiResponse(apiResponseBody);
            showFriends();
            updateFriendSearchView();
        }else
            addFriendView.showToastToUser(apiResponseBody);
    }
}
