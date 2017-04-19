package com.sportify.event.request;

import android.os.AsyncTask;

import com.sportify.util.Connector;

/**
 * Created by Maja on 2017-04-18.
 */

public class CreateEventRequestImpl implements CreateEventRequest{

    OnCreateEventFinishedListener onCreateEventFinishedListener;

    public CreateEventRequestImpl(final OnCreateEventFinishedListener onCreateEventFinishedListener){
        this.onCreateEventFinishedListener = onCreateEventFinishedListener;
    }
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

            String[] resultFromApi = Connector.connect("https://pvt15app.herokuapp.com/api/testCreateEvent",
                    "POST", String.format(params[0]));
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
