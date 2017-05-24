package com.sportify.editEvent.activity.presenter;

import android.content.SharedPreferences;
import android.util.Log;

import com.sportify.editEvent.activity.activity.EditEventView;
import com.sportify.editEvent.activity.request.EditEventRequest;
import com.sportify.editEvent.activity.request.EditEventRequestImpl;
import com.sportify.storage.Place;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import sportapp.pvt_sportapp.R;

/**
 * Created by Maja on 2017-05-22.
 */

public class EditEventPresenterImpl implements EditEventPresenter, EditEventRequest.OnEditEventFinishedListener{

    EditEventView editEventView;
    EditEventRequest editEventRequest;
    private SharedPreferences sharedPref;
    private String token = "";

    public EditEventPresenterImpl(EditEventView editEventView, SharedPreferences sharedPref){
        this.editEventView = editEventView;
        this.sharedPref = sharedPref;
        this.token = sharedPref.getString("jwt", "");
        editEventRequest = new EditEventRequestImpl(this, token);

        editEventRequest.makeApiRequestPut("PUT", "getallplaces", "\"category\": \"Simhall\"", "getAllPlaces");
    }

    @Override
    public void editEvent(int eventID) {

        editEventView.clearAllErrors();
        String eventName = editEventView.getEventName();
        String eventPrice = editEventView.getEventPrice();
        int eventPriceInt = 0;

        String eventDate = editEventView.getEventDate();
        String eventStartTime = editEventView.getEventStartTime();
        String eventEndTime = editEventView.getEventEndTime();
        String eventType = editEventView.getEventType();
        String eventMaxAttendance = editEventView.getEventMaxAttendance();
        String eventDescription = editEventView.getEventDescription();
        boolean eventPrivate = editEventView.getPrivateEvent();
        int eventPrivateInt = 0;

        if(eventPrivate){
            eventPrivateInt = 1;
        }

        if (eventName.isEmpty()) {
            editEventView.showEventNameEmptyError(R.string.event_name_empty_error);
        }
        else if(eventDate.isEmpty()){
            editEventView.showEventDateEmptyError(R.string.event_date_empty_error);
        }else if(eventStartTime.isEmpty()){
            editEventView.showEventStartTimeEmptyError(R.string.event_start_time_empty_error);
        }else if(eventEndTime.isEmpty()){
            editEventView.showEventEndTimeEmptyError(R.string.event_end_time_empty_error);
        }else if(eventType.isEmpty()) {
            editEventView.showEventTypeEmptyError(R.string.event_type_empty_error);
        }else if(!validDateFormat(eventDate)){
            editEventView.showEventDateFormatError(R.string.event_date_wrongformat_error);
        }else if(editEventView.getUserWroteSearch()){
            editEventView.showEventPlaceEmptyError(R.string.event_place_does_not_exist);
        }else {
            if (!eventPrice.isEmpty()) {
                try {
                    eventPriceInt = Integer.parseInt(eventPrice);
                } catch (NumberFormatException e) {
                    editEventView.showEventPriceWrongFormatError(R.string.event_price_wrongformat_error);
                    return;
                }
            }

            JSONObject jsonObject = new JSONObject();

            String placeID = editEventView.getEventPlaceId();

            try {
                jsonObject.put("eventID", "" + eventID);
                jsonObject.put("newEventName", eventName);
                jsonObject.put("newEventPrice", "" + eventPriceInt);
                jsonObject.put("newEventDate", "" + eventDate);
                jsonObject.put("newEventStartTime", "" + eventStartTime);
                jsonObject.put("newEventEndTime", "" + eventEndTime);
                jsonObject.put("newEventType", eventType);
                jsonObject.put("newEventPlace", placeID);
                if(!eventMaxAttendance.isEmpty()) {
                    jsonObject.put("newMaxAttendance", eventMaxAttendance);
                }
                jsonObject.put("newEventDescription", eventDescription);
                jsonObject.put("privateEvent", "" + eventPrivateInt);
                Log.d("JsonObject", jsonObject.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            editEventRequest.makeApiRequestPut("PUT", "changeEvent", jsonObject.toString(), "changeEvent");
        }
    }

    @Override
    public void deleteEvent(int eventID) {

        JSONObject jsonObject = new JSONObject();

        try{
            jsonObject.put("eventID", String.valueOf(eventID));
        }catch (JSONException e){
            e.printStackTrace();
        }

        editEventRequest.makeApiRequestPut("DELETE", "deleteEvent", jsonObject.toString(), "deleteEvent");
    }

    @Override
    public void showApiResponse(String apiResponse, String command) {

        switch (command){
            case "changeEvent" :
                System.out.println("response" + apiResponse.toString());
                editEventView.showToastToUser(apiResponse);
                editEventView.goToEventArea();
                break;
            case "getAllPlaces" :
                editEventRequest.upDatePlaces(apiResponse);
                updateViewPlaceAdapter();
                break;
            case "deleteEvent" :
                System.out.println("response delete " + apiResponse.toString());
                editEventView.showToastToUser(apiResponse);
                editEventView.goToUserArea();
                break;
        }
    }

    private void updateViewPlaceAdapter(){
        ArrayList<Place> arr = new ArrayList<>();
        for(Place p : editEventRequest.getPlaces()){
            arr.add(p);
        }
        editEventView.updatePlaceAdapter(arr);
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
