package com.sportify.maps.request;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.sportify.util.Connector;

import javax.inject.Inject;

/**
 * Created by fluchey on 2017-04-26.
 */

public class MapsRequestImpl implements MapsRequest {
    private onRequestFinishedListener onRequestFinishedListener;
    private String token = "";

    public MapsRequestImpl(final onRequestFinishedListener onRequestFinishedListener, String token) {
        this.onRequestFinishedListener = onRequestFinishedListener;
        this.token = token;
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
            mapsRequestImpl.onRequestFinishedListener.showApiResponse(result);
        }
    }
}
