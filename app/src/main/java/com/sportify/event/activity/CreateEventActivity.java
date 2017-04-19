package com.sportify.event.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sportify.event.presenter.CreateEventPresenter;
import com.sportify.event.presenter.CreateEventPresenterImpl;

import sportapp.pvt_sportapp.R;

/**
 * Created by Maja on 2017-04-18.
 */

public class CreateEventActivity extends AppCompatActivity implements CreateEventView {

    private EditText eventName;
    private EditText eventPrice;
    private EditText eventDescription;
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
    public String getEventDescription() {
        return eventDescription.getText().toString();
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
    public void showApiRequestMessage(String apiResponse){
        Toast.makeText(this, apiResponse, Toast.LENGTH_LONG).show();

    }
}