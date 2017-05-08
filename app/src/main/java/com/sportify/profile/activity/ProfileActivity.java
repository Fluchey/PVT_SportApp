package com.sportify.profile.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sportapp.pvt_sportapp.R;

public class ProfileActivity extends AppCompatActivity implements ProfileView {
    private EditText name;
    private EditText dateOfBirth;
    private EditText description;
    private CheckBox fotboll, basket, simning, bandy, volleyball,
            outdoortraining, climbing, running, parkour;
    private List<String> interests;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = (EditText) findViewById(R.id.etProfileNameHint);
        dateOfBirth  = (EditText) findViewById(R.id.etProfileBirthdayhint);
        description  = (EditText) findViewById(R.id.etDescriptionBoxProfileOmMig);

        fotboll = (CheckBox) findViewById(R.id.cbProfileFotboll);
        basket = (CheckBox) findViewById(R.id.cbProfileBasket);
        simning = (CheckBox) findViewById(R.id.cbProfileSimmning);
        bandy = (CheckBox) findViewById(R.id.cbProfileBandy);
        volleyball = (CheckBox) findViewById(R.id.cbProfileVolleyball);
        outdoortraining = (CheckBox) findViewById(R.id.cbProfileoutdoortraining);
        climbing = (CheckBox) findViewById(R.id.cbProfileClimbing);
        running = (CheckBox) findViewById(R.id.cbProfileRunning);
        parkour = (CheckBox) findViewById(R.id.cbPorfileParkour);
        interests = new ArrayList<>();
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

    @Override
    public void launchUserActivity() {
        //TODO: Goto UserAreaActivity or to Login Screen?
    }
}
