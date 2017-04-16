package com.sportify.event;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.Random;

import com.sportify.util.Connector;
import sportapp.pvt_sportapp.R;

public class CreateEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        Button btEvent = (Button) findViewById(R.id.createEventButton);

        btEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEventButtonClick();
            }
        });
    }

    public void createEventButtonClick() {
        int eventPrice = 0;
        EditText eventName = (EditText) findViewById(R.id.etEventName);
        EditText eventPriceEt= (EditText) findViewById(R.id.etEventPrice);
        EditText eventDescription = (EditText) findViewById(R.id.etEventDescription);
        TextView message = (TextView) findViewById(R.id.tvCreateEventMessage);
        message.setText("");

        if (TextUtils.isEmpty(eventName.getText().toString())) {
            message.setText("Event name is missing, try again");
        }
        //TODO Check how long the description is. Which is the shortest accepted description?
        else {
            if(!TextUtils.isEmpty(eventPriceEt.getText().toString())) {

                try {
                    eventPrice = Integer.parseInt(eventPriceEt.getText().toString());
                } catch (NumberFormatException e) {
                    message.setText("Event price is in wrong format, try again");
                    return;
                }
            }


            JSONObject jsonObject = new JSONObject();
            try {
                //TODO behöver event_id också, osäker på hur vi vill skapa det. Tillfällig lösning:
                Random rn = new Random();
                String id = "" + rn.nextInt(1)+1000;

                jsonObject.put("event_id", "" + id);
                jsonObject.put("event_namn", eventName.getText().toString());
                jsonObject.put("event_beskrivning", eventDescription.getText().toString());
                jsonObject.put("event_pris", "" + eventPrice);
                Log.d("JsonObject", jsonObject.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            new CreateEventRequest(this).execute(jsonObject.toString());
        }
    }

    private class CreateEventRequest extends AsyncTask<String, CreateEventActivity, Void> {

        private CreateEventActivity createEventActivity;
        protected int responseCode;
        protected String responseBody;

        public CreateEventRequest(CreateEventActivity createEvent) {
            this.createEventActivity = createEvent;
        }

        @Override
        protected Void doInBackground(String... params) {
            HttpURLConnection connection = null;

            String[] test = Connector.connect("https://pvt15app.herokuapp.com/api/testCreateEvent",
                    "POST", String.format(params[0]));
            responseBody = test[0];
            responseCode = Integer.parseInt(test[1]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (responseCode == 201) {
                Toast.makeText(createEventActivity, "Event created!", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(createEventActivity, responseBody, Toast.LENGTH_LONG).show();
            }

            EditText eventName = (EditText) findViewById(R.id.etEventName);
            EditText eventPrice = (EditText) findViewById(R.id.etEventPrice);
            EditText eventDescription = (EditText) findViewById(R.id.etEventDescription);
            eventName.setText("");
            eventPrice.getText().clear();
            eventDescription.getText().clear();

        }

    }
}