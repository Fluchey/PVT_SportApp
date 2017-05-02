package com.sportify.friends.presenter;

import android.content.SharedPreferences;
import android.util.Log;

import com.sportify.friends.activity.FriendView;
import com.sportify.friends.request.FriendRequest;
import com.sportify.friends.request.FriendRequestImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public void getFriends(String jsonMessage) {

        JSONObject json = null;
        JSONArray array = null;

        try {
            json = new JSONObject(jsonMessage);
            array = json.getJSONArray("friendList");
            Log.d("JsonArr: ", array.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("E-message " + e.getMessage().toString());
        }
        if(json == null || array == null){
            return;
        }

        try{
            for(int i=0; i < array.length(); i++){
                JSONObject jsonObject = array.getJSONObject(i);
                friendView.showFriend(jsonObject.getString("userID"));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void showApiResponse(String... params) {
//        friendView.showApiRequestMessage(params[0]);
        getFriends(params[0]);
    }
}
