package com.sportify.createEvent.createEventInviteFriends.activity;

import android.view.View;

import com.sportify.showFriends.Profile;

import java.util.ArrayList;

/**
 * Created by Maja on 2017-04-27.
 */

public interface CreateEventInviteFriendsView {

    void showFriends(ArrayList<Profile> friends);

    void updateFriendAdapter(ArrayList<Profile> friendList);

    ArrayList<Profile> getMarkedFriends();

    void showToastToUser(String message);

    void sendInvites(View view);
}
