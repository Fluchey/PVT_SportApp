package com.sportify.login.request;

import android.os.AsyncTask;

import com.sportify.util.Connector;

/**
 * Created by peradrianbergman on 2017-04-18.
 */

public class LoginRequestImpl implements LoginRequest {
    OnLoginAccountFinishedListener onLoginAccountFinishedListener;

    public LoginRequestImpl(final OnLoginAccountFinishedListener onLoginAccountFinishedListener) {
        this.onLoginAccountFinishedListener = onLoginAccountFinishedListener;
    }

    public void makeApiRequest(String jsonMessage) {
        ApiRequest apiRequest = (ApiRequest) new ApiRequest(this).execute(jsonMessage);
    }


    private class ApiRequest extends AsyncTask<String, LoginRequestImpl, Void> {
        private LoginRequestImpl loginRequestImpl;
        private String responseBody;

        public ApiRequest(LoginRequestImpl registerRequestImpl) {
            this.loginRequestImpl = loginRequestImpl;
        }

        @Override
        protected Void doInBackground(String... params) {
//            String[] resultFromApi = Connector.connect("https://pvt15app.herokuapp.com/api/testsignup",
//                    "PUT", String.format(params[0]));
//            responseBody = resultFromApi[0];
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            loginRequestImpl.onLoginAccountFinishedListener.closeProgressDialog();
            loginRequestImpl.onLoginAccountFinishedListener.showApiResponse(responseBody);
        }
    }
}
