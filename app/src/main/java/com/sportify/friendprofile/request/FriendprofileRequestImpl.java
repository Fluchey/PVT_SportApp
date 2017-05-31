package com.sportify.friendprofile.request;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.sportify.placeReview.request.PlaceReviewRequestImpl;
import com.sportify.storage.Event;
import com.sportify.storage.Profile;
import com.sportify.util.Connector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by rasmu on 25/05/2017.
 */

public class FriendprofileRequestImpl implements FriendprofileRequest {
    private SharedPreferences sharedPrefs;
    private onRequestFinishedListener onRequestFinishedListener;
    private String token;

    private Profile profile;

    private ArrayList<Event> events;
    private HashMap<Integer, String> creator;
    private HashMap<Integer, String> placeName;
    private ArrayList<String> eventImages;

    public FriendprofileRequestImpl(final onRequestFinishedListener onRequestFinishedListener, SharedPreferences sharedPrefs, String token) {
        this.onRequestFinishedListener = onRequestFinishedListener;
        this.sharedPrefs = sharedPrefs;
        this.token = token;

        events = new ArrayList<>();
        creator = new HashMap<>();
        placeName = new HashMap<>();
        eventImages = new ArrayList<>();
    }

    public void updateProfile(int profileId){
        JSONObject json = new JSONObject();
        try {
            json.put("profileID", profileId +"");
            Log.d("profile id", profileId +"");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        makeApiRequestPut(json.toString(), "getProfileInfo", "PUT", "showInfo");

        makeApiRequestPut(json.toString(), "isfriend", "PUT", "isfriend");
    }

    public void setProfile(String jsonMessage, int userId){
        JSONObject json = null;
        String firstname = "";
        String lastname = "";
        String description = "";
        List<String> interests = null;
        String image = "";
        int id = userId;
        String age = "";

        try {
            json = new JSONObject(jsonMessage);
            firstname = json.getString("firstName");
            lastname = json.getString("lastName");
            description = json.getString("userBio");
            image = json.getString("imageBase64");
            interests = com.sportify.util.Profile.getInterestsListFromString(json.get("interests").toString());
            age = json.getString("dateOfBirth");

            profile = new Profile(firstname, lastname, description, interests, id, age, image);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Profile getProfile(){
         return profile;
    }

    @Override
    public ArrayList<Event> getEvents(){ return events; }

    @Override
    public void updateEvents(int userId){
        JSONObject json = new JSONObject();
        try {
            json.put("profileId", "" + userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        makeApiRequestPut(json.toString(), "geteventforuser", "PUT", "geteventforuser");
    }
    @Override
    public void setEvents(String jsonMessage){
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

    @Override
    public boolean isFriend(String jsonMessage){
        Log.d("isFriend json", jsonMessage);
        JSONObject json = null;
        boolean friend = false;
        try {
            json = new JSONObject(jsonMessage);
            friend = json.getBoolean("friend");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return friend;
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
    public void makeApiRequestAddFriend(String method, String endUrl, String jsonMessage, String command) {
        ApiRequest apiRequest = (ApiRequest) new ApiRequest(this, command).execute(method, endUrl, jsonMessage);
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
