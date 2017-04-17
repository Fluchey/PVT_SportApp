package com.sportify.register.request;

/**
 * Created by fluchey on 2017-04-16.
 */

public interface RegisterRequest {
    interface OnCreateAccountFinishedListener {
        void showApiResponse(String apiResponse);

        void closeProgressDialog();
    }

    void makeApiRequest(String jsonMessage);
}
