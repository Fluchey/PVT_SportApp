package com.sportify.notifications.presenter;

/**
 * Created by Maja on 2017-05-17.
 */

public interface NotificationPresenter {

    void getNotificationsMakeApiRequest();

    void sendResponseEventInviteMakeApiRequest(String response, int eventID);

    void getNotificationsFromApiResponse(String jsonMessage);

}