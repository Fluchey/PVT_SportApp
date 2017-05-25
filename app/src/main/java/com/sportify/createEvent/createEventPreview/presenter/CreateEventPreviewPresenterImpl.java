package com.sportify.createEvent.createEventPreview.presenter;

import android.content.SharedPreferences;

import com.sportify.createEvent.createEventPreview.activity.CreateEventPreviewView;
import com.sportify.createEvent.createEventPreview.request.CreateEventPreviewRequest;
import com.sportify.createEvent.createEventPreview.request.CreateEventPreviewRequestImpl;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Maja on 2017-05-12.
 */

public class CreateEventPreviewPresenterImpl implements CreateEventPreviewPresenter, CreateEventPreviewRequest.OnCreateEventPreviewFinishedListener{

    private CreateEventPreviewView previewView;
    private CreateEventPreviewRequest previewRequest;
    private SharedPreferences sharedPref;
    private String token = "";

    public CreateEventPreviewPresenterImpl(CreateEventPreviewView previewView, SharedPreferences sharedPref){
        this.previewView = previewView;
        this.sharedPref = sharedPref;
        this.token = sharedPref.getString("jwt", "");

        previewRequest = new CreateEventPreviewRequestImpl(this, token);
    }

    @Override
    public void deleteEvent(int eventID) {
        JSONObject jsonObject = new JSONObject();

        try{
            jsonObject.put("eventID", String.valueOf(eventID));
        }catch (JSONException e){
            e.printStackTrace();
        }

        previewRequest.makeApiRequest("DELETE", "deleteEvent", jsonObject.toString());
    }

    @Override
    public void showApiResponse(String apiResponse) {
        previewView.goToUserArea(apiResponse);
    }
}
