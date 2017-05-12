package com.sportify.createEvent.createEventInviteFriends.presenter;

/**
 * Created by Maja on 2017-04-27.
 */

public interface CreateEventInviteFriendsPresenter {

    void getFriendsMakeApiRequest();

    void getFriendsFromApiResponse(String jsonMessage);

    void showFriends();

    void updateFriendSearchView();


}
