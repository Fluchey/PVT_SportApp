package com.sportify.createEvent.createEventPageOne.presenter;

import android.content.SharedPreferences;
import android.util.Log;

import com.sportify.createEvent.createEventPageOne.activity.CreateEventPageOneView;
import com.sportify.createEvent.createEventPageOne.request.CreateEventPageOneRequest;
import com.sportify.createEvent.createEventPageOne.request.CreateEventPageOneRequestImpl;
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

public class CreateEventPageOnePresenterImpl implements CreateEventPageOnePresenter, CreateEventPageOneRequest.OnCreateEventFinishedListener{
    private CreateEventPageOneView createEventPageOneView;
    private CreateEventPageOneRequest createEventPageOneRequest;
    private SharedPreferences sharedPref;
    private String token = "";

    public CreateEventPageOnePresenterImpl(CreateEventPageOneView createEventPageOneView, SharedPreferences sharedPref){
        this.createEventPageOneView = createEventPageOneView;
        this.sharedPref = sharedPref;
        this.token = sharedPref.getString("jwt", "");
        createEventPageOneRequest = new CreateEventPageOneRequestImpl(this, token);

        /**
         * This runs oncreate and updates the ArrayAdapter with all the current places in the database.
         * Fulaste lösningen i världen, skickar med ett bluff JSON objekt. Plz fix
         */
        createEventPageOneRequest.makeApiRequestPut("PUT", "getallplaces", "\"category\": \"Simhall\"", "getAllPlaces");
    }

    @Override
    public void createEvent() {
        createEventPageOneView.clearAllErrors();
        String eventName = createEventPageOneView.getEventName();
        String eventPriceEt = createEventPageOneView.getEventPrice();
        int eventPrice = 0;

        String eventDate = createEventPageOneView.getEventDate();
        String eventStartTime = createEventPageOneView.getEventStartTime();
        String eventEndTime = createEventPageOneView.getEventEndTime();
        String eventType = createEventPageOneView.getEventType();
        String eventMaxAttendance = createEventPageOneView.getEventMaxAttendance();
        String eventDescription = createEventPageOneView.getEventDescription();
        boolean eventPrivate = createEventPageOneView.getPrivateEvent();
        int eventPrivateInt = 0;

        if(eventPrivate){
            eventPrivateInt = 1;
        }
        if (eventName.isEmpty()) {
            createEventPageOneView.showEventNameEmptyError(R.string.event_name_empty_error);
        }

        //TODO: Lägg in slutdatum
        else if(eventDate.isEmpty()){
            createEventPageOneView.showEventDateEmptyError(R.string.event_date_empty_error);
        }
        else if(!validDateFormat(eventDate)){
            createEventPageOneView.showEventDateFormatError(R.string.event_date_wrongformat_error);
        }
        else if(eventStartTime.isEmpty()){
            createEventPageOneView.showEventStartTimeEmptyError(R.string.event_start_time_empty_error);
        }
        else if(eventEndTime.isEmpty()){
            createEventPageOneView.showEventEndTimeEmptyError(R.string.event_end_time_empty_error);
        }
        else if(eventType.isEmpty()) {
            createEventPageOneView.showEventTypeEmptyError(R.string.event_type_empty_error);
        }
        else if(createEventPageOneView.getUserWroteSearch()){
            createEventPageOneView.showEventPlaceEmptyError(R.string.event_place_does_not_exist);
        }else{
            if (!eventPriceEt.isEmpty()) {
                try {
                    eventPrice = Integer.parseInt(eventPriceEt);
                } catch (NumberFormatException e) {
                    createEventPageOneView.showEventPriceWrongFormatError(R.string.event_price_wrongformat_error);
                    return;
                }
            }
            JSONObject jsonObject = new JSONObject();
            String placeID = createEventPageOneView.getEventPlaceId();
//            for(Place p : createEventPageOneRequest.getPlaces()){
//                if(p.getName().equals(eventPlace)){
//                    placeId = p.getId();
//                }
//            }
            try {
                jsonObject.put("eventName", eventName);
                jsonObject.put("eventPrice", "" + eventPrice);
                jsonObject.put("eventDate", "" + eventDate);
                jsonObject.put("eventStartTime", "" + eventStartTime);
                jsonObject.put("eventEndTime", "" + eventEndTime);
                jsonObject.put("eventType", eventType);
                jsonObject.put("eventPlace", placeID);
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
            Log.d("Event:", jsonObject.toString());
            createEventPageOneRequest.makeApiRequestPut("POST", "createEvent", jsonObject.toString(), "createEvent");
        }
    }

    @Override
    public void showApiResponse(String apiResponse, String command) {

        JSONObject json = null;
        String message = "";
        int eventID = -1;

        try{
            json = new JSONObject(apiResponse);
            message = json.getString("message");
            eventID = json.getInt("eventID");

        }catch (JSONException e){
            e.printStackTrace();
        }

        switch (command){
            case "createEvent":
                createEventPageOneView.setEventID(eventID);
                createEventPageOneView.showToastToUser(message);
                break;

            case "getAllPlaces":
                createEventPageOneRequest.upDatePlaces(apiResponse);
                updateViewPlaceAdapter();
                break;
        }

    }

    private void updateViewPlaceAdapter() {
        ArrayList<Place> arr = new ArrayList<>();
        for (Place p : createEventPageOneRequest.getPlaces()){
            arr.add(p);
        }
        createEventPageOneView.updatePlaceAdapter(arr);
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
