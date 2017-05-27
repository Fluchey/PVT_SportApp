package com.sportify.notifications.presenter;

import android.content.SharedPreferences;
import android.util.Log;

import com.sportify.notifications.EventNotification;
import com.sportify.notifications.FriendRequestNotification;
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
        notifications = new ArrayList<>();
        updateNotificationView();
    }

    @Override
    public void updateNotificationView() {
        getEventNotificationsMakeApiRequest();
        getFriendRequestNotificationsMakeApiRequest();
    }

    @Override
    public void getEventNotificationsMakeApiRequest() {
        notificationRequest.makeApiRequest("geteventinvites", "POST", "{}", "eventInvitation");
    }

    @Override
    public void getFriendRequestNotificationsMakeApiRequest() {
        notificationRequest.makeApiRequest("getfriendrequests", "POST", "{}", "friendRequest");
    }

    @Override
    public void getNotificationsFromApiResponse(String jsonMessage, String command) {
        System.out.println("Dags för Json, ser ut såhär: " + jsonMessage.toString());
        JSONObject json = null;
        JSONArray array = null;

        if(jsonMessage == null){
            return;
        }

        try{
            json = new JSONObject(jsonMessage);
            if(command.equalsIgnoreCase("eventInvitation")){
                array = json.getJSONArray("eventNotifications");
            }else if(command.equalsIgnoreCase("friendRequest")){
                array = json.getJSONArray("friendRequests");
            }
            Log.d("JsonArr: ", array.toString());
        }catch (JSONException e) {
            e.printStackTrace();
        }

        if(json == null || array == null){
            return;
        }

        try{
            for(int i=0; i < array.length(); i++){

                //TODO: Hämta profilbild
                JSONObject jsonNotification = array.getJSONObject(i);
                Notification notification = null;
                String friendName = jsonNotification.getString("friendName");

                if(command.equalsIgnoreCase("eventInvitation")){
                    String eventName = jsonNotification.getString("event");
                    int eventID = jsonNotification.getInt("eventID");
                    notification = new EventNotification(friendName, eventName, eventID);
                }else if(command.equalsIgnoreCase("friendRequest")){
                    int friendID = jsonNotification.getInt("profileID");
                    notification = new FriendRequestNotification(friendName, friendID);
                }
                notifications.add(notification);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        //TODO: Flytta på denna så den görs först efter att hela notification-listan har fyllts på.
        notificationView.showNotifications(notifications);
    }

    @Override
    public void respondFriendRequest(int friendID, String friendName, String response) {
        JSONObject json = new JSONObject();

        try{
            json.put("friendID", "" + friendID);
            json.put("friendName", friendName);
            json.put("response", response);
        }catch (JSONException e){
            e.printStackTrace();
        }
        notificationRequest.makeApiRequest("respondfriendrequest", "PUT", json.toString(), "response");
    }

    @Override
    public void showApiResponse(String responseBody, String command) {
        System.out.println("Testar api " + responseBody + " " + command);
        if(!command.equalsIgnoreCase("response")) {
            System.out.println("Hämta notifications");
            getNotificationsFromApiResponse(responseBody, command);
        }else if(command.equalsIgnoreCase("response")){
            notificationView.showToastToUser(responseBody);
        }
    }
}
