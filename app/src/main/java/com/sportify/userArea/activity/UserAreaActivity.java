package com.sportify.userArea.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import sportapp.pvt_sportapp.R;

public class UserAreaActivity extends AppCompatActivity implements UserAreaView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);
    }

    public void findEventButtonClick(){
        Toast.makeText(this, "I do nothing, ask my developers why", Toast.LENGTH_LONG);
    }
}
