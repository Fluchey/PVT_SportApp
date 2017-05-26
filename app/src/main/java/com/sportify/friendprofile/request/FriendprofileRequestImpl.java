package com.sportify.friendprofile.request;

import android.os.AsyncTask;

import com.sportify.placeReview.request.PlaceReviewRequest;
import com.sportify.placeReview.request.PlaceReviewRequestImpl;
import com.sportify.storage.Profile;
import com.sportify.util.Connector;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by rasmu on 25/05/2017.
 */

public class FriendprofileRequestImpl implements FriendprofileRequest {
    private onRequestFinishedListener onRequestFinishedListener;
    private String token;

    private Profile profile;

    public FriendprofileRequestImpl(final onRequestFinishedListener onRequestFinishedListener, String token) {
        this.onRequestFinishedListener = onRequestFinishedListener;
        this.token = token;
    }

    public void updateProfile(int userId){
        JSONObject json = new JSONObject();
        try {
            json.put("profileID", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /**
         * First get all existing places
         */
        makeApiRequestPut(json.toString(), "getProfileInfo", "PUT", "showInfo");
    }

    public void setProfile(String jsonMessage){
        JSONObject json = null;
        String firstname = "";
        String lastname = "";
        String description = "";
        List<String> interests = null;
        String image = "";
        int id = -1;
        int age = -1;

        try {
            json = new JSONObject(jsonMessage);
            firstname = json.getString("firstName");
            lastname = json.getString("lastName");
            description = json.getString("userBio");
            interests = (List) json.get("interests");
            image = json.getString("imageBase64");

            profile = new Profile(firstname, lastname, description, interests, id, age, image);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Profile getProfile(){
         return profile;
    }

    @Override
    public void makeApiRequestPut(String jsonMessage, String endURL, String method, String command){
        ApiRequest apiRequest = (ApiRequest) new ApiRequest(this, command).execute(method, endURL, jsonMessage);
    }

    @Override
    public void makeApiRequestGet(String method, String endURL, String command){
        ApiRequest apiRequest = (ApiRequest) new ApiRequest(this, command).execute(method, endURL);
    }

    private class ApiRequest extends AsyncTask<String, PlaceReviewRequestImpl, Void> {
        private FriendprofileRequest request;
        private String[] result;
        private String command;

        /**
         * @param request
         * @param command                The command which decides what to do in PresenterImpl after apiresponse has finished
         */
        public ApiRequest(FriendprofileRequest request, String command) {
            this.request = request;
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
            onRequestFinishedListener.showApiResponse(command, result);
        }
    }
}
