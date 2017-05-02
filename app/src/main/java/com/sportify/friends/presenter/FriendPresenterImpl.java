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

    public void getFriends(String friends) {

        JSONObject json = null;
//        ArrayList<String> friendList = null;
        JSONArray array = null;

        System.out.println("getFriends");
        try {
            System.out.println("Json");

            json = new JSONObject(friends);
            array = json.getJSONArray("friendList");
            Log.d("JsonArr: ", array.toString());
//            responseBody = new JSONObject(friends.substring(friends.indexOf("{"), friends.lastIndexOf("}") + 1));
//            if(responseBody != null){
//                System.out.println("Yay!");
//            }
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("Dåligt " + e.getMessage().toString());
        }
        if(json == null || array == null){
            System.out.println("null Array/Json");
            return;
        }

        try{
            for(int i=0; i < array.length(); i++){
                JSONObject jsonObject = array.getJSONObject(i);
                friendView.showFriends(jsonObject.getString("userID"));
            }

            }catch(JSONException e){
            System.out.println("FEL!");
            e.printStackTrace();
        }

//        try {
//            friendList = (ArrayList<String>) responseBody.get("friendList");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            friendList = (ArrayList<String>) responseBody.get("friendList");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        String showFriends = null;
//        for(int i = 0; i<friendList.size(); i++){
//            showFriends += friendList.get(i);
//        }
//        friendView.showFriends(friendList.toString());
    }

    @Override
    public void showApiResponse(String apiResponse) {
        friendView.showApiRequestMessage(apiResponse);
    }
}
