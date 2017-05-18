package com.sportify.notifications.activity;

import com.sportify.notifications.Notification;

import java.util.ArrayList;

public interface NotificationView {

    void showNotifications(ArrayList<Notification> notifications);

    void showToastToUser(String message);

}