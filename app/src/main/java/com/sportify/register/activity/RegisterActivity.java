package com.sportify.register.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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
    private TextView wrongFormatMessage;

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
        wrongFormatMessage = (TextView) findViewById(R.id.tvSignUpMessage);
    }

    /**
     * Triggered when user clicks on sign up button
     *
     * @param v
     */
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
    public void showEnterAllFieldsError() {
        wrongFormatMessage.setText("Please enter data in all fields");
    }

    @Override
    public void showPasswordEmptyError() {
        wrongFormatMessage.setText("Invalid Password, try again");
    }

    @Override
    public void showEmailWrongFormatError() {
        wrongFormatMessage.setText("Invalid Email, try again");
    }

    @Override
    public void showApiRequestMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void emptyErrorMessage() {
        wrongFormatMessage.setText("");
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

