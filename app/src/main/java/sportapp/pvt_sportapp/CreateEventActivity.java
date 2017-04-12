package sportapp.pvt_sportapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

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

    public void createEventButtonClick(){
        EditText eventName = (EditText) findViewById(R.id.etEventName);
        EditText eventPrice = (EditText) findViewById(R.id.etEventPrice);
        EditText eventDescription = (EditText) findViewById(R.id.etEventDescription);
        TextView message = (TextView) findViewById(R.id.tvCreateEventMessage);

        if(TextUtils.isEmpty(eventName.getText().toString())){
            message.setText("Event name is missing, try again");
        }else if(TextUtils.isEmpty(eventPrice.getText().toString())){
            message.setText("Event price is missing, try again");
        }
        //Check how long the description is. Which is the shortest accepted description?
        else{
            JSONObject jsonObject = new JSONObject();
            try{
                //behöver event_id också, osäker på hur vi vill skapa det
                jsonObject.put("event_namn", eventName.getText().toString());
                jsonObject.put("event_beskrivning", eventDescription.getText().toString());
                jsonObject.put("event_pris", eventPrice.getText().toString());
                Log.d("JsonObject", jsonObject.toString());
            }catch (JSONException e){
                e.printStackTrace();
            }

            //create asynctask and push to database
        }

    }
}
