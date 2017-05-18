package com.sportify.editEvent.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sportify.eventArea.activity.EventAreaActivity;

import sportapp.pvt_sportapp.R;

public class EditEventActivity extends AppCompatActivity implements EditEventView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
    }


    public void goToEventAreaFromEditButton(View v){
        Intent goToEventAreaViewIntent = new Intent(EditEventActivity.this, EventAreaActivity.class);
        EditEventActivity.this.startActivity(goToEventAreaViewIntent);
    }


}
