package com.sportify.eventArea.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.sportify.editEvent.activity.activity.EditEventActivity;
import com.sportify.eventArea.presenter.EventAreaPresenter;
import com.sportify.eventArea.presenter.EventAreaPresenterImpl;
import com.sportify.storage.Place;
import com.sportify.userArea.activity.UserAreaActivity;

import sportapp.pvt_sportapp.R;

public class EventAreaActivity extends AppCompatActivity implements EventAreaView{
    private EventAreaPresenter presenter;
    private int eventId;
    private SharedPreferences sharedPref;

    private TextView hostNameTv;
    private TextView eventNameTv;
    private TextView placeNameTv;
    private TextView descriptionTv;
    private TextView eventDateTv;
    private TextView startTimeTv;
    private TextView endTimeTv;
    private TextView priceTv;

    /*
     * Event information
     */

    private String eventName;
//    private String placeName;
    private Place place;
    private String eventDate;
    private String startTime;
    private String endTime;
    private String eventType;
    private int price;
    private boolean privateEvent;
    private String description;
    private int maxAttendance;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_area);

        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        presenter = new EventAreaPresenterImpl(this, sharedPref);

        Bundle bundle = getIntent().getExtras();
        eventId = (bundle.getInt("eventId"));
        presenter.getEventFromDb(eventId);

        hostNameTv = (TextView) findViewById(R.id.tvEventAreaHostName);
        eventNameTv = (TextView) findViewById(R.id.eventAreaHeader);
        placeNameTv = (TextView) findViewById(R.id.tvEventAreaPlaceText);
        eventDateTv = (TextView) findViewById(R.id.tvEventAreaDate);
        startTimeTv = (TextView) findViewById(R.id.tvEventAreaStartTime);
        endTimeTv = (TextView) findViewById(R.id.tvEventAreaEndTime);
        descriptionTv = (TextView) findViewById(R.id.tvEventAreaDescription);
        priceTv = (TextView) findViewById(R.id.tvEventAreaPrice);
    }


    public void toUserAreaFromEvent(View v){
        Intent goToUserAreaViewIntent = new Intent(EventAreaActivity.this, UserAreaActivity.class);
        EventAreaActivity.this.startActivity(goToUserAreaViewIntent);
    }

    public void goToEditEventActivity(View v){
        Intent goToEditEventViewIntent = new Intent(EventAreaActivity.this, EditEventActivity.class);

        goToEditEventViewIntent.putExtra("eventId", eventId);
        goToEditEventViewIntent.putExtra("eventName", eventName);
//        goToEditEventViewIntent.putExtra("place", placeName);
        goToEditEventViewIntent.putExtra("place", place);
        goToEditEventViewIntent.putExtra("eventDate", eventDate);
        goToEditEventViewIntent.putExtra("startTime", startTime);
        goToEditEventViewIntent.putExtra("endTime", endTime);
        goToEditEventViewIntent.putExtra("eventType", eventType);
        goToEditEventViewIntent.putExtra("maxAttendance", maxAttendance);
        goToEditEventViewIntent.putExtra("price", price);
        goToEditEventViewIntent.putExtra("privateEvent", privateEvent);
        goToEditEventViewIntent.putExtra("description", description);


        EventAreaActivity.this.startActivity(goToEditEventViewIntent);
    }


    @Override
    public void setEventName(String eventName) {
        this.eventNameTv.setText(eventName);
        this.eventName = eventName;
    }

    @Override
    public String getEventName() {
        return eventName;
    }

    @Override
    public void setPlaceName(Place place) {
        this.placeNameTv.setText(place.getName());
        this.place = place;
    }

    @Override
    public Place getPlaceName() {
        return place;
    }

    @Override
    public void setHostName(String firstName, String lastName) {
        this.hostNameTv.setText(firstName + " " + lastName);
    }

    @Override
    public String getHostName() {
        return hostNameTv.getText().toString();
    }

    @Override
    public void setStartTime(String startTime) {
        this.startTimeTv.setText(startTime);
        this.startTime = startTime;
    }

    @Override
    public String getStartTime() {
        return startTime;
    }

    @Override
    public void setEndTime(String endTime) {
        this.endTimeTv.setText(endTime);
        this.endTime = endTime;
    }

    @Override
    public void setEventDate(String date) {
        this.eventDate = date;
    }

    @Override
    public String getEventDate() {
        return eventDate;
    }

    @Override
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public String getEventType() {
        return eventType;
    }

    @Override
    public void setPrice(int price) {
        this.priceTv.setText(String.valueOf(price));
        this.price = price;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public void setMaxAttendance(int maxAttendance) {
        this.maxAttendance = maxAttendance;
    }

    @Override
    public int getMaxAttendance() {
        return maxAttendance;
    }

    @Override
    public void setPrivateEvent(boolean privateEvent) {
        System.out.println("Kolla privat " + privateEvent);
        this.privateEvent = privateEvent;
    }

    @Override
    public void setDescription(String description) {
        this.descriptionTv.setText(description);
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void sendResponsEventInvite(View v) {
        String response = v.getTag().toString();
        presenter.sendResponsEventInvite(response, eventId);
    }

    public void shareToFacebook(View v){
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken==null || accessToken.isExpired()){
            Toast.makeText(this, "Fel: Du har inte loggat in med Facebook", Toast.LENGTH_LONG).show();
        } else {
            presenter.shareEventToFacebook();
        }
    }
}
