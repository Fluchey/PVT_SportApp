package com.sportify.register.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sportify.register.presenter.RegisterPresenter;
import com.sportify.register.presenter.RegisterPresenterImpl;

import sportapp.pvt_sportapp.R;

public class RegisterActivity extends AppCompatActivity implements RegisterView {
    private EditText username;
    private EditText password;
    private EditText firstName;
    private EditText lastName;
    private EditText phoneNumber;
    private EditText email;

    private ProgressDialog dialog;

    private RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerPresenter = new RegisterPresenterImpl(this);
        dialog = new ProgressDialog(this);

        username = (EditText) findViewById(R.id.etRegisterUsername);
        password = (EditText) findViewById(R.id.etRegisterPassword);
        firstName = (EditText) findViewById(R.id.etRegisterFirstName);
        lastName = (EditText) findViewById(R.id.etRegisterLastName);
        phoneNumber = (EditText) findViewById(R.id.etRegisterPhoneNumber);
        email = (EditText) findViewById(R.id.etRegisterMail);
    }

    public void signUpButtonClick(View v) {
        registerPresenter.createAccount();
    }

    @Override
    public String getUserName() {
        return username.getText().toString();
    }

    @Override
    public String getFirstName() {
        return firstName.getText().toString();
    }

    @Override
    public String getLastName() {
        return lastName.getText().toString();
    }

    @Override
    public String getMail() {
        return email.getText().toString();
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber.getText().toString();
    }

    @Override
    public String getPassword() {
        return password.getText().toString();
    }

    @Override
    public void showUsernameEmptyError(int resId) {
        username.setError(getString(resId));
    }

    @Override
    public void showFirstNameEmptyError(int resId) {
        firstName.setError(getString(resId));
    }

    @Override
    public void showLastNameEmptyError(int resId) {
        lastName.setError(getString(resId));
    }

    @Override
    public void showPhoneNumberEmptyError(int resId) {
        phoneNumber.setError(getString(resId));
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
}

