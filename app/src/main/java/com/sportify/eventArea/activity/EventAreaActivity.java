package com.sportify.eventArea.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.sportify.eventArea.presenter.EventAreaPresenter;
import com.sportify.eventArea.presenter.EventAreaPresenterImpl;
import com.sportify.userArea.activity.UserAreaActivity;

import sportapp.pvt_sportapp.R;

public class EventAreaActivity extends AppCompatActivity implements EventAreaView{
    private EventAreaPresenter presenter;
    private int eventId;
    private SharedPreferences sharedPref;

    private TextView hostName;
    private TextView eventName;
    private TextView placeName;
    private TextView description;
    private TextView startDate;
    private TextView startTime;
    private TextView endTime;
    private TextView price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_area);

        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        presenter = new EventAreaPresenterImpl(this, sharedPref);

        Bundle bundle = getIntent().getExtras();
        eventId = (bundle.getInt("eventId"));
        presenter.getEventFromDb(eventId);

        hostName = (TextView) findViewById(R.id.tvEventAreaHostName);
        eventName = (TextView) findViewById(R.id.eventAreaHeader);
        placeName = (TextView) findViewById(R.id.tvEventAreaPlaceText);
        startDate = (TextView) findViewById(R.id.tvEventAreaDate);
        startTime = (TextView) findViewById(R.id.tvEventAreaStartTime);
        endTime = (TextView) findViewById(R.id.tvEventAreaEndTime);
        description = (TextView) findViewById(R.id.tvEventAreaDescription);
        price = (TextView) findViewById(R.id.tvEventAreaPrice);
    }


    public void toUserAreaFromEvent(View v){
        Intent goToUserAreaViewIntent = new Intent(EventAreaActivity.this, UserAreaActivity.class);
        EventAreaActivity.this.startActivity(goToUserAreaViewIntent);
    }

    @Override
    public void setEventName(String eventName) {
        this.eventName.setText(eventName);
    }

    @Override
    public void setPlaceName(String placeName) {
        this.placeName.setText(placeName);
    }

    @Override
    public void setHostName(String firstName, String lastName) {
        this.hostName.setText(firstName + " " + lastName);
    }

    @Override
    public void setStartTime(String startTime) {
        this.startTime.setText(startTime);
    }

    @Override
    public void setEndTime(String endTime) {
        this.endTime.setText(endTime);
    }

    @Override
    public void setStartDate(String startDate) {
        this.startDate.setText(startDate);
    }

    @Override
    public void setEndDate(String endDate) {

    }

    @Override
    public void setPrice(int price) {
        this.price.setText(String.valueOf(price));
    }

    @Override
    public void setDescription(String description) {
        this.description.setText(description);
    }

    @Override
    public void sendResponsEventInvite(View v) {
        String response = v.getTag().toString();
        presenter.sendResponsEventInvite(response, eventId);
    }
}
