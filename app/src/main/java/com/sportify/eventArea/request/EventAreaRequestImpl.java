package com.sportify.eventArea.request;

import android.os.AsyncTask;

import com.sportify.maps.request.MapsRequestImpl;
import com.sportify.storage.Event;
import com.sportify.storage.Participant;
import com.sportify.storage.Place;
import com.sportify.storage.User;
import com.sportify.util.Connector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by fluchey on 2017-05-17.
 */

public class EventAreaRequestImpl implements EventAreaRequest {
    private EventAreaRequest.onRequestFinishedListener onRequestFinishedListener;
    private String token;

    private Event event;
    private Place place;
    private User user;
    private ArrayList<Participant> participants;

    public EventAreaRequestImpl(final onRequestFinishedListener onRequestFinishedListener, String token) {
        this.onRequestFinishedListener = onRequestFinishedListener;
        this.token = token;
        event = new Event();
        place = new Place();
        user = new User();
        participants = new ArrayList<>();
    }


    @Override
    public void makeApiRequestPut(String jsonMessage, String endUrl, String method, String command) {
        EventAreaRequestImpl.ApiRequest apiRequest = (EventAreaRequestImpl.ApiRequest) new EventAreaRequestImpl.ApiRequest(this, command).execute(endUrl, method, jsonMessage);
    }

    @Override
    public String[] loadEventData(String json) {

        String[] setToReturn = new String[2];

        JSONObject jsonObject = null;
        String attendance = "";
        String imageBase64 = "";
        try{
            /**
             * EVENT GREJER
             */
            jsonObject = new JSONObject(json);
            event.setId(jsonObject.getInt("eventId"));
            event.setEventName(jsonObject.getString("name"));
            event.setDate(jsonObject.getString("eventDate"));
            event.setStartTime(jsonObject.getString("startTime"));
            event.setEndTime(jsonObject.getString("endTime"));
            event.setPrice(jsonObject.getInt("price"));
            event.setEventDescription(jsonObject.getString("eventDescription"));
            event.setMaxAttendance(jsonObject.getInt("maxAttendance"));
            event.setPrivateEvent(jsonObject.getBoolean("privateEvent"));
            event.setEventType(jsonObject.getString("eventType"));

            /**
             * PLATS GREJER
             */
            JSONObject placeJson = jsonObject.getJSONObject("place");
            place.setId(placeJson.getString("placeId"));
            place.setName(placeJson.getString("placeName"));

            /**
             * CREATOR GREJER
             */
            JSONObject creatorJson = jsonObject.getJSONObject("creator");
            user.setFirstName(creatorJson.getString("firstName"));
            user.setLastName(creatorJson.getString("lastName"));
            user.setUserId(creatorJson.getInt("profileId"));

            attendance = jsonObject.getString("attendance");
            imageBase64 = jsonObject.getString("imageBase64");

            /**
             * PARTICIPANTS
             */
            JSONArray participantArray = jsonObject.getJSONArray("participants");
            if(!(participants == null)) {

                for (int i = 0; i < participantArray.length(); i++){
                    JSONObject participant= participantArray.getJSONObject(i);
                    participants.add(new Participant(participant.getString("firstname"), participant.getString("lastname"),
                                     participant.getString("imageBase64"), participant.getInt("participantid"), participant.getString("status")));
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        setToReturn[0] = attendance;
        setToReturn[1] = imageBase64;
        return setToReturn;
    }

    @Override
    public Event getEvent() {
        return event;
    }

    @Override
    public Place getPlace() {
        return place;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public ArrayList<Participant> getParticipants() {
        return participants;
    }

    private class ApiRequest extends AsyncTask<String, MapsRequestImpl, Void> {
        private EventAreaRequestImpl eventAreaRequest;
        private String[] result;
        private String command;

        /**
         * @param eventAreaRequest
         * @param command         The command wich decides what to do in PresenterImpl after apiresponse has finished
         */
        public ApiRequest(EventAreaRequestImpl eventAreaRequest, String command) {
            this.eventAreaRequest = eventAreaRequest;
            this.command = command;
        }

        /**
         * @param params params [0] = method (POST, PUT, GET..)
         *               params [1] = endUrl
         *               (params [2] = jsonMessage) - optional, only if PUT or POST
         * @return
         */
        @Override
        protected Void doInBackground(String... params) {
            if (params[0].equals("GET") || params[0].equals("DELETE")) {
                result = Connector.connectGetOrDelete(params[0], "https://pvt15app.herokuapp.com/api/" + params[1], token);
//                result = Connector.connectGetOrDelete(params[0], "http://192.168.0.12:9000/api/" + params[1], token);
                return null;
            } else {
                result = Connector.connect("https://pvt15app.herokuapp.com/api/" + params[0],
                        params[1], params[2], token);
//                result = Connector.connect("http://192.168.0.12:9000/api/" + params[0],
//                        params[1], params[2], token);
                return null;
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            eventAreaRequest.onRequestFinishedListener.showApiResponse(command, result);
        }
    }
}
