package com.sportify.eventArea.presenter;

import android.content.SharedPreferences;

import com.sportify.eventArea.activity.EventAreaView;
import com.sportify.eventArea.request.EventAreaRequest;
import com.sportify.eventArea.request.EventAreaRequestImpl;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by fluchey on 2017-05-17.
 */

public class EventAreaPresenterImpl implements EventAreaPresenter, EventAreaRequest.onRequestFinishedListener {
    private String token;
    private EventAreaView activity;
    private SharedPreferences sharedPreferences;
    private EventAreaRequest request;
    String attendance;
    String imageBase64;

    public EventAreaPresenterImpl(EventAreaView eventAreaActivity, SharedPreferences sharedPreferences) {
        this.activity = eventAreaActivity;
        this.sharedPreferences = sharedPreferences;
        this.token = sharedPreferences.getString("jwt", "");
        request = new EventAreaRequestImpl(this, token);
    }


    @Override
    public void getEventFromDb(int eventId) {
        JSONObject json = new JSONObject();
        try {
            json.put("eventId", String.valueOf(eventId));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.makeApiRequestPut(json.toString(), "event/getEventById", "PUT", "getEventById");
    }

    @Override
    public void shareEventToFacebook() {
        JSONObject json = new JSONObject();
        try {
            json.put("name", activity.getEventName());
            json.put("description", activity.getDescription());
            json.put("date", activity.getEventDate());
            json.put("type", activity.getEventType());
            json.put("hostName", activity.getHostName());
            json.put("placeName", activity.getPlaceName().getName());
            json.put("time", activity.getStartTime());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.makeApiRequestPut(json.toString(), "shareToFacebook", "PUT", "shareToFacebook");
    }

    @Override
    public void sendResponsEventInvite(String response, int eventId) {
        JSONObject json = new JSONObject();
        try {
            json.put("response", response);
            json.put("eventID", "" + eventId);
        }catch (JSONException e){
            e.printStackTrace();
        }
        request.makeApiRequestPut(json.toString(), "respondeventinvite", "PUT", "respondeventinvite");
    }

    @Override
    public void showApiResponse(String command, String... params) {
        switch (command){
            case "getEventById":
                String result[] = request.loadEventData(params[0]);;
                attendance = result[0];
                imageBase64 = result[1];
                presentDataOnView();
        }
    }

    private void presentDataOnView() {
        activity.setHostName(request.getUser().getFirstName(), request.getUser().getLastName());
        activity.setEventName(request.getEvent().getEventName());
        activity.setPlaceName(request.getPlace());
        activity.setEventDate(request.getEvent().getDate());
        activity.setStartTime(request.getEvent().getStartTime());
        activity.setEndTime(request.getEvent().getEndTime());
        activity.setEventType(request.getEvent().getEventType());
        activity.setMaxAttendance(request.getEvent().getMaxAttendance());
        activity.setPrice(request.getEvent().getPrice());
        activity.setPrivateEvent(request.getEvent().isPrivateEvent());
        activity.setAttendance(attendance);
        activity.setEventImage(imageBase64);
        activity.setDescription(request.getEvent().getEventDescription());


    }
}
