package com.sportify.eventArea.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sportify.settings.activity.SettingsActivity;
import com.sportify.userArea.activity.UserAreaActivity;

import sportapp.pvt_sportapp.R;

public class EventAreaActivity extends AppCompatActivity implements EventAreaView{
    private int eventId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_area);

        Bundle bundle = getIntent().getExtras();
        eventId = (bundle.getInt("eventId"));
        Toast.makeText(this, String.valueOf(eventId), Toast.LENGTH_SHORT).show();
    }


    public void toUserAreaFromEvent(View v){
        Intent goToUserAreaViewIntent = new Intent(EventAreaActivity.this, UserAreaActivity.class);
        EventAreaActivity.this.startActivity(goToUserAreaViewIntent);
    }
}
