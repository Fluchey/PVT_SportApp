package com.sportify.showFriends.request;

import android.os.AsyncTask;

import com.sportify.util.Connector;

/**
 * Created by Maja on 2017-04-27.
 */

public class FriendRequestImpl implements FriendRequest {

    OnShowFriendsFinishedListener onShowFriendsFinishedListener;
    private String token = "";

    public FriendRequestImpl(final OnShowFriendsFinishedListener onShowFriendsFinishedListener, String token){
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

    private class ApiRequest extends AsyncTask<String, FriendRequestImpl, Void>{

        private FriendRequestImpl friendRequestImpl;
        private String [] resultFromApi;

        public ApiRequest(FriendRequestImpl friendRequestImpl){
            this.friendRequestImpl = friendRequestImpl;
        }

        @Override
        protected Void doInBackground(String... params) {

          resultFromApi = Connector.connect("https://pvt15app.herokuapp.com/api/findfriends",
                        "GET", String.format(params[0]), token);
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
