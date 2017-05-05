package com.sportify.profile.request;

/**
 * Created by peradrianbergman on 2017-05-05.
 */

public interface ProfileRequest {
    interface OnCreateProfileFinishedListener {
        void showApiResponse(String... params);
        void closeProgressDialog();
    }

    void makeApiRequest(String jsonMessage, String connectorUrl);
}
