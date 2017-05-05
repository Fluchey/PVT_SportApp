package com.sportify.createEvent.createEventPageOne.presenter;

import android.content.SharedPreferences;
import android.util.Log;

import com.sportify.createEvent.createEventPageOne.activity.CreateEventView;
import com.sportify.createEvent.createEventPageOne.request.CreateEventRequest;
import com.sportify.createEvent.createEventPageOne.request.CreateEventRequestImpl;
import com.sportify.storage.Place;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import sportapp.pvt_sportapp.R;

/**
 * Created by Maja on 2017-04-18.
 */

public class CreateEventPresenterImpl implements CreateEventPresenter, CreateEventRequest.OnCreateEventFinishedListener{
    private CreateEventView createEventView;
    private CreateEventRequest createEventRequest;
    private SharedPreferences sharedPref;
    private String token = "";

    public CreateEventPresenterImpl(CreateEventView createEventView, SharedPreferences sharedPref){
        this.createEventView = createEventView;
        this.sharedPref = sharedPref;
        this.token = sharedPref.getString("jwt", "");
        createEventRequest = new CreateEventRequestImpl(this, token);

        /**
         * This runs oncreate and updates the ArrayAdapter with all the current places in the database.
         */
        createEventRequest.makeApiRequestGet("GET", "getallplaces", "getAllPlaces");
    }

    @Override
    public void createEvent() {
        createEventView.clearAllErrors();
        System.out.println("Skapa event!");
        String eventName = createEventView.getEventName();
        String eventPriceEt = createEventView.getEventPrice();
        int eventPrice = 0;
        String eventDate = createEventView.getEventDate();
        String eventStartTime = createEventView.getEventStartTime();
        String eventEndTime = createEventView.getEventEndTime();
        String eventType = createEventView.getEventType();
        String eventPlace = createEventView.getEventPlace();
        String eventMaxAttendance = createEventView.getEventMaxAttendance();
        String eventDescription = createEventView.getEventDescription();
        boolean eventPrivate = createEventView.getPrivateEvent();
        int eventPrivateInt = 0;

        if(eventPrivate){
            eventPrivateInt = 1;
        }
        if (eventName.isEmpty()) {
            createEventView.showEventNameEmptyError(R.string.event_name_empty_error);
        }
        else if(eventDate.isEmpty()){
            createEventView.showEventDateEmptyError(R.string.event_date_empty_error);
        }
        else if(!validDateFormat(eventDate)){
            createEventView.showEventDateFormatError(R.string.event_date_wrongformat_error);
        }
        else if(eventStartTime.isEmpty()){
            createEventView.showEventStartTimeEmptyError(R.string.event_start_time_empty_error);
        }
        else if(eventEndTime.isEmpty()){
            createEventView.showEventEndTimeEmptyError(R.string.event_end_time_empty_error);
        }
        else if(eventType.isEmpty()) {
            createEventView.showEventTypeEmptyError(R.string.event_type_empty_error);
        }
        else if(createEventView.getUserWroteSearch()){
            createEventView.showEventPlaceEmptyError(R.string.event_place_does_not_exist);
        }else{
            if (!eventPriceEt.isEmpty()) {
                try {
                    eventPrice = Integer.parseInt(eventPriceEt);
                } catch (NumberFormatException e) {
                    createEventView.showEventPriceWrongFormatError(R.string.event_price_wrongformat_error);
                    return;
                }
            }
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("eventName", eventName);
                jsonObject.put("eventPrice", "" + eventPrice);
                jsonObject.put("eventDate", "" + eventDate);
                jsonObject.put("eventStartTime", "" + eventStartTime);
                jsonObject.put("eventEndTime", "" + eventEndTime);
                jsonObject.put("eventType", eventType);
                jsonObject.put("eventPlace", eventPlace);
                if(!eventMaxAttendance.isEmpty()) {
                    jsonObject.put("eventMaxAttendance", eventMaxAttendance);
                }
                jsonObject.put("eventDescription", eventDescription);
                jsonObject.put("privateEvent", "" + eventPrivateInt);
                Log.d("JsonObject", jsonObject.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            /**
             * Create new asynctask
             */
            createEventRequest.makeApiRequestPut("POST", "createEvent", jsonObject.toString(), "createEvent");
        }
    }

    @Override
    public void showApiResponse(String apiResponse, String command) {
        switch (command){
            case "createEvent":
                createEventView.showToastToUser(apiResponse);
                break;

            case "getAllPlaces":
                createEventRequest.upDatePlaces(apiResponse);
                updateViewPlaceAdapter();
                break;
        }

    }

    private void updateViewPlaceAdapter() {
        ArrayList<String> arr = new ArrayList<>();
        for (Place p : createEventRequest.getPlaces()){
            arr.add(p.getName());
        }
        createEventView.updatePlaceAdapter(arr);
    }

    private boolean validDateFormat(String date){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        dateFormat.setLenient(false);

        try{
            dateFormat.parse(date);
        }catch (ParseException e){
            return false;
        }
        return true;
    }
}
