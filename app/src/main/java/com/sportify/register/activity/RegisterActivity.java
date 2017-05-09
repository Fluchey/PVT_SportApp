package com.sportify.register.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sportify.profile.activity.ProfileActivity;
import com.sportify.register.presenter.RegisterPresenter;
import com.sportify.register.presenter.RegisterPresenterImpl;

import sportapp.pvt_sportapp.R;

public class RegisterActivity extends AppCompatActivity implements RegisterView {
    private EditText username;
    private EditText email;
    private EditText password;
    private ProgressDialog dialog;
    private SharedPreferences sharedPref;
    private RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        registerPresenter = new RegisterPresenterImpl(this, sharedPref);
        dialog = new ProgressDialog(this);

        username = (EditText) findViewById(R.id.etRegisterUserName);
        password = (EditText) findViewById(R.id.etRegisterPassword);
        email = (EditText) findViewById(R.id.etRegisterMail);
    }

    public void signUpButtonClick(View v) {
        registerPresenter.createAccount();
    }

    @Override
    public String getMail() {
        return email.getText().toString();
    }

    public String getUsername() {
        return username.getText().toString();
    }

    @Override
    public String getPassword() {
        return password.getText().toString();
    }

    @Override
    public void showPasswordEmptyError(int resId) {
        password.setError(getString(resId));
    }

    @Override
    public void showEmailEmptyError(int resId) {
        email.setError(getString(resId));
    }

    @Override
    public void showEmailWrongFormatError(int resId) {
        email.setError(getString(resId));
    }

    @Override
    public void showApiRequestMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        //responseCode is not handled by any Register Class so we check the responseCode
        if (!message.isEmpty() && message!=null &&
                message.equalsIgnoreCase("User succesfully created")) {
            Intent gotoCreateUserProfile = new Intent(RegisterActivity.this, ProfileActivity.class);
            RegisterActivity.this.startActivity(gotoCreateUserProfile);
        }
    }

    @Override
    public void showProgressDialog() {
        dialog.setMessage("Creating account, please wait");
        dialog.show();
    }

    @Override
    public void closeProgressDialog() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void showUsernameEmptyError(int resId) {
        username.setError(getString(resId));
    }
}

