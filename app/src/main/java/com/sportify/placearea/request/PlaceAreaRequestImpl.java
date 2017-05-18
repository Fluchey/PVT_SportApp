package com.sportify.placearea.request;

import android.os.AsyncTask;

import com.sportify.storage.Place;
import com.sportify.util.Connector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by fluchey on 2017-05-18.
 */

public class PlaceAreaRequestImpl implements PlaceAreaRequest {
    private PlaceAreaRequest.OnRequestFinishedListener onRequestFinishedListener;
    private String token;
    private Place place;

    public PlaceAreaRequestImpl(OnRequestFinishedListener onRequestFinishedListener, String token) {
        this.onRequestFinishedListener = onRequestFinishedListener;
        this.token = token;
        place = new Place();
    }

    @Override
    public void loadPlaceData(String json) {
        JSONObject jsonObject = null;
        JSONArray jsonCategories = null;
        try{
            jsonObject = new JSONObject(json);
            /**
             * PLATS GREJER
             */
            place.setId(jsonObject.getString("placeId"));
            place.setName(jsonObject.getString("placeName"));

            /**
             * KATEGORIER TILL PLATSEN
             */
            jsonCategories = jsonObject.getJSONArray("categories");
            ArrayList<String> categories = new ArrayList<>();
            for(int i = 0; i < jsonCategories.length(); i++){
                categories.add(jsonCategories.getString(i));
            }
            place.setCategories(categories);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void makeApiRequestPut(String jsonMessage, String endURL, String method, String command) {
        ApiRequest apiRequest = (PlaceAreaRequestImpl.ApiRequest) new PlaceAreaRequestImpl.ApiRequest(this, command).execute(endURL, method, jsonMessage);
    }

    @Override
    public Place getPlace() {
        return this.place;
    }

    private class ApiRequest extends AsyncTask<String, PlaceAreaRequestImpl, Void> {
        private PlaceAreaRequestImpl placeAreaRequest;
        private String[] result;
        private String command;

        /**
         * @param placeAreaRequest
         * @param command         The command wich decides what to do in PresenterImpl after apiresponse has finished
         */
        public ApiRequest(PlaceAreaRequestImpl placeAreaRequest, String command) {
            this.placeAreaRequest = placeAreaRequest;
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
            placeAreaRequest.onRequestFinishedListener.showApiResponse(command, result);
        }
    }
}
