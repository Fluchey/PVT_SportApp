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
    private EditText name;
    private EditText dateOfBirth;
    private EditText description;
    private CheckBox fotboll, basket, simning, bandy, volleyball,
            outdoortraining, climbing, running, parkour;
    private ImageButton checkboxProfileButton;
    private ImageButton profilePictureButton;
    private List<String> interests;
    private Calendar calendar;
    private DatePickerDialog.OnDateSetListener date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        profilePresenter = new ProfilePresenterImpl(this, sharedPref);

        name = (EditText) findViewById(R.id.etProfileNameHint);
        dateOfBirth  = (EditText) findViewById(R.id.etProfileBirthdayhint);
        description  = (EditText) findViewById(R.id.etDescriptionBoxProfileOmMig);
        profilePictureButton = (ImageButton) findViewById(R.id.ibProfilePicturebutton);
        checkboxProfileButton = (ImageButton) findViewById(R.id.ibCheckboxProfileButton);

        fotboll = (CheckBox) findViewById(R.id.cbProfileFotboll);
        basket = (CheckBox) findViewById(R.id.cbProfileBasket);
        simning = (CheckBox) findViewById(R.id.cbProfileSimmning);
        bandy = (CheckBox) findViewById(R.id.cbProfileBandy);
        //volleyball = (CheckBox) findViewById(R.id.cbProfileVolleyball);
        outdoortraining = (CheckBox) findViewById(R.id.cbProfileoutdoortraining);
        //climbing = (CheckBox) findViewById(R.id.cbProfileClimbing);
        running = (CheckBox) findViewById(R.id.cbProfileRunning);
        //parkour = (CheckBox) findViewById(R.id.cbPorfileParkour);
        interests = new ArrayList<>();

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
    public String getProfileName() {
        return name.toString();
    }

    @Override //TODO: See if DatePicker stores this as string.
    public String getDateOfBirth() {
        return dateOfBirth.toString();
    }

    @Override
    public String getUserBio() {
        return description.toString();
    }

    @Override
    public List<String> getInterests() {
        if (fotboll.isChecked()) interests.add("fotboll");
        if (basket.isChecked()) interests.add("basket");
        if (simning.isChecked()) interests.add("simning");
        if (bandy.isChecked()) interests.add("bandy");
        if (volleyball.isChecked()) interests.add("volleyball");
        if (outdoortraining.isChecked()) interests.add("outdoortraining");
        if (climbing.isChecked()) interests.add("climbing");
        if (running.isChecked()) interests.add("running");
        if (parkour.isChecked()) interests.add("parkour");
        return interests;
    }

    @Override
    public void showNameEmptyError(int resId) {
        name.setError(getString(resId));
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

    public void profilePictureButtonClick(){
        profilePresenter.addProfilePicture();
    }

    public void checkboxProfileButton(){
        profilePresenter.createProfile();
    }

    @Override
    public void launchUserActivity() {
        //TODO: Goto Login Screen
    }
}
