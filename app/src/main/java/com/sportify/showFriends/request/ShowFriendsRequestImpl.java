package com.sportify.showFriends.request;

import android.os.AsyncTask;

import com.sportify.util.Connector;

/**
 * Created by Maja on 2017-05-05.
 */

public class ShowFriendsRequestImpl implements ShowFriendsRequest {

    ShowFriendsRequest.OnShowFriendsFinishedListener onShowFriendsFinishedListener;
    private String token = "";

    public ShowFriendsRequestImpl(final ShowFriendsRequest.OnShowFriendsFinishedListener onShowFriendsFinishedListener, String token){
        this.onShowFriendsFinishedListener = onShowFriendsFinishedListener;
        this.token = token;
    }

    @Override
    public void makeApiRequest(String jsonMessage) {
        ApiRequest apiRequest = (ApiRequest) new ApiRequest(this).execute(jsonMessage);
    }

    private class ApiRequest extends AsyncTask<String, ShowFriendsRequestImpl, Void>{

        private ShowFriendsRequestImpl showFriendsRequestimpl;
        private String[] resultFromApi;

        public ApiRequest(ShowFriendsRequestImpl showFriendsRequest) {
            this.showFriendsRequestimpl = showFriendsRequest;
        }

        @Override
        protected Void doInBackground(String... params) {

            resultFromApi = Connector.connect("https://pvt15app.herokuapp.com/api/findfriends",
                    "POST", String.format(params[0]), token);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);

            showFriendsRequestimpl.onShowFriendsFinishedListener.showApiResponse(resultFromApi);
        }
    }
}
