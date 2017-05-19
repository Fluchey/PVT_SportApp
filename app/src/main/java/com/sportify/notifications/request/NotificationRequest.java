package com.sportify.notifications.request;

/**
 * Created by Maja on 2017-05-17.
 */

public interface NotificationRequest {

    interface OnShowNotificationFinishedListener{
        void showApiResponse(String responseBody, String command);
    }

    void makeApiRequest(String endUrl, String method, String jsonMessage, String command);
}
