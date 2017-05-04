package com.sportify.showFriends.presenter;

import android.content.SharedPreferences;
import android.util.Log;

import com.sportify.showFriends.Profile;
import com.sportify.showFriends.activity.FriendView;
import com.sportify.showFriends.request.FriendRequest;
import com.sportify.showFriends.request.FriendRequestImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import sportapp.pvt_sportapp.R;

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
        String userID = "1";
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
        }
        if(json == null || array == null){
            return;
        }

        try{
            ArrayList<Profile> friends = new ArrayList<>();

            for(int i=0; i < array.length(); i++){
                JSONObject jsonObject = array.getJSONObject(i);
                //TODO: Byt ut facebook icon till profilbild
                String firstname = jsonObject.getString("firstname");

                Profile friend = new Profile(firstname, firstname, R.drawable.com_facebook_button_icon_blue);

                friends.add(friend);
            }

            friendView.showFriends(friends);

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
