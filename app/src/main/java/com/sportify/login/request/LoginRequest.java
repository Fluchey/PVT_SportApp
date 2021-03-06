package com.sportify.login.request;

/**
 * Created by peradrianbergman on 2017-04-18.
 */

public interface LoginRequest {
    interface OnLoginAccountFinishedListener {
        void showApiResponse(String... params);

        void closeProgressDialog();
    }

    void makeApiRequest(String jsonMessage, String connectorUrl);
}
