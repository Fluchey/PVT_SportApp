package com.sportify.createEvent.createEventPageOne.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.sportify.createEvent.createEventPageOne.presenter.CreateEventPresenter;
import com.sportify.createEvent.createEventPageOne.presenter.CreateEventPresenterImpl;
import com.sportify.createEvent.createEventPageThree.activity.CreateEventInviteFriendsActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import sportapp.pvt_sportapp.R;

/**
 * Created by Maja on 2017-04-18.
 */

public class CreateEventActivity extends AppCompatActivity implements CreateEventView {
    /**
     * CONNECTIONS
     */
    private CreateEventPresenter createEventPresenter;
    private SharedPreferences sharedPref;

    /**
     * EDIT TEXTS
     */
    private EditText eventName;
    private EditText eventPrice;
    private EditText eventDescription;
    private EditText eventType;
    private EditText eventMaxAttendance;
    private CheckBox eventPrivate;


    /**
     * EVENT PLACE
     */
    private AutoCompleteTextView eventPlace;
    private boolean userWroteSearch;
    private ArrayAdapter arrayAdapter;

    /**
     * EVENT DATE
     */
    private Calendar calendar;
    private DatePickerDialog.OnDateSetListener startDate;
    private DatePickerDialog.OnDateSetListener endDate;
    private EditText eventStartDate;
    private EditText eventEndDate;

    /**
     * EVENT TIME
     */
    private EditText eventStartTime;
    private EditText eventEndTime;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        createEventPresenter = new CreateEventPresenterImpl(this, sharedPref);

        /**
         *  EVENT PLACE
         */
        eventPlace = (AutoCompleteTextView) findViewById(R.id.etEventPlace);
        userWroteSearch = true;
        eventPlace.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                userWroteSearch = true;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        eventPlace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                userWroteSearch = false;
            }
        });

        eventPrice = (EditText) findViewById(R.id.etEventPrice);
        eventDescription = (EditText) findViewById(R.id.etEventDescription);

        /**
         *  DATE AND CALENDAR
         */
        eventStartDate = (EditText) findViewById(R.id.etEventStartDate);
        calendar = Calendar.getInstance();
        startDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                eventStartDate.setText(sdf.format(calendar.getTime()));
            }
        };
        eventStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateEventActivity.this, startDate, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        eventEndDate = (EditText) findViewById(R.id.etEventEndDate);
        calendar = Calendar.getInstance();
        endDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                eventEndDate.setText(sdf.format(calendar.getTime()));
            }
        };
        eventEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateEventActivity.this, endDate, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        /**
         *  START AND END TIME
         */
        eventStartTime = (EditText) findViewById(R.id.etEventStartTime);
        eventStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreateEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedMinute == 0){
                            eventStartTime.setText(selectedHour + ":" + String.valueOf(selectedMinute) + "0");
                        }else {
                            eventStartTime.setText(selectedHour + ":" + selectedMinute);
                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        eventEndTime = (EditText) findViewById(R.id.etEventEndTime);
        eventEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreateEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedMinute == 0){
                            eventEndTime.setText(selectedHour + ":" + String.valueOf(selectedMinute) + "0");
                        }else {
                            eventEndTime.setText(selectedHour + ":" + selectedMinute);
                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        eventType = (EditText) findViewById(R.id.etEventType);
        final String [] categories = getResources().getStringArray(R.array.sections);
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Pick category");
        dialog.setItems(categories, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position){
                eventType.setText(categories[position]);
            }
        });
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        eventType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alert = dialog.create();
                alert.show();
            }
        });


        /**
         * EDIT TEXTS
         */
        eventName = (EditText) findViewById(R.id.etEventName);
        eventMaxAttendance = (EditText) findViewById(R.id.etEventMaxAttendance);
        eventPrivate = (CheckBox) findViewById(R.id.cbEventPrivate);
    }

    public void goToInviteFriends(View v){
        Intent goToInviteFriendsIntent = new Intent(CreateEventActivity.this, CreateEventInviteFriendsActivity.class);
        CreateEventActivity.this.startActivity(goToInviteFriendsIntent);
    }

    public void createEventClick(View v) {
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

    public String getEventStartDate() {
        return eventStartDate.getText().toString();
    }

    @Override
    public String getEventEndDate(){
        return eventEndDate.getText().toString();
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
    public void showEventNameEmptyError(int resId) {
        eventName.setError(getString(resId));
    }

    @Override
    public void showEventPriceWrongFormatError(int resId) {
        eventPrice.setError(getString(resId));
    }

    @Override
    public void showEventStartDateEmptyError(int resId) {
        eventStartDate.setError(getString(resId));
    }

    @Override
    public void showEventEndDateEmptyError(int resId) {
        eventEndDate.setError(getString(resId));
    }

    @Override
    public void showEventStartDateFormatError(int resId) {
        eventStartDate.setError(getString(resId));
    }

    @Override
    public void showEventEndDateFormatError(int resId) {
        eventEndDate.setError(getString(resId));
    }

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
    public void showEventPlaceEmptyError(int resId) {
        eventPlace.setError(getString(resId));
    }

    @Override
    public void clearAllErrors() {
        eventName.setError(null);
        eventPrice.setError(null);
        eventDescription.setError(null);
        eventPlace.setError(null);
        eventStartDate.setError(null);
        eventStartTime.setError(null);
        eventEndTime.setError(null);
        eventType.setError(null);
        eventMaxAttendance.setError(null);
    }

    @Override
    public void showToastToUser(String apiResponse) {
        Toast.makeText(this, apiResponse, Toast.LENGTH_LONG).show();
    }

    @Override
    public void updatePlaceAdapter(ArrayList<String> arr) {
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr);
        eventPlace.setAdapter(arrayAdapter);
    }

    @Override
    public boolean getUserWroteSearch() {
        return userWroteSearch;
    }
}