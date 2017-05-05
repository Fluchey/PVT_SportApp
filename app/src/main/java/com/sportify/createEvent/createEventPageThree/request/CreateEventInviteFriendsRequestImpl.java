package com.sportify.createEvent.createEventPageThree.request;

import android.os.AsyncTask;

import com.sportify.util.Connector;

/**
 * Created by Maja on 2017-04-27.
 */

public class CreateEventInviteFriendsRequestImpl implements CreateEventInviteFriendsRequest {

    OnShowFriendsFinishedListener onShowFriendsFinishedListener;
    private String token = "";

    public CreateEventInviteFriendsRequestImpl(final OnShowFriendsFinishedListener onShowFriendsFinishedListener, String token){
        this.onShowFriendsFinishedListener = onShowFriendsFinishedListener;
        this.token = token;
    }

    /**
     * * Create asynctask to get friends
     * @param jsonMessage
     */
    @Override
    public void makeApiRequest(String jsonMessage) {
        ApiRequest apiRequest = (ApiRequest) new ApiRequest(this).execute(jsonMessage);
    }

    private class ApiRequest extends AsyncTask<String, CreateEventInviteFriendsRequestImpl, Void>{

        private CreateEventInviteFriendsRequestImpl friendRequestImpl;
        private String [] resultFromApi;

        public ApiRequest(CreateEventInviteFriendsRequestImpl friendRequestImpl){
            this.friendRequestImpl = friendRequestImpl;
        }

        @Override
        protected Void doInBackground(String... params) {

          resultFromApi = Connector.connect("https://pvt15app.herokuapp.com/api/findfriends",
                        "POST", String.format(params[0]), token);
//            resultFromApi = Connector.connect("http://130.237.89.181:9000/api/findfriends",
//                    "POST", String.format(params[0]), token);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);

            friendRequestImpl.onShowFriendsFinishedListener.showApiResponse(resultFromApi);
        }
    }
}
