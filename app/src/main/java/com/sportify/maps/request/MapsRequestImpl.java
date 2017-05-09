package com.sportify.maps.request;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.sportify.storage.Event;
import com.sportify.storage.Place;
import com.sportify.util.Connector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by fluchey on 2017-04-26.
 */

public class MapsRequestImpl implements MapsRequest {
    private onRequestFinishedListener onRequestFinishedListener;
    private String token;
    private ArrayList<Place> currentSearchPlaces;
    private ArrayList<Event> currentSearchEvents;

    public MapsRequestImpl(final onRequestFinishedListener onRequestFinishedListener, String token) {
        this.onRequestFinishedListener = onRequestFinishedListener;
        this.token = token;

        currentSearchPlaces = new ArrayList<>();
        currentSearchEvents = new ArrayList<>();
    }

    @Override
    public void updateCurrentPlaces(String jsonMessage) {
        currentSearchPlaces.clear();
        JSONObject json = null;
        JSONArray placeArray = null;
        try {
            json = new JSONObject(jsonMessage);
            placeArray = json.getJSONArray("places");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (json == null || placeArray == null) {
            return;
        }
        try {
            for (int i = 0; i < placeArray.length(); i++) {
                JSONObject jsonObject = placeArray.getJSONObject(i);
                currentSearchPlaces.add(new Place(jsonObject.getString("place_id"), jsonObject.getString("name"), jsonObject.getString("category"), Double.parseDouble(jsonObject.getString("lat")), Double.parseDouble(jsonObject.getString("lon"))));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCurrentEvents(String jsonMessage) {
        currentSearchEvents.clear();
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
                currentSearchEvents.add(new Event(jsonObject.getInt("eventId"), jsonObject.getString("name"), jsonObject.getString("eventDate"), jsonObject.getString("startTime"), jsonObject.getString("endTime"), jsonObject.getString("eventDescription"), jsonObject.getString("place") ,  jsonObject.getInt("price"),
                        jsonObject.getString("eventType"), jsonObject.getInt("maxAttendance"), jsonObject.getBoolean("privateEvent")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void makeApiRequestPut(String jsonMessage, String endUrl, String method, String command) {
        ApiRequest apiRequest = (MapsRequestImpl.ApiRequest) new MapsRequestImpl.ApiRequest(this, command).execute(endUrl, method, jsonMessage);
    }

    @Override
    public void makeApiRequestGet(String method, String endURL, String command) {
        ApiRequest apiRequest = (MapsRequestImpl.ApiRequest) new MapsRequestImpl.ApiRequest(this, command).execute
                (method, endURL);
    }

    @Override
    public ArrayList<Place> getCurrentSearchPlaces() {
        return currentSearchPlaces;
    }

    @Override
    public ArrayList<Event> getEvents() {
        return currentSearchEvents;
    }

    private class ApiRequest extends AsyncTask<String, MapsRequestImpl, Void> {
        private MapsRequestImpl mapsRequestImpl;
        private String[] result;
        private String command;

        /**
         * @param mapsRequestImpl
         * @param command         The command wich decides what to do in PresenterImpl after apiresponse has finished
         */
        public ApiRequest(MapsRequestImpl mapsRequestImpl, String command) {
            this.mapsRequestImpl = mapsRequestImpl;
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
            mapsRequestImpl.onRequestFinishedListener.showApiResponse(command, result);
        }
    }
}
