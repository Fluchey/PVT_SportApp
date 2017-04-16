package com.sportify.register;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    }

    /**
     * Triggered when user clicks on sign up button
     *
     * @param v
     */
    public void signUpButtonClick(View v) {
        username = (EditText) findViewById(R.id.etRegisterUsername);
        password = (EditText) findViewById(R.id.etRegisterPassword);
        firstName = (EditText) findViewById(R.id.etRegisterFirstName);
        lastName = (EditText) findViewById(R.id.etRegisterLastName);
        phoneNumber = (EditText) findViewById(R.id.etRegisterPhoneNumber);
        email = (EditText) findViewById(R.id.etRegisterMail);
        wrongFormatMessage = (TextView) findViewById(R.id.tvSignUpMessage);

        registerPresenter.createAccount(username.getText().toString(),
                password.getText().toString(),
                firstName.getText().toString(),
                lastName.getText().toString(),
                phoneNumber.getText().toString(),
                email.getText().toString());
    }

    @Override
    public void showUserNameEmptyError() {
        wrongFormatMessage.setText("Invalid Username, try again");
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

