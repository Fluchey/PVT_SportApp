package com.sportify.createEvent.request;

import android.os.AsyncTask;

import com.sportify.util.Connector;

/**
 * Created by Maja on 2017-04-18.
 */

public class CreateEventRequestImpl implements CreateEventRequest{

    OnCreateEventFinishedListener onCreateEventFinishedListener;
    private String token = "";

    public CreateEventRequestImpl(final OnCreateEventFinishedListener onCreateEventFinishedListener, String token){
        this.onCreateEventFinishedListener = onCreateEventFinishedListener;
        this.token = token;
    }

    /**
     * Create asynctask to create the event
     * @param jsonMessage - The new event
     */
    @Override
    public void makeApiRequest(String jsonMessage) {
        ApiRequest apiRequest = (ApiRequest) new ApiRequest(this).execute(jsonMessage);
    }

    private class ApiRequest extends AsyncTask<String, CreateEventRequestImpl, Void>{

        private CreateEventRequestImpl createEventRequestImpl;
        private String responseBody;

        public ApiRequest(CreateEventRequestImpl createEventRequestImpl){
            this.createEventRequestImpl = createEventRequestImpl;
        }
        @Override
        protected Void doInBackground(String... params) {

            String[] resultFromApi = Connector.connect("http://130.237.89.181:9000/api/createEvent",
                    "POST", String.format(params[0]), token);
//            String[] resultFromApi = Connector.connect("http://193.11.94.17:9000/api/changeEvent",
//                    "PUT", String.format(params[0]), token);

            responseBody = resultFromApi[0];

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);

            createEventRequestImpl.onCreateEventFinishedListener.showApiResponse(responseBody);
        }
    }
}
