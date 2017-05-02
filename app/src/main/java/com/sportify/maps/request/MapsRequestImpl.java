package com.sportify.maps.request;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.sportify.storage.PlaceStorage;
import com.sportify.util.Connector;

import javax.inject.Inject;

/**
 * Created by fluchey on 2017-04-26.
 */

public class MapsRequestImpl implements MapsRequest {
    private onRequestFinishedListener onRequestFinishedListener;
    private String token = "";
    private PlaceStorage places;

    public MapsRequestImpl(final onRequestFinishedListener onRequestFinishedListener, String token) {
        this.onRequestFinishedListener = onRequestFinishedListener;
        this.token = token;

        /**
         * Fetch all places from the database, for autocompletion when searching for a place
         */
        places = new PlaceStorage();
        ApiRequest apiRequest = (MapsRequestImpl.ApiRequest) new MapsRequestImpl.ApiRequest(this).execute
        ("", "getallplaces");
    }

    @Override
    public void makeApiRequest(String jsonMessage, String endUrl) {
        ApiRequest apiRequest = (MapsRequestImpl.ApiRequest) new MapsRequestImpl.ApiRequest(this).execute(jsonMessage, endUrl);
    }

    private class ApiRequest extends AsyncTask<String, MapsRequestImpl, Void> {
        private MapsRequestImpl mapsRequestImpl;

        private String[] result;

        public ApiRequest(MapsRequestImpl mapsRequestImpl) {
            this.mapsRequestImpl = mapsRequestImpl;
        }

        /**
         * @param params
         * params [0] = jsonMessage
         * params [1] = endUrl
         * @return
         */
        @Override
        protected Void doInBackground(String... params) {
            result = Connector.connect("https://pvt15app.herokuapp.com/api/" + params[1],
                    "PUT", params[0], token);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mapsRequestImpl.onRequestFinishedListener.closeLoadIndicator();
            mapsRequestImpl.onRequestFinishedListener.showApiResponse(result);
        }
    }
}
