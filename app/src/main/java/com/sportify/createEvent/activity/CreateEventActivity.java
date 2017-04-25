package com.sportify.createEvent.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sportify.createEvent.presenter.CreateEventPresenter;
import com.sportify.createEvent.presenter.CreateEventPresenterImpl;

import sportapp.pvt_sportapp.R;

/**
 * Created by Maja on 2017-04-18.
 */

public class CreateEventActivity extends AppCompatActivity implements CreateEventView {

    private EditText eventName;
    private EditText eventPrice;
    private EditText eventDescription;
    private EditText eventDate;
    private EditText eventStartTime;
    private EditText eventEndTime;
    private EditText eventType;
    private EditText eventMaxAttendance;
    private CheckBox eventPrivate;
    private TextView message;

    private CreateEventPresenter createEventPresenter;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        createEventPresenter = new CreateEventPresenterImpl(this);

        eventName = (EditText) findViewById(R.id.etEventName);
        eventPrice = (EditText) findViewById(R.id.etEventPrice);
        eventDescription = (EditText) findViewById(R.id.etEventDescription);
        message = (TextView) findViewById(R.id.tvCreateEventMessage);
        eventDate = (EditText) findViewById(R.id.etEventDate);
        eventStartTime = (EditText) findViewById(R.id.etEventStartTime);
        eventEndTime = (EditText) findViewById(R.id.etEventEndTime);
        eventType = (EditText) findViewById(R.id.etEventType);
        eventMaxAttendance = (EditText) findViewById(R.id.etEventMaxAttendance);
        eventPrivate = (CheckBox) findViewById(R.id.cbEventPrivate);
    }

    public void createEventClick(View v){
        createEventPresenter.createEvent();
    }

    @Override
    public String getEventName() {
        return eventName.getText().toString();
    }

    @Override
    public String getEventPrice() {
        return eventPrice.getText().toString();
    }

    @Override
    public String getEventDate() {
        return eventDate.getText().toString();
    }

    @Override
    public String getEventStartTime() {
        return eventStartTime.getText().toString();
    }

    @Override
    public String getEventEndTime() {
        return eventEndTime.getText().toString();
    }

    @Override
    public String getEventType() {
        return eventType.getText().toString();
    }

    @Override
    public String getEventMaxAttendance() {
        return eventMaxAttendance.getText().toString();
    }

    @Override
    public String getEventDescription() {
        return eventDescription.getText().toString();
    }

    @Override
    public boolean getPrivateEvent() {
        return eventPrivate.isChecked();
    }

    @Override
    public void showEventNameEmptyError() {
        message.setText("Event name is missing, try again");
    }

    @Override
    public void showEventPriceWrongFormatError() {
        message.setText("Event price is in wrong format, try again");
    }

    @Override
    public void showEventDateEmptyError() {
        message.setText("Event date is missing, try again");
    }

    @Override
    public void showEventStartTimeEmptyError() {
        message.setText("Event start time is missing, try again");
    }

    @Override
    public void showEventEndTimeEmptyError() {
        message.setText("Event end time is missing, try again");
    }

    @Override
    public void showEventTypeEmptyError() {
        message.setText("Event type is missing, try again");
    }

    public void clearMessageTv(){
        message.setText("");
    }

    @Override
    public void showApiRequestMessage(String apiResponse){
        Toast.makeText(this, apiResponse, Toast.LENGTH_LONG).show();
    }
}