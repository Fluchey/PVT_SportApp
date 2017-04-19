package com.sportify.register.request;

import android.os.AsyncTask;

import com.sportify.util.Connector;

/**
 * Created by fluchey on 2017-04-16.
 */

public class RegisterRequestImpl implements RegisterRequest {
    OnCreateAccountFinishedListener onCreateAccountFinishedListener;

    public RegisterRequestImpl(final OnCreateAccountFinishedListener onCreateAccountFinishedListener) {
        this.onCreateAccountFinishedListener = onCreateAccountFinishedListener;
    }

    public void makeApiRequest(String jsonMessage) {
        ApiRequest apiRequest = (ApiRequest) new ApiRequest(this).execute(jsonMessage);
    }


    private class ApiRequest extends AsyncTask<String, RegisterRequestImpl, Void> {
        private RegisterRequestImpl registerRequestImpl;

        private String responseBody;

        public ApiRequest(RegisterRequestImpl registerRequestImpl) {
            this.registerRequestImpl = registerRequestImpl;
        }

        @Override
        protected Void doInBackground(String... params) {
            //TODO: Call correct backend - also backend needs to be updated to go by email on login
            String[] resultFromApi = Connector.connect("https://pvt15app.herokuapp.com/api/testsignup",
                    "PUT", String.format(params[0]));
            responseBody = resultFromApi[0];
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            registerRequestImpl.onCreateAccountFinishedListener.closeProgressDialog();
            registerRequestImpl.onCreateAccountFinishedListener.showApiResponse(responseBody);
        }
    }
}
