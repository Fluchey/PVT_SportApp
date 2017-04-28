package com.sportify.friends.presenter;

import android.content.SharedPreferences;

import com.sportify.friends.activity.FriendView;
import com.sportify.friends.request.FriendRequest;
import com.sportify.friends.request.FriendRequestImpl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Maja on 2017-04-27.
 */

public class FriendPresenterImpl implements FriendPresenter, FriendRequest.OnShowFriendsFinishedListener{

    private FriendView friendView;
    private FriendRequest friendRequest;
    private SharedPreferences sharedPref;
    private String token = "";

    public FriendPresenterImpl(FriendView friendView, SharedPreferences sharedPref){
        this.friendView = friendView;
        this.sharedPref = sharedPref;
        this.token = sharedPref.getString("Token", "");
        this.friendRequest = new FriendRequestImpl(this, token);
    }

    @Override
    public void showFriends() {
        //Ta ID från den som är inloggad
        String userID = "2";
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("userID", userID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        friendRequest.makeApiRequest(jsonObject.toString());
    }

    public void getFriends(String friends) {

        JSONObject responseBody = null;
        ArrayList<String> friendList = null;

        System.out.println("getFriends");
        try {
            System.out.println("Json");
            responseBody = new JSONObject(friends);
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("Dåligt");
        }

        try {
            friendList = (ArrayList<String>) responseBody.get("friendList");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String showFriends = null;
        for(int i = 0; i<friendList.size(); i++){
            showFriends += friendList.get(i);
        }
        friendView.showFriends(showFriends);
    }

    @Override
    public void showApiResponse(String apiResponse) {
        friendView.showApiRequestMessage(apiResponse);
    }
}
