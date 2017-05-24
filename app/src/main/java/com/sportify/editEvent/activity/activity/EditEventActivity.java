package com.sportify.editEvent.activity.activity;

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

import com.sportify.editEvent.activity.presenter.EditEventPresenter;
import com.sportify.editEvent.activity.presenter.EditEventPresenterImpl;
import com.sportify.eventArea.activity.EventAreaActivity;
import com.sportify.storage.Place;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import sportapp.pvt_sportapp.R;

public class EditEventActivity extends AppCompatActivity implements EditEventView{

    /**
     * CONNECTIONS
     */
    private EditEventPresenter editEventPresenter;
    private SharedPreferences sharedPref;

    private int eventID;

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
    private String idOfPlace;
    private ArrayAdapter arrayAdapter;

    /**
     * EVENT DATE
     */
    private Calendar calendar;
    private DatePickerDialog.OnDateSetListener eventDatePickerDialog;
    private EditText eventDate;

    /**
     * EVENT TIME
     */
    private EditText eventStartTime;
    private EditText eventEndTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editEventPresenter = new EditEventPresenterImpl(this, sharedPref);

        /**
         *  EVENT PLACE
         */

        eventPlace = (AutoCompleteTextView) findViewById(R.id.etEditEventPlace);
        eventPlace.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userWroteSearch = true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        eventPlace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                userWroteSearch = false;
                Place p = (Place) parent.getItemAtPosition(position);
                idOfPlace = p.getId();
                System.out.println("Place id " + idOfPlace);
            }
        });

        eventPrice = (EditText) findViewById(R.id.etEditEventPrice);
        eventDescription = (EditText) findViewById(R.id.etEditEventDescription);

        /**
         *  DATE AND CALENDAR
         */
        eventDate = (EditText) findViewById(R.id.etEditEventStartDate);
        calendar = Calendar.getInstance();
        eventDatePickerDialog = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                eventDate.setText(sdf.format(calendar.getTime()));
            }
        };

        eventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditEventActivity.this, eventDatePickerDialog, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        /**
         *  START AND END TIME
         */

        eventStartTime = (EditText) findViewById(R.id.etEditEventStartTime);
        eventStartTime.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;

                mTimePicker = new TimePickerDialog(EditEventActivity.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedMinute == 0) {
                            eventStartTime.setText(selectedHour + ":" + String.valueOf(selectedMinute) + "0");
                        } else {
                            eventStartTime.setText(selectedHour + ":" + selectedMinute);
                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        eventEndTime = (EditText) findViewById(R.id.etEditEventEndTime);

        eventEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(EditEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

        eventType = (EditText) findViewById(R.id.etEditEventType);
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
        eventName = (EditText) findViewById(R.id.etEditEventName);
        eventMaxAttendance = (EditText) findViewById(R.id.etEditEventMaxAttendance);
        eventPrivate = (CheckBox) findViewById(R.id.cbEditEventPrivate);

        //Set all information of the event in edit text fields
        Bundle bundle = getIntent().getExtras();
        this.eventID = (bundle.getInt("eventId"));
        eventName.setText(bundle.getString("eventName"));
//        eventPlace.setText(bundle.getString("place"));
        Place p = (Place) getIntent().getSerializableExtra("place");
        eventPlace.setText(p.getName());
        userWroteSearch = false;

        //TODO: StartDate, endDate, startTime, endTime
        eventDate.setText(bundle.getString("eventDate"));
        eventStartTime.setText(bundle.getString("startTime"));
        eventEndTime.setText(bundle.getString("endTime"));
        eventType.setText(bundle.getString("eventType"));
        idOfPlace = p.getId();

        String maxAttendance = String.valueOf(bundle.getInt("maxAttendance"));
        if(!maxAttendance.equals("0")){
            eventMaxAttendance.setText(maxAttendance);
        }
        String price = String.valueOf(bundle.getInt("price"));
        if(!price.equals("0")){
            eventPrice.setText(price);
        }

        eventPrivate.setChecked(bundle.getBoolean("privateEvent"));
        System.out.println("Privat " + bundle.getBoolean("privateEvent"));

        eventDescription.setText(bundle.getString("description"));
//        eventMaxAttendance.setText(bundle.getInt("maxAttendance"));

    }

    public void goToEventArea(){
        Intent goToEventAreaViewIntent = new Intent(EditEventActivity.this, EventAreaActivity.class);

        goToEventAreaViewIntent.putExtra("eventId", eventID);
        EditEventActivity.this.startActivity(goToEventAreaViewIntent);
    }

    public void editEvent(View v){
        editEventPresenter.editEvent(eventID);
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
    public String getEventPlaceId() {
        return idOfPlace;
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
    public void setEventName(String eventName) {
        this.eventName.setText(eventName);
    }

    @Override
    public void setEventPlace(String eventPlace) {
        this.eventPlace.setText(eventPlace);
    }

    @Override
    public void setDate(String date) {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        eventDate.setText(sdf.format(date));
    }

    @Override
    public void setStartTime(String startTime) {

    }

    @Override
    public void setEndTime(String endTime) {

    }

    @Override
    public void setEventType(String eventType) {
        this.eventType.setText(eventType);
    }

    @Override
    public void setMaxAttendance(int maxAttendance) {
        this.eventMaxAttendance.setText(maxAttendance);
    }

    @Override
    public void setPrice(int price) {
        this.eventPrice.setText(price);
    }

    @Override
    public void setPrivate(boolean privateEvent) {
        if(privateEvent){
            eventPrivate.setChecked(true);
        }
    }

    @Override
    public void showEventNameEmptyError(int resId) {
        eventName.setError(getString(resId));
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
    public void showEventPriceWrongFormatError(int resId) {
        eventPrice.setError(getString(resId));
    }

    @Override
    public void showEventDateEmptyError(int resId) {
        eventDate.setError(getString(resId));
    }

    @Override
    public void showEventDateFormatError(int resId) {
        eventDate.setError(getString(resId));
    }

    @Override
    public void showEventPlaceEmptyError(int resId) {
        eventPlace.setError(getString(resId));
    }

    @Override
    public void showToastToUser(String apiResponse) {
        Toast.makeText(this, apiResponse, Toast.LENGTH_LONG).show();
    }

    @Override
    public void updatePlaceAdapter(ArrayList<Place> arr) {
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr);
        eventPlace.setAdapter(arrayAdapter);
    }

    @Override
    public boolean getUserWroteSearch() {
        return userWroteSearch;
    }

    @Override
    public int getEventID() {
        return eventID;
    }
}
