package com.sportify.createEvent.presenter;

import android.content.SharedPreferences;
import android.util.Log;

import com.sportify.createEvent.activity.CreateEventView;
import com.sportify.createEvent.request.CreateEventRequest;
import com.sportify.createEvent.request.CreateEventRequestImpl;

import org.json.JSONException;
import org.json.JSONObject;

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

        //TODO Måste skicka med profileID när eventet skapas. Fånga vem som är inloggad.
        int creatorID = 110;

        String eventName = createEventView.getEventName();
        String eventPriceEt = createEventView.getEventPrice();
        int eventPrice = 0;
        String eventDate = createEventView.getEventDate();
        String eventStartTime = createEventView.getEventStartTime();
        String eventEndTime = createEventView.getEventEndTime();
        String eventType = createEventView.getEventType();
        String eventMaxAttendance = createEventView.getEventMaxAttendance();
        String eventDescription = createEventView.getEventDescription();
        boolean eventPrivate = createEventView.getPrivateEvent();
        int eventPrivateInt = 0;

        if(eventPrivate){
            eventPrivateInt = 1;
        }
        if (eventName.isEmpty()) {
            createEventView.showEventNameEmptyError();
        }
        if(eventDate.isEmpty()){
            createEventView.showEventDateEmptyError();
        }
        if(eventStartTime.isEmpty()){
            createEventView.showEventStartTimeEmptyError();
        }
        if(eventEndTime.isEmpty()){
            createEventView.showEventEndTimeEmptyError();
        }
        if(eventType.isEmpty()){
            createEventView.showEventTypeEmptyError();
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
//                jsonObject.put("eventID", "" + eventName);
                jsonObject.put("eventCreator", "" + creatorID);
                jsonObject.put("eventName", eventName);
                jsonObject.put("eventPrice", "" + eventPrice);
                jsonObject.put("eventDate", "" + eventDate);
                jsonObject.put("eventStartTime", "" + eventStartTime);
                jsonObject.put("eventEndTime", "" + eventEndTime);
                jsonObject.put("eventType", eventType);
                if(!eventMaxAttendance.isEmpty()) {
                    jsonObject.put("eventMaxAttendance", eventMaxAttendance);
                }
                jsonObject.put("eventDescription", eventDescription);
                jsonObject.put("privateEvent", "" + eventPrivateInt);


                String noUpdate = "noUpdate";
                jsonObject.put("eventID", "123");
                jsonObject.put("newEventName", eventName);
                jsonObject.put("newEventDescription", noUpdate);
                jsonObject.put("newEventDate", noUpdate);
                jsonObject.put("newStartTime", noUpdate);
                jsonObject.put("newEndTime", noUpdate);
                jsonObject.put("newPrice", noUpdate);
                jsonObject.put("newEventType", noUpdate);
                jsonObject.put("newEventImage", noUpdate);
                jsonObject.put("newMaxAttendance", noUpdate);
                jsonObject.put("privateEvent", noUpdate);


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
}
