package com.sportify.createEvent.presenter;

import android.content.SharedPreferences;
import android.util.Log;

import com.sportify.createEvent.activity.CreateEventView;
import com.sportify.createEvent.request.CreateEventRequest;
import com.sportify.createEvent.request.CreateEventRequestImpl;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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
        this.token = sharedPref.getString("Token", "");
        createEventRequest = new CreateEventRequestImpl(this, token);
    }

    @Override
    public void createEvent() {

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
        else if (eventName.isEmpty()) {
            createEventView.showEventNameEmptyError();
        }
        else if(eventDate.isEmpty()){
            createEventView.showEventDateEmptyError();
        }
        else if(!validDateFormat(eventDate)){
            createEventView.showEventDateFormatError();
        }
        else if(eventStartTime.isEmpty()){
            createEventView.showEventStartTimeEmptyError();
        }
        else if(eventEndTime.isEmpty()){
            createEventView.showEventEndTimeEmptyError();
        }
        else if(eventType.isEmpty()) {
            createEventView.showEventTypeEmptyError();
        }
        else if(eventPlace.isEmpty()){
            createEventView.showEventPlaceEmptyError();
        }else{
            if (!eventPriceEt.isEmpty()) {
                try {
                    eventPrice = Integer.parseInt(eventPriceEt);
                } catch (NumberFormatException e) {
                    createEventView.showEventPriceWrongFormatError();
                    return;
                }
            }
            createEventView.clearMessageTv();
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
            createEventRequest.makeApiRequest(jsonObject.toString());
        }
    }

    @Override
    public void showApiResponse(String apiResponse) {
        createEventView.showApiRequestMessage(apiResponse);
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