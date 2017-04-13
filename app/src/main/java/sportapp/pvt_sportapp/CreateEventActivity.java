package sportapp.pvt_sportapp;

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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

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
        EditText eventName = (EditText) findViewById(R.id.etEventName);
        EditText eventPrice = (EditText) findViewById(R.id.etEventPrice);
        EditText eventDescription = (EditText) findViewById(R.id.etEventDescription);
        TextView message = (TextView) findViewById(R.id.tvCreateEventMessage);
        message.setText("");

        if (TextUtils.isEmpty(eventName.getText().toString())) {
            message.setText("Event name is missing, try again");
        } else if (TextUtils.isEmpty(eventPrice.getText().toString())) {
            message.setText("Event price is missing");
        }
        //Check how long the description is. Which is the shortest accepted description?
        else {

            try {
                int price = Integer.parseInt(eventPrice.getText().toString());
            } catch (NumberFormatException e) {
                message.setText("Event price is in wrong format, try again");
                return;
            }

            JSONObject jsonObject = new JSONObject();
            try {
                //behöver event_id också, osäker på hur vi vill skapa det. Tillfällig lösning:
                Random rn = new Random();
                String id = "" + rn.nextInt();
                jsonObject.put("event_id", "" + id);
                jsonObject.put("event_namn", eventName.getText().toString());
                jsonObject.put("event_beskrivning", eventDescription.getText().toString());
                jsonObject.put("event_pris", "" + Integer.parseInt(eventPrice.getText().toString()));
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

            try {
                URL url = new URL("https://pvt15app.herokuapp.com/api/testCreateEvent");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
                osw.write(String.format(params[0]));
                osw.flush();
                osw.close();
                Log.d("Responscode", connection.getResponseCode() + "");
                BufferedReader br;

                System.out.println("" + connection.getResponseCode());

                if (200 <= connection.getResponseCode() && connection.getResponseCode() <= 299) {
                    br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
                } else {
                    br = new BufferedReader(new InputStreamReader((connection.getErrorStream())));
                }

                StringBuilder out = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    out.append(line);
                }
                Log.d("JSON?", out.toString());
                JSONObject jsonObject = new JSONObject(out.toString());
                responseBody = jsonObject.getString("body");
                responseCode = connection.getResponseCode();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (responseCode == 201) {
                Toast.makeText(createEventActivity, "Event created!", Toast.LENGTH_LONG).show();
            } else {
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