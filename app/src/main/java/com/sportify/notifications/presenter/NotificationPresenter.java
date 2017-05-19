package com.sportify.notifications.presenter;

/**
 * Created by Maja on 2017-05-17.
 */

public interface NotificationPresenter {

    void updateNotificationView();

    void getEventNotificationsMakeApiRequest();

    void getFriendRequestNotificationsMakeApiRequest();

    void getNotificationsFromApiResponse(String jsonMessage, String command);

}