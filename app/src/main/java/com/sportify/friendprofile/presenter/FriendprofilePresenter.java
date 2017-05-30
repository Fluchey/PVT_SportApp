package com.sportify.friendprofile.presenter;

import com.sportify.storage.Event;
import com.sportify.storage.Profile;

import java.util.ArrayList;

/**
 * Created by rasmu on 25/05/2017.
 */

public interface FriendprofilePresenter {

    public void showFriendInfo(Profile info);
    public void updateInfo();
    public ArrayList<Event> getEvents();
    public void addFriend();
}
