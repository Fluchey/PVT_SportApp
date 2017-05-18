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
    String method;

    public NotificationPresenterImpl(NotificationView notificationView, SharedPreferences sharedPref){
        this.notificationView = notificationView;
        this.sharedPref = sharedPref;
        this.token = sharedPref.getString("jwt", "");
        this.notificationRequest = new NotificationRequestImpl(this, token);
        getNotificationsMakeApiRequest();
    }

    @Override
    public void getNotificationsMakeApiRequest() {
        method = "POST";
        notificationRequest.makeApiRequest("geteventinvites", method, "{}");
    }

    @Override
    public void sendResponseEventInviteMakeApiRequest(String response, int eventID) {

        JSONObject json = new JSONObject();
        try {
            json.put("response", response);
            json.put("eventID", "" + eventID);
        }catch (JSONException e){
            e.printStackTrace();
        }

        System.out.println("JSON " + json.toString());
        method = "PUT";
        notificationRequest.makeApiRequest("respondeventinvite", method, json.toString());
    }

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
                String eventName = notification.getString("event");
                int eventID = notification.getInt("eventID");

                Notification newNotification = new Notification(host, eventName, eventID);

                notifications.add(newNotification);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        notificationView.showNotifications(notifications);
    }

    @Override
    public void showApiResponse(String... params) {
        if(method.equalsIgnoreCase("POST")) {
            getNotificationsFromApiResponse(params[0]);
        }else{
            notificationView.showToastToUser(params[0]);
        }
    }
}
