package com.sportify.event.presenter;

import android.util.Log;

import com.sportify.event.activity.CreateEventView;
import com.sportify.event.request.CreateEventRequest;
import com.sportify.event.request.CreateEventRequestImpl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

/**
 * Created by Maja on 2017-04-18.
 */

public class CreateEventPresenterImpl implements CreateEventPresenter, CreateEventRequest.OnCreateEventFinishedListener{
    private CreateEventView createEventView;
    private CreateEventRequest createEventRequest;

    public CreateEventPresenterImpl(CreateEventView createEventView){
        this.createEventView = createEventView;
        createEventRequest = new CreateEventRequestImpl(this);
    }

    @Override
    public void createEvent() {

        String eventName = createEventView.getEventName();
        String eventPriceEt = createEventView.getEventPrice();
        int eventPrice = 0;
        String eventDescription = createEventView.getEventDescription();

        if (eventName.isEmpty()) {
            createEventView.showEventNameEmptyError();
        } else if (!eventPriceEt.isEmpty()) {
            try {
                eventPrice = Integer.parseInt(eventPriceEt);
            } catch (NumberFormatException e) {
                createEventView.showEventPriceWrongFormatError();
                return;
            }
        } else {
            createEventView.clearMessageTv();
            JSONObject jsonObject = new JSONObject();
            try {
                Random rn = new Random();
                String id = "" + rn.nextInt(1) + 1000;

                jsonObject.put("event_id", "" + id);
                jsonObject.put("event_namn", eventName);
                jsonObject.put("event_beskrivning", eventDescription);
                jsonObject.put("event_pris", "" + eventPrice);
                Log.d("JsonObject", jsonObject.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            createEventRequest.makeApiRequest(jsonObject.toString());
        }
    }

    @Override
    public void showApiResponse(String apiResponse) {
        createEventView.showApiRequestMessage(apiResponse);
    }
}
