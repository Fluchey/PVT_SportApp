package com.sportify.createEvent.createEventPreview.request;

import android.os.AsyncTask;

import com.sportify.editEvent.activity.request.EditEventRequestImpl;
import com.sportify.util.Connector;

/**
 * Created by Maja on 2017-05-12.
 */

public class CreateEventPreviewRequestImpl implements CreateEventPreviewRequest {

    OnCreateEventPreviewFinishedListener onCreateEventPreviewFinishedListener;
    private String token = "";

    public CreateEventPreviewRequestImpl(OnCreateEventPreviewFinishedListener onCreateEventPreviewFinishedListener, String token){
        this.onCreateEventPreviewFinishedListener = onCreateEventPreviewFinishedListener;
        this.token = token;
    }


    @Override
    public void makeApiRequest(String method, String endUrl, String jsonMessage) {
        CreateEventPreviewRequestImpl.ApiRequest apiRequest = (CreateEventPreviewRequestImpl.ApiRequest) new CreateEventPreviewRequestImpl.ApiRequest(this).execute(method, endUrl, jsonMessage);
    }

    private class ApiRequest extends AsyncTask<String, EditEventRequestImpl, Void> {

        CreateEventPreviewRequestImpl previewRequestImpl;
        private String responseBody;

        public ApiRequest(CreateEventPreviewRequestImpl previewRequestImpl){
            this.previewRequestImpl = previewRequestImpl;
        }

        @Override
        protected Void doInBackground(String... params) {

            String[] resultFromApi = Connector.connect("https://pvt15app.herokuapp.com/api/" + params[1],
                    params[0], String.format(params[2]), token);
            responseBody = resultFromApi[0];

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);

            previewRequestImpl.onCreateEventPreviewFinishedListener.showApiResponse(responseBody);
        }

    }

}
