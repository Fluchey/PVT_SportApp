package com.sportify.eventArea.presenter;

import android.content.SharedPreferences;
import android.util.Log;

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
        Log.d("Json", json.toString());
        request.makeApiRequestPut(json.toString(), "event/getEventById", "PUT", "getEventById");
    }

    @Override
    public void showApiResponse(String command, String... params) {
        switch (command){
            case "getEventById":
                request.loadEventData(params[0]);
                presentDataOnView();
        }
    }

    private void presentDataOnView() {
        activity.setPlaceName(request.getPlace().getName());
        activity.setStartDate(request.getEvent().getStartDate());
        activity.setStartTime(request.getEvent().getStartTime());
        activity.setEndTime(request.getEvent().getEndTime());
        activity.setPrice(request.getEvent().getPrice());
        activity.setDescription(request.getEvent().getEventDescription());
    }
}
