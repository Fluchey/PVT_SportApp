package com.sportify.profile.request;


import android.os.AsyncTask;

import com.sportify.util.Connector;

/**
 * Created by peradrianbergman on 2017-05-05.
 */

public class ProfileRequestImpl implements ProfileRequest{
    OnCreateProfileFinishedListener onCreateProfileFinishedListener;
    private String token = "";

    public ProfileRequestImpl(final OnCreateProfileFinishedListener onCreateProfileFinishedListener, String token){
        this.onCreateProfileFinishedListener = onCreateProfileFinishedListener;
        this.token = token;
    }

    public void makeApiRequest(String jsonMessage, String url) {
        ApiRequest apiRequest = (ApiRequest) new ApiRequest(this, url).execute(jsonMessage);
    }

    private class ApiRequest extends AsyncTask<String, ProfileRequestImpl, Void> {
        private ProfileRequestImpl profileRequestImpl;
        private String [] resultFromApi;
        private String connectorUrl;

        public ApiRequest(ProfileRequestImpl profileRequestImpl, String connectorUrl) {
            this.profileRequestImpl = profileRequestImpl;
            this.connectorUrl = connectorUrl;
        }

        @Override
        protected Void doInBackground(String... params) {
            resultFromApi = Connector.connect(connectorUrl,
                    "PUT", String.format(params[0]), token);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            profileRequestImpl.onCreateProfileFinishedListener.closeProgressDialog();
            profileRequestImpl.onCreateProfileFinishedListener.showApiResponse(resultFromApi);
        }
    }


}
