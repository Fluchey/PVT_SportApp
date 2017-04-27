package com.sportify.login.request;

import android.os.AsyncTask;

import com.sportify.util.Connector;

/**
 * Created by peradrianbergman on 2017-04-18.
 */

public class LoginRequestImpl implements LoginRequest {
    OnLoginAccountFinishedListener onLoginAccountFinishedListener;
    private String token = "";

    public LoginRequestImpl(final OnLoginAccountFinishedListener onLoginAccountFinishedListener, String token) {
        this.onLoginAccountFinishedListener = onLoginAccountFinishedListener;
        this.token = token;
    }

    public void makeApiRequest(String jsonMessage, String url) {
        ApiRequest apiRequest = (ApiRequest) new ApiRequest(this, url).execute(jsonMessage);
    }


    private class ApiRequest extends AsyncTask<String, LoginRequestImpl, Void> {
        private LoginRequestImpl loginRequestImpl;
        private String responseBody;
        private String responseCode;
        private String connectorUrl;

        public ApiRequest(LoginRequestImpl loginRequestImpl, String connectorUrl) {
            this.loginRequestImpl = loginRequestImpl;
            this.connectorUrl = connectorUrl;
        }

        @Override
        protected Void doInBackground(String... params) {
            String[] resultFromApi = Connector.connect(connectorUrl,
                    "PUT", String.format(params[0]), token);
            responseBody = resultFromApi[0];
            responseCode = resultFromApi[1];
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            loginRequestImpl.onLoginAccountFinishedListener.closeProgressDialog();
            loginRequestImpl.onLoginAccountFinishedListener.showApiResponse(responseBody, responseCode);
        }
    }
}
