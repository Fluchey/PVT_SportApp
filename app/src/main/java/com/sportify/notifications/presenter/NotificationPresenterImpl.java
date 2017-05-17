package com.sportify.notifications.presenter;

import android.content.SharedPreferences;
import android.util.Log;

import com.sportify.notifications.Notification;
import com.sportify.notifications.activity.NotificationView;
import com.sportify.notifications.request.NotificationRequest;
import com.sportify.notifications.request.NotificationRequestImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Maja on 2017-05-17.
 */

public class NotificationPresenterImpl implements NotificationPresenter, NotificationRequest.OnShowNotificationFinishedListener {

    private NotificationView notificationView;
    private NotificationRequest notificationRequest;
    private SharedPreferences sharedPref;
    private String token = "";
    private ArrayList<Notification> notifications;

    public NotificationPresenterImpl(NotificationView notificationView, SharedPreferences sharedPref){
        this.notificationView = notificationView;
        this.sharedPref = sharedPref;
        this.token = sharedPref.getString("jwt", "");
        this.notificationRequest = new NotificationRequestImpl(this, token);
        getNotificationsMakeApiRequest();

    }

    @Override
    public void getNotificationsMakeApiRequest() {
        notificationRequest.makeApiRequest("{}");
    }

    //TODO: Slå ihop denna med showNotifications()?
    @Override
    public void getNotificationsFromApiResponse(String jsonMessage) {
        JSONObject json = null;
        JSONArray array = null;

        if(jsonMessage == null){
            return;
        }

        try{
            json = new JSONObject(jsonMessage);
            array = json.getJSONArray("eventNotifications");
            Log.d("JsonArr: ", array.toString());
        }catch (JSONException e) {
            e.printStackTrace();
        }

        if(json == null || array == null){
            return;
        }

        try{
            notifications = new ArrayList<>();

            for(int i=0; i < array.length(); i++){

                JSONObject notification = array.getJSONObject(i);
                String host = notification.getString("host");
                String message = notification.getString("event");

                Notification newNotification = new Notification(host, message);

                notifications.add(newNotification);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        notificationView.showNotifications(notifications);
    }

    @Override
    public void showApiResponse(String... params) {
        getNotificationsFromApiResponse(params[0]);
    }
}
