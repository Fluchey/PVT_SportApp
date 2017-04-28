package com.sportify.friends.presenter;

import android.content.SharedPreferences;

import com.sportify.friends.activity.FriendView;
import com.sportify.friends.request.FriendRequest;
import com.sportify.friends.request.FriendRequestImpl;

import java.util.ArrayList;

/**
 * Created by Maja on 2017-04-27.
 */

public class FriendPresenterImpl implements FriendPresenter, FriendRequest.OnShowFriendsFinishedListener{

    private FriendView friendView;
    private FriendRequest friendRequest;
    private SharedPreferences sharedPref;
    private String token = "";

    public FriendPresenterImpl(FriendView friendView, FriendRequest friendRequest, SharedPreferences sharedPref){
        this.friendView = friendView;
        this.sharedPref = sharedPref;
        this.token = sharedPref.getString("Token", "");
        this.friendRequest = new FriendRequestImpl(this, token);
    }

    @Override
    public void showFriends(ArrayList<String> friendList) {
        for(int i = 0; i<friendList.size(); i++){
            friendView.showFriends(friendList.get(i));
        }
    }

    @Override
    public void showApiResponse(String apiResponse) {
        friendView.showApiRequestMessage(apiResponse);
    }
}
