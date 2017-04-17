package com.sportify.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.sportify.event.CreateEventActivity;
import com.sportify.register.activity.RegisterActivity;

import sportapp.pvt_sportapp.R;

public class LoginActivity extends AppCompatActivity { // Extends --> FragmentActivity --> Activity
    LoginButton loginButton;
    CallbackManager callbackManager;
    private AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        initializeControls();
        loginWithFacebook();


        TextView tvLoginRegisterHere = (TextView) findViewById(R.id.tvLoginRegisterHere);
        tvLoginRegisterHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        //Tillfälligt för att kunna testa createEvent
        TextView tvCreateEventHere = (TextView) findViewById(R.id.tvCreateEventHere);
        tvCreateEventHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createEventIntent = new Intent(LoginActivity.this, CreateEventActivity.class);
                LoginActivity.this.startActivity(createEventIntent);
            }
        });
    }

    private void initializeControls(){ //Facebook controls
        callbackManager = CallbackManager.Factory.create();
    }

    private void loginWithFacebook() {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_LONG).show();
                accessToken = loginResult.getAccessToken(); //Needs to be used for FB API calls
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

}