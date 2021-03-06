package com.sportify.userArea.request;

import android.os.AsyncTask;
import android.util.Log;

import com.sportify.storage.Event;
import com.sportify.util.Connector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by fluchey on 2017-04-20.
 */

public class UserAreaRequestImpl implements UserAreaRequest {
    private String token = "";
    UserAreaRequest.OnRequestFinishedListener onRequestFinishedListener;
    private ArrayList<Event> events;
    private HashMap<Integer, String> creator;
    private HashMap<Integer, String> placeName;
    private ArrayList<String> eventImages;

    public UserAreaRequestImpl(String token, OnRequestFinishedListener listener){
        this.token = token;
        onRequestFinishedListener = listener;
        events = new ArrayList<>();
        creator = new HashMap<>();
        placeName = new HashMap<>();
        eventImages = new ArrayList<>();
    }

    @Override
    public void makeApiRequestPut(String jsonMessage, String endURL, String method, String command) {
        ApiRequest apiRequest = (UserAreaRequestImpl.ApiRequest) new UserAreaRequestImpl.ApiRequest(this, command).execute(endURL, method, jsonMessage);
    }

    @Override
    public void updateEvents(String jsonMessage) {
        JSONObject json = null;
        JSONArray eventArray = null;
        try {
            json = new JSONObject(jsonMessage);
            eventArray = json.getJSONArray("events");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (json == null ||  eventArray == null) {
            return;
        }
        try {
            for (int i = 0; i < eventArray.length(); i++) {
                JSONObject jsonObject = eventArray.getJSONObject(i);
                events.add(new Event(jsonObject.getInt("eventId"),
                        jsonObject.getString("name"),
                        jsonObject.getString("eventDate"),
                        jsonObject.getString("startTime"),
                        jsonObject.getString("endTime"),
                        jsonObject.getString("eventDescription"),
                        jsonObject.getString("place"),
                        jsonObject.getInt("price"),
                        jsonObject.getString("eventType"),
                        jsonObject.getInt("maxAttendance"),
                        jsonObject.getBoolean("privateEvent"),
                        jsonObject.getString("imageBase64")));

                creator.put(jsonObject.getInt("eventId"), (jsonObject.getString("creatorFirstName") + " " + jsonObject.getString("creatorLastName")));

                placeName.put(jsonObject.getInt("eventId"), jsonObject.getString("placeName"));

//                String imageBase64 = "No Image";
//                if(!jsonObject.getString("imageBase64").isEmpty()) {
//                    imageBase64 = jsonObject.getString("imageBase64");
//                }
                eventImages.add(jsonObject.getString("imageBase64"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(Event e: events){
            Log.d("Event", e.getEventName());
        }
    }

    @Override
    public ArrayList<Event> getEvents() {
        return events;
    }

    @Override
    public HashMap<Integer, String> getCreator() {
        return creator;
    }

    @Override
    public HashMap<Integer, String> getPlaceName() {
        return placeName;
    }

    @Override
    public ArrayList<String> getEventImages() {
        return eventImages;
    }

    private class ApiRequest extends AsyncTask<String, UserAreaRequestImpl, Void> {
        private UserAreaRequestImpl userAreaRequest;
        private String[] result;
        private String command;

        /**
         * @param userAreaRequest
         * @param command         The command wich decides what to do in PresenterImpl after apiresponse has finished
         */
        public ApiRequest(UserAreaRequestImpl userAreaRequest, String command) {
            this.userAreaRequest = userAreaRequest;
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
//                result = Connector.connect("http://192.168.0.5:9000/api/" + params[0],
//                        params[1], params[2], token);
                return null;
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            userAreaRequest.onRequestFinishedListener.showApiResponse(command, result);
        }
    }
}