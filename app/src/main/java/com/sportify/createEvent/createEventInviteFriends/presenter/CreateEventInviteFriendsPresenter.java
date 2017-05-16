package com.sportify.createEvent.createEventInviteFriends.presenter;

import com.sportify.showFriends.Profile;

import java.util.ArrayList;

/**
 * Created by Maja on 2017-04-27.
 */

public interface CreateEventInviteFriendsPresenter {

    void getFriendsMakeApiRequest();

    void getFriendsFromApiResponse(String jsonMessage);

    void showFriends();

    void updateFriendSearchView();

    void sendInvites(ArrayList<Profile> markedFriends, int eventID);
}
