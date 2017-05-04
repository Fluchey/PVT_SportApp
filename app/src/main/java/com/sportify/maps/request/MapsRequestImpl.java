package com.sportify.maps.request;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.sportify.storage.Place;
import com.sportify.storage.PlaceStorage;
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
    private String token = "";
//    private ArrayList<Place> places;
    private ArrayList<Place> currentSearchPlaces;

    public MapsRequestImpl(final onRequestFinishedListener onRequestFinishedListener, String token) {
        this.onRequestFinishedListener = onRequestFinishedListener;
        this.token = token;

//        places = new ArrayList<>();
        currentSearchPlaces = new ArrayList<>();
    }

    @Override
    public void updateCurrentSearchPlaces(String jsonMessage) {
        currentSearchPlaces.clear();
        JSONObject json = null;
        JSONArray array = null;
        try {
            json = new JSONObject(jsonMessage);
            array = json.getJSONArray("places");
            Log.d("JsonArr: ", array.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (json == null || array == null) {
            return;
        }


        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                currentSearchPlaces.add(new Place(jsonObject.getString("name"), jsonObject.getString("category"), Double.parseDouble(jsonObject.getString("lat")), Double.parseDouble(jsonObject.getString("lon"))));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (Place p : currentSearchPlaces) {
            Log.d("Place", p.toString());
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

//    @Override
//    public void updateAllPlaces(String jsonMessage) {
//        JSONObject json = null;
//        JSONArray array = null;
//        try {
//            json = new JSONObject(jsonMessage);
//            array = json.getJSONArray("places");
//            Log.d("JsonArr: ", array.toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        if (json == null || array == null) {
//            return;
//        }
//
//
//        try {
//            for (int i = 0; i < array.length(); i++) {
//                JSONObject jsonObject = array.getJSONObject(i);
//                places.add(new Place(jsonObject.getString("name"), jsonObject.getString("category"), Double.parseDouble(jsonObject.getString("lat")), Double.parseDouble(jsonObject.getString("lon"))));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        for (Place p : places) {
//            Log.d("Place", p.toString());
//        }
//    }

    @Override
    public ArrayList<Place> getCurrentSearchPlaces() {
        return currentSearchPlaces;
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
                result = Connector.connectGetOrDelete(params[0], "http://192.168.0.5:9000/api/" + params[1], token);
                return null;
            } else {
                result = Connector.connect("http://192.168.0.5:9000/api/" + params[0],
                        params[1], params[2], token);
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
