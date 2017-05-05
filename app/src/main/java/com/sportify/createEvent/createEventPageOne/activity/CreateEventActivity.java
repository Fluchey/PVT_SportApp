package com.sportify.createEvent.createEventPageOne.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.sportify.createEvent.createEventPageOne.presenter.CreateEventPresenter;
import com.sportify.createEvent.createEventPageOne.presenter.CreateEventPresenterImpl;

import java.util.ArrayList;

import sportapp.pvt_sportapp.R;

/**
 * Created by Maja on 2017-04-18.
 */

public class CreateEventActivity extends AppCompatActivity implements CreateEventView {

    private EditText eventName;
    private EditText eventPrice;
    private EditText eventDescription;
    private AutoCompleteTextView eventPlace;
    private ArrayAdapter arrayAdapter;
    private EditText eventDate;
    private EditText eventStartTime;
    private EditText eventEndTime;
    private EditText eventType;
    private EditText eventMaxAttendance;
    private CheckBox eventPrivate;
    private SharedPreferences sharedPref;

    private CreateEventPresenter createEventPresenter;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        createEventPresenter = new CreateEventPresenterImpl(this, sharedPref);

        eventName = (EditText) findViewById(R.id.etEventName);
        eventPlace = (AutoCompleteTextView) findViewById(R.id.etEventPlace);
        eventPrice = (EditText) findViewById(R.id.etEventPrice);
        eventDescription = (EditText) findViewById(R.id.etEventDescription);
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
    public String getEventPlace() {
        return eventPlace.getText().toString();
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

//    @Override
//    public void showEventNameEmptyError() {
//        message.setText(R.string.event_name_empty_error);
//    }

    @Override
    public void showEventNameEmptyError(int resId){
        eventName.setError(getString(resId));
    }

    @Override
    public void showEventPriceWrongFormatError(int resId) {
        eventPrice.setError(getString(resId));
    }

    @Override
    public void showEventDateEmptyError(int resId) {
        eventDate.setError(getString(resId));
    }

    @Override
    public void showEventDateFormatError(int resId){
        eventDate.setError(getString(resId));}

    @Override
    public void showEventStartTimeEmptyError(int resId) {
        eventStartTime.setError(getString(resId));
    }

    @Override
    public void showEventEndTimeEmptyError(int resId) {
        eventEndTime.setError(getString(resId));
    }

    @Override
    public void showEventTypeEmptyError(int resId) {
        eventType.setError(getString(resId));
    }

    @Override
    public void showEventPlaceEmptyError(int resId){
        eventPlace.setError(getString(resId));
    }

    @Override
    public void clearAllErrors() {
        eventName.setError(null);
        eventPrice.setError(null);
        eventDescription.setError(null);
        eventPlace.setError(null);
        eventDate.setError(null);
        eventStartTime.setError(null);
        eventEndTime.setError(null);
        eventType.setError(null);
        eventMaxAttendance.setError(null);
    }

    @Override
    public void showApiRequestMessage(String apiResponse){
        Toast.makeText(this, apiResponse, Toast.LENGTH_LONG).show();
    }

    @Override
    public void updatePlaceAdapter(ArrayList<String> arr) {
        arrayAdapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr);
        eventPlace.setAdapter(arrayAdapter);
    }
}