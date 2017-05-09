package com.sportify.showFriends.activity;

import com.sportify.showFriends.Profile;

import java.util.ArrayList;

/**
 * Created by Maja on 2017-05-05.
 */

public interface ShowFriendsView {

    //TODO: Något som måste finnas här?

    void showFriends(ArrayList<Profile> friendList);

    void updateFriendAdapter(ArrayList<Profile> friendList);

    void getUserWroteSearch();
}
