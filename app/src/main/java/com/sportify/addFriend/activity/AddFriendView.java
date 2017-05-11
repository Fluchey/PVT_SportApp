package com.sportify.addFriend.activity;

import com.sportify.showFriends.Profile;

import java.util.ArrayList;

/**
 * Created by Maja on 2017-05-05.
 */

public interface AddFriendView {

    void showFriends(ArrayList<Profile> friendList);

    void updateFriendAdapter(ArrayList<Profile> friendList);

    void showToastToUser(String message);
}
