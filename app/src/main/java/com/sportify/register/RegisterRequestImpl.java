package com.sportify.register;

import android.os.AsyncTask;
import android.util.Log;

import com.sportify.util.Connector;

/**
 * Created by fluchey on 2017-04-16.
 */

class RegisterRequestImpl extends AsyncTask<String, RegisterActivity, Void> implements RegisterRequest{
    private int responseCode;
    private String responseBody;
    OnCreateAccountFinishedListener onCreateAccountFinishedListener;

    public RegisterRequestImpl(String jsonMessage, final OnCreateAccountFinishedListener onCreateAccountFinishedListener) {
        this.onCreateAccountFinishedListener = onCreateAccountFinishedListener;
        doInBackground(jsonMessage);
    }

    @Override
    protected void onPreExecute() {
    }

    /**
     * This happens in the background.
     * Connects to API and saves responseBody and responseCode.
     *
     * @param params The JSON object to be sent to the REST API
     * @return
     */
    @Override
    protected Void doInBackground(String... params) {
        String[] resultFromApi = Connector.connect("https://pvt15app.herokuapp.com/api/testsignup",
                "PUT", String.format(params[0]));
        responseBody = resultFromApi[0];
//        responseCode = Integer.parseInt(resultFromApi[1]);
        onCreateAccountFinishedListener.showApiResponse(responseBody);
        Log.d("ResponseBody", responseBody);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        onCreateAccountFinishedListener.showApiResponse(responseBody);
    }
}
