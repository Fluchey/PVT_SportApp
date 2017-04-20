package com.sportify.userArea.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sportify.userArea.presenter.UserAreaPresenter;
import com.sportify.userArea.presenter.UserAreaPresenterImpl;

import sportapp.pvt_sportapp.R;

public class UserAreaActivity extends AppCompatActivity implements UserAreaView {
    private UserAreaPresenter userAreaPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        userAreaPresenter = new UserAreaPresenterImpl();
    }


    public void findEventButtonClick(View v) {
        Toast.makeText(this, "I do nothing, ask my developers why", Toast.LENGTH_LONG).show();
    }

    public void createEventButtonClick(View v) {
        Toast.makeText(this, "I do nothing, ask my developers why", Toast.LENGTH_LONG).show();
    }
}
