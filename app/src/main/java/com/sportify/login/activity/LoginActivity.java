package com.sportify.login.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.sportify.login.presenter.LoginPresenterImpl;
import com.sportify.event.activity.CreateEventActivity;
import com.sportify.register.activity.RegisterActivity;
import com.sportify.userArea.UserAreaActivity;

import sportapp.pvt_sportapp.R;

public class LoginActivity extends AppCompatActivity implements LoginView {
    private EditText email;
    private EditText password;
    private LoginButton loginButton;
    private CallbackManager callbackManager; //Added Private
    private ProgressDialog dialog;
    private LoginPresenterImpl loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        loginPresenter = new LoginPresenterImpl(this);
        initializeControls();
        loginWithFacebook();

        email = (EditText) findViewById(R.id.etLoginEmail);
        password = (EditText) findViewById(R.id.etLoginPassword);

        //TODO: Move Register new User to UserAreaActivity
        TextView tvLoginRegisterHere = (TextView) findViewById(R.id.tvLoginRegisterHere);
        tvLoginRegisterHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        //TODO: Move Create Event to UserAreaActivity
        TextView tvCreateEventHere = (TextView) findViewById(R.id.tvCreateEventHere);
        tvCreateEventHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createEventIntent = new Intent(LoginActivity.this, CreateEventActivity.class);
                LoginActivity.this.startActivity(createEventIntent);
            }
        });
    }

    //TODO: Move Facebook in presenter/request
    private void initializeControls(){ //Facebook controls
        callbackManager = CallbackManager.Factory.create();
    }

    private void loginWithFacebook() {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_LONG).show();
                //accessToken = loginResult.getAccessToken(); //Needs to be used for FB API calls
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Login Cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "Login error: "+error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void loginButtonClick(View v) {
        loginPresenter.loginUser();
    }

    public String getEmail() {
        return email.getText().toString();
    }

    @Override
    public String getPassword() {
        return password.getText().toString();
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
    public void showPasswordEmptyError(int resId) {
        password.setError(getString(resId));
    }

    @Override
    public void showApiRequestMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressDialog() {
        dialog.setMessage("Signing in, please wait");
        dialog.show();
    }

    @Override
    public void closeProgressDialog() {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
    }

    @Override
    public void launchUserActivity() {
        startActivity(new Intent(LoginActivity.this, UserAreaActivity.class));
    }
}