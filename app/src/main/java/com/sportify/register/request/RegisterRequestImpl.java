package com.sportify.register.request;

import android.os.AsyncTask;

import com.sportify.util.Connector;

/**
 * Created by fluchey on 2017-04-16.
 */

public class RegisterRequestImpl implements RegisterRequest {
    OnCreateAccountFinishedListener onCreateAccountFinishedListener;
    private String token = "";

    public RegisterRequestImpl(final OnCreateAccountFinishedListener onCreateAccountFinishedListener, String token) {
        this.onCreateAccountFinishedListener = onCreateAccountFinishedListener;
        this.token = token;
    }

    public void makeApiRequest(String jsonMessage) {
        ApiRequest apiRequest = (ApiRequest) new ApiRequest(this).execute(jsonMessage);
    }


    private class ApiRequest extends AsyncTask<String, RegisterRequestImpl, Void> {
        private RegisterRequestImpl registerRequestImpl;
        private String [] resultFromApi;

        public ApiRequest(RegisterRequestImpl registerRequestImpl) {
            this.registerRequestImpl = registerRequestImpl;
        }

        @Override
        protected Void doInBackground(String... params) {
            resultFromApi = Connector.connect("https://pvt15app.herokuapp.com/api/signup",
                    "PUT", String.format(params[0]), token);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            registerRequestImpl.onCreateAccountFinishedListener.closeProgressDialog();
            registerRequestImpl.onCreateAccountFinishedListener.showApiResponse(resultFromApi);
        }
    }
}
