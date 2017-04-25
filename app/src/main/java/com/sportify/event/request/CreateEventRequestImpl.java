package com.sportify.event.request;

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
        System.out.println("makeApiRequest");
    }

    private class ApiRequest extends AsyncTask<String, CreateEventRequestImpl, Void>{

        private CreateEventRequestImpl createEventRequestImpl;
        private String responseBody;

        public ApiRequest(CreateEventRequestImpl createEventRequestImpl){
            System.out.println("ApiRequest");
            this.createEventRequestImpl = createEventRequestImpl;
        }
        @Override
        protected Void doInBackground(String... params) {

            System.out.println("Background");
            String[] resultFromApi = Connector.connect("https://pvt15app.herokuapp.com/api/testCreateEvent",
                    "POST", String.format(params[0]), token);
            System.out.println("Background2");
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
