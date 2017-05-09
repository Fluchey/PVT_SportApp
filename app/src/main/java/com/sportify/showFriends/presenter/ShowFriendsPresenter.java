package com.sportify.showFriends.presenter;

/**
 * Created by Maja on 2017-05-05.
 */

public interface ShowFriendsPresenter {

    void getFriendsMakeApiRequest();

    void getFriendsFromApiResponse(String jsonMessage);

    void showFriends();

    void updateFriends();

    void goToFriendsProfile(String userID);
}
