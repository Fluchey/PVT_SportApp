package com.sportify.createEvent.createEventPageOne.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.sportify.createEvent.createEventPageBeforeInviteFriends.activity.CreateEventBeforeInviteFriendsActivity;
import com.sportify.createEvent.createEventPageOne.presenter.CreateEventPageOnePresenter;
import com.sportify.createEvent.createEventPageOne.presenter.CreateEventPageOnePresenterImpl;
import com.sportify.storage.Place;
import com.sportify.userArea.activity.UserAreaActivity;
import com.sportify.util.Profile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import sportapp.pvt_sportapp.R;

/**
 * Created by Maja on 2017-04-18.
 */

public class CreateEventPageOneActivity extends AppCompatActivity implements CreateEventPageOneView {

    /**
     * IMAGES
     */
    private static int RESULT_LOAD_IMG = 1826;
    private static int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 9287;
    String imgDecodableString;
    ImageButton eventPicture;
    boolean customImage;

    /**
     * CONNECTIONS
     */
    private CreateEventPageOnePresenter createEventPageOnePresenter;
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
    private boolean fromMap;
    private String idOfPlace;
    private ArrayAdapter arrayAdapter;
    private String placeId;

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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event_first_page);
        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        createEventPageOnePresenter = new CreateEventPageOnePresenterImpl(this, sharedPref);

        /**
         * Event picture
         */
        eventPicture = (ImageButton) findViewById(R.id.imageButton3);
        customImage = false;


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
                Place p = (Place) parent.getItemAtPosition(position);
                idOfPlace = p.getId();
            }
        });

        eventPrice = (EditText) findViewById(R.id.etEventPrice);
        eventDescription = (EditText) findViewById(R.id.etEventDescription);

        Bundle bundle = getIntent().getExtras();
        try{
            placeId = bundle.getString("placeId");
        }catch (NullPointerException e){

        }


        /**
         *  DATE AND CALENDAR
         */
        eventDate = (EditText) findViewById(R.id.etEventStartDate);
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
                new DatePickerDialog(CreateEventPageOneActivity.this, eventDatePickerDialog, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
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
                mTimePicker = new TimePickerDialog(CreateEventPageOneActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                mTimePicker = new TimePickerDialog(CreateEventPageOneActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

    public void goToInviteFriends(){
        Intent goToInviteFriendsIntent = new Intent(CreateEventPageOneActivity.this, CreateEventBeforeInviteFriendsActivity.class);
        goToInviteFriendsIntent.putExtra("EVENT_ID", eventID);
        goToInviteFriendsIntent.putExtra("EVENT_NAME", getEventName());
        goToInviteFriendsIntent.putExtra("eventDescription", getEventDescription());

        Bitmap image = getEventImage();
        String imageBase64 = "";

        if(image != null && userSelectedImage()){
            imageBase64 = Profile.encodeBitMapToString(image);
        }

        goToInviteFriendsIntent.putExtra("imageBase64", imageBase64);
        CreateEventPageOneActivity.this.startActivity(goToInviteFriendsIntent);
    }

    public void createEventClick(View v) {
        createEventPageOnePresenter.createEvent();
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
    public String getEventDate(){
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
    public void showEventNameEmptyError(int resId) {
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
    public void showEventDateFormatError(int resId) {
        eventDate.setError(getString(resId));
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
        eventDate.setError(null);
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
    public void updatePlaceAdapter(ArrayList<Place> arr) {
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr);
        eventPlace.setAdapter(arrayAdapter);

        try {
            for(Place p : arr){
                if(p.getId().equals(placeId)){
                    eventPlace.setText(p.getName());
                    userWroteSearch = false;
                    idOfPlace = p.getId();
                }
            }
        }catch (NullPointerException e){

        }
    }

    @Override
    public boolean getUserWroteSearch() {
        return userWroteSearch;
    }

    @Override
    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    @Override
    public void eventPictureButtonClick(View v) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // Explain to the user why we need to read the contacts
                }

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                // app-defined int constant that should be quite unique

                return;
            }
            // Create intent to Open Image applications like Gallery, Google Photos
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            // Start the Intent
            startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    public Bitmap getEventImage() {
        ImageButton eventImage = (ImageButton) findViewById(R.id.imageButton3);
        Bitmap image = ((BitmapDrawable) eventImage.getDrawable()).getBitmap();
        return image;
    }

    @Override
    public Boolean userSelectedImage() {
        return customImage;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageButton eventPicture = (ImageButton) findViewById(R.id.imageButton3);

                //Get Height and Width of imageButton
                int image_width = eventPicture.getWidth();
                int image_height = eventPicture.getHeight();

                // Set the Image in ImageButton after scaling the Bitmap to size of imageButton
                Bitmap bitmap = BitmapFactory.decodeFile(imgDecodableString);
                Bitmap image = bitmap.createScaledBitmap(bitmap, image_width, image_height, false); //scale the image

                eventPicture.setImageBitmap(image);
                customImage = true;

            } else {
                Toast.makeText(this, "Var god välj en bild",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Något gick fel :(", Toast.LENGTH_LONG)
                    .show();
        }
    }
}