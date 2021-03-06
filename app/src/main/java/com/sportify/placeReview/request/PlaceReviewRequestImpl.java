package com.sportify.placeReview.request;

import android.os.AsyncTask;
import android.util.Log;

import com.sportify.util.Connector;
import com.sportify.storage.PlaceReview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by rasmu on 02/05/2017.
 */

public class PlaceReviewRequestImpl implements PlaceReviewRequest {
    private onRequestFinishedListener onRequestFinishedListener;
    private String token;

    private ArrayList<PlaceReview> allReviews;

    public PlaceReviewRequestImpl (final onRequestFinishedListener onRequestFinishedListener, String token){
        this.onRequestFinishedListener = onRequestFinishedListener;
        this.token = token;

        allReviews = new ArrayList<>();
    }

    @Override
    public void updateAllReviews(String jsonMessage){
        allReviews.clear();
        JSONObject json = null;
        JSONArray reviewArray = null;
        try {
            json = new JSONObject(jsonMessage);
            reviewArray = json.getJSONArray("placeReviews");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (json == null || reviewArray == null) {
            return;
        }
        try {
            for (int i = 0; i < reviewArray.length(); i++) {
                JSONObject jsonObject = reviewArray.getJSONObject(i);
                allReviews.add(new PlaceReview(jsonObject.getInt("placeName"), jsonObject.getString("comment"), Integer.parseInt(jsonObject.getString("profileId")), "", Float.parseFloat(jsonObject.getString("rating")), ""));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void makeApiRequestPut(String jsonMessage, String endURL, String method, String command){
        ApiRequest apiRequest = (ApiRequest) new ApiRequest(this, command).execute(method, endURL, jsonMessage);
    }

    @Override
    public void makeApiRequestGet(String method, String endURL, String command){
        ApiRequest apiRequest = (ApiRequest) new ApiRequest(this, command).execute(method, endURL);
    }

    @Override
    public void submitReview(double rating, String comment, int userId, int placeId){
        JSONObject json = new JSONObject();
        try{
            json.put("profileId", userId);
            json.put("placeName", placeId);
            json.put("comment", comment);
            json.put("rating", rating);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        makeApiRequestPut(json.toString(), "addReview", "PUT", "addReview");
    }

    @Override
    public void updateReview(double rating, String comment, int userId, int placeId){
        JSONObject json = new JSONObject();
        try{
            json.put("profileId", userId);
            json.put("placeName", placeId);
            json.put("comment", comment);
            json.put("rating", rating);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        makeApiRequestPut(json.toString(), "updateReview", "PUT", "addReview");
    }

    @Override
    public void renewAllReviews(){
        JSONObject json = new JSONObject();
        try {
            json.put("profileId", -1);
            json.put("placeName", -1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /**
         * First get all existing places
         */
        makeApiRequestPut(json.toString(), "getReviews", "PUT", "updateReviews");
    }

    @Override
    public void renewAllReviews(int userId, int placeId){
        JSONObject json = new JSONObject();
        try {
            json.put("profileId", userId);
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

    private class ApiRequest extends AsyncTask<String, PlaceReviewRequestImpl, Void> {
        private PlaceReviewRequestImpl placeReviewRequestImpl;
        private String[] result;
        private String command;

        /**
         * @param placeReviewRequestImpl
         * @param command                The command which decides what to do in PresenterImpl after apiresponse has finished
         */
        public ApiRequest(PlaceReviewRequestImpl placeReviewRequestImpl, String command) {
            this.placeReviewRequestImpl = placeReviewRequestImpl;
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
//                result = Connector.connectGetOrDelete(params[0], "http://127.0.0.1:9000/api/" + params[1], token);
                return null;
            } else {
                result = Connector.connect("https://pvt15app.herokuapp.com/api/" + params[1],
                        params[0], params[2], token);
//                result = Connector.connect("http://127.0.0.1:9000/api/" + params[1],
//                        params[0], params[2], token);
                return null;
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            placeReviewRequestImpl.onRequestFinishedListener.showApiResponse(command, result);
        }
    }
}