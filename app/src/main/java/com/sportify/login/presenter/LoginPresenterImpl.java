package com.sportify.login.presenter;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.sportify.login.activity.LoginActivity;
import com.sportify.login.activity.LoginView;
import com.sportify.login.request.LoginRequest;
import com.sportify.login.request.LoginRequestImpl;
import com.sportify.userArea.UserAreaActivity;

import sportapp.pvt_sportapp.R;

/**
 * Created by peradrianbergman on 2017-04-18.
 */

public class LoginPresenterImpl implements LoginPresenter {
    private static final String TAG = "LoginPresenterImpl";
    private LoginView loginView;
    private LoginRequest loginRequest;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        loginRequest = new LoginRequestImpl(this);
    }

    @Override
    public void loginUser() {
        String username = loginView.getUsername();
        String password = loginView.getPassword();
        Log.d(TAG, "loginUser: " + username);
        Log.d(TAG, "loginPW: " + password);

        if (username.isEmpty()) {
            loginView.showUsernameEmptyError(R.string.username_empty_error);
        } else if (password.isEmpty()) {
            loginView.showPasswordEmptyError(R.string.password_empty_error);
        } else {
            //TODO: call LoginRequest.login which will call Rest API
            loginView.launchUserActivity();
        }
    }

    @Override
    public void loginFB() {
        //TODO: login() OR registerUser()
    }





}
