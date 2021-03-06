package com.sportify.addFriend.request;

import android.os.AsyncTask;

import com.sportify.util.Connector;

/**
 * Created by Maja on 2017-05-05.
 */

public class AddFriendRequestImpl implements AddFriendRequest {

    OnShowFriendsFinishedListener onShowFriendsFinishedListener;
    private String token = "";

    public AddFriendRequestImpl(final OnShowFriendsFinishedListener onShowFriendsFinishedListener, String token){
        this.onShowFriendsFinishedListener = onShowFriendsFinishedListener;
        this.token = token;
    }

    @Override
    public void makeApiRequestGetUsers(String method, String endUrl) {
        ApiRequest apiRequest = (ApiRequest) new ApiRequest(this).execute(method, endUrl);
    }

    @Override
    public void makeApiRequestAddFriend(String method, String endUrl, String jsonMessage) {
        ApiRequest apiRequest = (ApiRequest) new ApiRequest(this).execute(method, endUrl, jsonMessage);
    }

    private class ApiRequest extends AsyncTask<String, AddFriendRequestImpl, Void>{

        private AddFriendRequestImpl addFriendRequestImpl;
        private String[] resultFromApi;

        public ApiRequest(AddFriendRequestImpl addFriendRequest) {
            this.addFriendRequestImpl = addFriendRequest;
        }

        @Override
        protected Void doInBackground(String... params) {

            if(params[0].equals("GET")){
                resultFromApi = Connector.connectGetOrDelete(params[0], "https://pvt15app.herokuapp.com/api/" + params[1], token);
//                resultFromApi = Connector.connectGetOrDelete(params[0], "http://130.237.89.152:9000/api/" + params[1], token);
            }else {

                resultFromApi = Connector.connect("https://pvt15app.herokuapp.com/api/" + params[1],
                        params[0], String.format(params[2]), token);
            }
//            resultFromApi = Connector.connect("http://130.237.89.152:9000/api/" + params[1],
//                    params[0], String.format(params[2]), token);
//        }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);

            addFriendRequestImpl.onShowFriendsFinishedListener.showApiResponse(resultFromApi);
        }
    }
}
