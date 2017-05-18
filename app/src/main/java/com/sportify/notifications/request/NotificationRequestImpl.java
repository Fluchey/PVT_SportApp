package com.sportify.notifications.request;

import android.os.AsyncTask;

import com.sportify.util.Connector;

/**
 * Created by Maja on 2017-05-17.
 */

public class NotificationRequestImpl implements NotificationRequest {

    NotificationRequest.OnShowNotificationFinishedListener onShowNotificationFinishedListener;
    private String token = "";

    public NotificationRequestImpl(final NotificationRequest.OnShowNotificationFinishedListener onShowNotificationFinishedListener, String token){
        this.onShowNotificationFinishedListener = onShowNotificationFinishedListener;
        this.token = token;
    }


    @Override
    public void makeApiRequest(String endUrl, String method, String jsonMessage) {
        ApiRequest apiRequest = (ApiRequest) new ApiRequest(this).execute(endUrl, method, jsonMessage);
    }

    private class ApiRequest extends AsyncTask<String, NotificationRequestImpl, Void>{

        private NotificationRequestImpl notificationRequestImpl;
        private String[] resultFromApi;

        public ApiRequest(NotificationRequestImpl notificationRequestImpl){
            this.notificationRequestImpl = notificationRequestImpl;
        }
        @Override
        protected Void doInBackground(String... params) {

            resultFromApi = Connector.connect("https://pvt15app.herokuapp.com/api/" + params[0],
                    params[1], String.format(params[2]), token);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);

            notificationRequestImpl.onShowNotificationFinishedListener.showApiResponse(resultFromApi);
        }
    }


}
