package com.sportify.placearea.request;

import android.os.AsyncTask;
import android.util.Log;

import com.sportify.storage.Place;
import com.sportify.storage.PlaceReview;
import com.sportify.util.Connector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by fluchey on 2017-05-18.
 */

public class PlaceAreaRequestImpl implements PlaceAreaRequest {
    private PlaceAreaRequest.OnRequestFinishedListener onRequestFinishedListener;
    private String token;
    private Place place;

    private ArrayList<PlaceReview> allReviews;

    public PlaceAreaRequestImpl(OnRequestFinishedListener onRequestFinishedListener, String token) {
        this.onRequestFinishedListener = onRequestFinishedListener;
        this.token = token;
        place = new Place();
        allReviews = new ArrayList<>();
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
    public void updateAllReviews(String jsonMessage){
        allReviews.clear();
        JSONObject json = null;
        JSONArray reviewArray = null;
        JSONArray nameArray = null;
        HashMap<Integer, String> nameMap = new HashMap();
        try {
            json = new JSONObject(jsonMessage);
            reviewArray = json.getJSONArray("placeReviews");
            nameArray = json.getJSONArray("userNames");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (json == null || reviewArray == null) {
            return;
        }
        try {
            for (int i = 0; i < nameArray.length(); i++) {
                JSONObject jsonObject = nameArray.getJSONObject(i);
                Log.d("Name: ", jsonObject.getString("name"));
                nameMap.put(jsonObject.getInt("profileId"), jsonObject.getString("name"));
            }
            for (int i = 0; i < reviewArray.length(); i++) {
                JSONObject jsonObject = reviewArray.getJSONObject(i);
                int profileId = Integer.parseInt(jsonObject.getString("profileId"));
                allReviews.add(new PlaceReview(jsonObject.getInt("placeName"), jsonObject.getString("comment"), profileId, nameMap.get(profileId), Float.parseFloat(jsonObject.getString("rating")), jsonObject.getString("date")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void renewAllReviews(int placeId){
        JSONObject json = new JSONObject();
        try {
            json.put("profileId", -1);
            json.put("placeName", placeId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /**
         * First get all existing places
         */
        makeApiRequestPut(json.toString(), "getReviews", "PUT", "updateReviews");
    }

    @Override
    public ArrayList<PlaceReview> getAllReviews(){ return allReviews; }

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
