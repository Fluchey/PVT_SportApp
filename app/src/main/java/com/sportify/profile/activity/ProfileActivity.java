package com.sportify.profile.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.sportify.profile.presenter.ProfilePresenterImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import sportapp.pvt_sportapp.R;

public class ProfileActivity extends AppCompatActivity implements ProfileView {
    private SharedPreferences sharedPref;
    private ProfilePresenterImpl profilePresenter;
    private EditText firstname;
    private EditText lastname;
    private EditText dateOfBirth;
    private EditText description;
    private CheckBox fotboll, basket, simning, bandy, ridning,
            running, parkour, outdoortraining, skateboarding, badminton;

    private ImageButton checkboxProfileButton;
    private ImageButton profilePictureButton;
    private List<String> interests;
    private Calendar calendar;
    private DatePickerDialog.OnDateSetListener date;
    int userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userID = getIntent().getIntExtra("userID", -1);
        profilePresenter = new ProfilePresenterImpl(this, sharedPref);

        firstname = (EditText) findViewById(R.id.etProfileNameHint);
        lastname = (EditText) findViewById(R.id.etLastnameHint);
        dateOfBirth  = (EditText) findViewById(R.id.etProfileBirthdayhint);
        description  = (EditText) findViewById(R.id.etDescriptionBoxProfileOmMig);
        profilePictureButton = (ImageButton) findViewById(R.id.ibProfilePicturebutton);
        checkboxProfileButton = (ImageButton) findViewById(R.id.ibCheckboxProfileButton);

        fotboll = (CheckBox) findViewById(R.id.cbProfileFotboll);
        basket = (CheckBox) findViewById(R.id.cbProfileBasket);
        simning = (CheckBox) findViewById(R.id.cbProfileSimmning);
        bandy = (CheckBox) findViewById(R.id.cbProfileBandy);
        ridning = (CheckBox) findViewById(R.id.cbProfileHästRidning);
        running = (CheckBox) findViewById(R.id.cbProfileRunning);
        parkour = (CheckBox) findViewById(R.id.cbProfileParkour);
        outdoortraining = (CheckBox) findViewById(R.id.cbProfileoutdoortraining);
        skateboarding = (CheckBox) findViewById(R.id.cbProfileSkateboarding);
        badminton = (CheckBox) findViewById(R.id.cbProfileBadminton);

        calendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                dateOfBirth.setText(sdf.format(calendar.getTime()));
            }
        };

        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ProfileActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    @Override
    public String getProfileFirstName() {
        return firstname.getText().toString();
    }

    @Override
    public String getProfileLastName() {
        return lastname.getText().toString();
    }

    @Override //TODO: See if DatePicker stores this as string.
    public String getDateOfBirth() {
        return dateOfBirth.getText().toString();
    }

    @Override
    public String getUserBio() {
        return description.getText().toString();
    }

    @Override
    public List<String> getInterests() {
        interests = new ArrayList<>();
        if (badminton.isChecked()) interests.add("Badminton");
        if (bandy.isChecked()) interests.add("Bandy");
        if (basket.isChecked()) interests.add("Basket");
        if (fotboll.isChecked()) interests.add("Fotboll");
        if (running.isChecked()) interests.add("Löpning");
        if (parkour.isChecked()) interests.add("Parkour");
        if (ridning.isChecked()) interests.add("Ridning");
        if (simning.isChecked()) interests.add("Simning");
        if (skateboarding.isChecked()) interests.add("Skateboard");
        if (outdoortraining.isChecked()) interests.add("Utegym");

        return interests;
    }

    @Override
    public void showFirstNameEmptyError(int resId) {
        firstname.setError(getString(resId));
    }

    @Override
    public void showLastNameEmptyError(int resId) {
        lastname.setError(getString(resId));
    }

    @Override
    public void showDateOfBirthEmptyError(int resId) {
        dateOfBirth.setError(getString(resId));
    }

    @Override
    public void showDateOfBirthWrongFormatError(int resId) {
        dateOfBirth.setError(getString(resId));
    }

    @Override
    public void showNoInterestCheckedError(int resID) {
        Toast.makeText(this, getString(resID), Toast.LENGTH_LONG).show();
    }

    public void profilePictureButtonClick(View v){
        profilePresenter.addProfilePicture();
    }

    public void checkboxProfileButton(View v){
        profilePresenter.updateBaseProfileInfo(userID);
    }

    @Override
    public void launchLoginActivity() {
        //TODO: Goto Login Screen
    }
}
