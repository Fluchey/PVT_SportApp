package com.sportify.userArea.presenter;

import android.content.SharedPreferences;

import com.sportify.storage.Event;
import com.sportify.userArea.activity.UserAreaView;
import com.sportify.userArea.request.UserAreaRequest;
import com.sportify.userArea.request.UserAreaRequestImpl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by fluchey on 2017-04-20.
 */

public class UserAreaPresenterImpl implements UserAreaPresenter, UserAreaRequest.OnRequestFinishedListener {
    private UserAreaView userAreaView;
    private UserAreaRequest request;
    private SharedPreferences sharedPref;
    private String token = "";


    public UserAreaPresenterImpl(UserAreaView userAreaView, SharedPreferences sharedPref){
        this.userAreaView = userAreaView;
        this.sharedPref = sharedPref;
        this.token = sharedPref.getString("jwt", "");
        request = new UserAreaRequestImpl(token, this);
        getEventsFromDb();
    }

    private void getEventsFromDb() {
        JSONObject json = new JSONObject();
        try {
            json.put("dummy", "dummy");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.makeApiRequestPut(json.toString(), "geteventforuser", "PUT", "geteventforuser");
    }

    @Override
    public void showApiResponse(String command, String[] result) {
        switch(command){
            case "geteventforuser":
                request.updateEvents(result[0]);
                showEventOnView();
                break;
        }
    }

    private void showEventOnView() {
        userAreaView.showEvents(request.getEvents(), request.getCreator(), request.getPlaceName(), request.getEventImages());
    }

    @Override
    public ArrayList<Event> getEventsFromStorage() {
        return request.getEvents();
    }
}
