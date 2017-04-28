package com.sportify.friends.request;

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
        private String responsbody;

        public ApiRequest(FriendRequestImpl friendRequestImpl){
            this.friendRequestImpl = friendRequestImpl;
        }

        @Override
        protected Void doInBackground(String... params) {

            String[] resultFromApi = Connector.connect("https://pvt15app.herokuapp.com/api/findfriends",
                        "PUT", String.format(params[0]), token);

            responsbody = resultFromApi[0];

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);

            friendRequestImpl.onShowFriendsFinishedListener.showApiResponse(responsbody);
        }
    }


}