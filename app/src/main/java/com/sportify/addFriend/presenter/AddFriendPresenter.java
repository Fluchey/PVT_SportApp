package com.sportify.addFriend.presenter;

/**
 * Created by Maja on 2017-05-05.
 */

public interface AddFriendPresenter {

    void addFriend(String name, int profileID);

    void getAllUsersMakeApiRequest();

    void getAllUsersFromApiResponse(String jsonMessage);

    void showFriends();

    void updateFriendSearchView();

    void goToFriendsProfile(String userID);
}
