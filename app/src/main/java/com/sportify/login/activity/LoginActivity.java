package com.sportify.login.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.sportify.login.presenter.LoginPresenterImpl;
import com.sportify.profile.activity.ProfileActivity;
import com.sportify.register.activity.RegisterActivity;
import com.sportify.userArea.activity.UserAreaActivity;

import sportapp.pvt_sportapp.R;

public class LoginActivity extends AppCompatActivity implements LoginView {
    private EditText email;
    private EditText password;
    private CallbackManager callbackManager; //Added Private
    private ProgressDialog dialog;
    private LoginPresenterImpl loginPresenter;
    private SharedPreferences sharedPref;
    private LoginButton loginFacebookButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main_login);
        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        loginPresenter = new LoginPresenterImpl(this, sharedPref);
        dialog = new ProgressDialog(this);
        initializeControls();
        loginWithFacebook();
        if (facebookIsLoggedIn()) {
            Log.d("LoginActivity.java", "logged in true");
            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            loginPresenter.loginUserFacebook(accessToken);
            Log.d("LoginActivity.java", sharedPref.getString("firstName", ""));
            Log.d("LoginActivity", "imageBase64: " + sharedPref.getString("imageBase64", ""));
        }

        email = (EditText) findViewById(R.id.etLoginEmail);
        password = (EditText) findViewById(R.id.etLoginPassword);
    }


    //TODO: Move Facebook in presenter/request
    private void initializeControls(){ //Facebook controls
        loginFacebookButton = (LoginButton) findViewById(R.id.loginFacebookButton);
        //permissions "birthday" requires: https://developers.facebook.com/docs/facebook-login/review/what-is-login-review
        loginFacebookButton.setReadPermissions("email", "user_friends");
        callbackManager = CallbackManager.Factory.create();
    }

    private void loginWithFacebook() { //LoginManager
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(), "Inloggningen lyckades! :)", Toast.LENGTH_LONG).show();
                AccessToken accessToken = loginResult.getAccessToken();
                loginPresenter.loginUserFacebook(accessToken);
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Inloggningen avbröts", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "Inloggningsfel: "+error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override //Forwards Facebook result back to callbackManager
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
        dialog.setMessage("Loggar in...");
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
        Intent goToUserAreaIntent = new Intent(LoginActivity.this, UserAreaActivity.class);
        goToUserAreaIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        LoginActivity.this.startActivity(goToUserAreaIntent);
    }

    @Override
    public void launchProfileActivity(int userID){
        Intent goToProfileActivityIntent = new Intent(LoginActivity.this, ProfileActivity.class);
        goToProfileActivityIntent.putExtra("userID", userID);
        LoginActivity.this.startActivity(goToProfileActivityIntent);
    }

    private boolean facebookIsLoggedIn(){
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken==null || accessToken.isExpired()) return false;
        return true;
    }
}