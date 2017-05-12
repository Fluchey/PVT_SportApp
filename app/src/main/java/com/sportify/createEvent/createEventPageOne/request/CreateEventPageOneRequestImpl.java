package com.sportify.createEvent.createEventPageOne.request;

import android.os.AsyncTask;
import android.util.Log;

import com.sportify.storage.Place;
import com.sportify.util.Connector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Maja on 2017-04-18.
 */

public class CreateEventPageOneRequestImpl implements CreateEventPageOneRequest {

    OnCreateEventFinishedListener onCreateEventFinishedListener;
    private String token = "";
    private ArrayList<Place> places;

    public CreateEventPageOneRequestImpl(final OnCreateEventFinishedListener onCreateEventFinishedListener, String token){
        this.onCreateEventFinishedListener = onCreateEventFinishedListener;
        this.token = token;
        places = new ArrayList<>();
    }

    @Override
    public void upDatePlaces(String jsonMessage) {
        JSONObject json = null;
        JSONArray array = null;
        try {
            json = new JSONObject(jsonMessage);
            array = json.getJSONArray("places");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (json == null || array == null) {
            return;
        }


        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                places.add(new Place(jsonObject.getString("place_id"), jsonObject.getString("name"), jsonObject.getString("category"), Double.parseDouble(jsonObject.getString("lat")), Double.parseDouble(jsonObject.getString("lon"))));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }

    /**
     *
     * @param method PUT, POST, GET...
     * @param endUrl The end of the URL
     * @param jsonMessage   Jsonmessage
     * @param command   The command for the switch in showApiResponse
     */
    @Override
    public void makeApiRequestPut(String method, String endUrl, String jsonMessage, String command) {
        ApiRequest apiRequest = (ApiRequest) new ApiRequest(this, command).execute(method, endUrl, jsonMessage, command);
    }

    @Override
    public void makeApiRequestGet(String method, String endUrl, String command) {
        ApiRequest apiRequest = (ApiRequest) new ApiRequest(this, command).execute(method, endUrl, command);
    }

    private class ApiRequest extends AsyncTask<String, CreateEventPageOneRequestImpl, Void>{

        private CreateEventPageOneRequestImpl createEventRequestImpl;
        private String responseBody;
        private String command;

        public ApiRequest(CreateEventPageOneRequestImpl createEventRequestImpl, String command){
            this.createEventRequestImpl = createEventRequestImpl;
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

            if(params[0].equals("GET") || params[0].equals("DELETE")){
                String[] resultFromApi = Connector.connectGetOrDelete(params[0], "https://pvt15app.herokuapp.com/api/" + params[1], token);
//                String[] resultFromApi = Connector.connectGetOrDelete(params[0], "http://192.168.0.12:9000/api/" + params[1], token);
                responseBody = resultFromApi[0];
                Log.d("Token:", token);
                return null;
            }else {
            String[] resultFromApi = Connector.connect("https://pvt15app.herokuapp.com/api/" + params[1],
                    params[0], String.format(params[2]), token);
//                String[] resultFromApi = Connector.connect("http://192.168.0.12:9000/api/" + params[1],
//                        params[0], String.format(params[2]), token);
                responseBody = resultFromApi[0];
                return null;
            }
        }

        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
            createEventRequestImpl.onCreateEventFinishedListener.showApiResponse(responseBody, command);
        }
    }
}
