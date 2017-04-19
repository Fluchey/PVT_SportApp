package com.sportify.login.presenter;

import android.util.Log;

import com.sportify.login.activity.LoginView;
import com.sportify.login.request.LoginRequest;
import com.sportify.login.request.LoginRequestImpl;

import org.json.JSONException;
import org.json.JSONObject;

import sportapp.pvt_sportapp.R;

/**
 * Created by peradrianbergman on 2017-04-18.
 */

public class LoginPresenterImpl implements LoginPresenter, LoginRequest.OnLoginAccountFinishedListener {
    private static final String TAG = "LoginPresenterImpl";
    private LoginView loginView;
    private LoginRequest loginRequest;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        loginRequest = new LoginRequestImpl(this);
    }

    @Override
    public void loginUser() {
        String email = loginView.getEmail();
        String password = loginView.getPassword();
        Log.d(TAG, "loginUser: " + email);
        Log.d(TAG, "loginPW: " + password);

        if (email.isEmpty()) {
            loginView.showEmailEmptyError(R.string.username_empty_error);
        } else if (password.isEmpty()) {
            loginView.showPasswordEmptyError(R.string.password_empty_error);
        } else {
            //TODO: call LoginRequest.login which will call Rest API
            //loginView.launchUserActivity();

            /**
             *  Convert to JSON object
             */
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("mailadress", email);
                jsonObject.put("lösenord", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            /**
             * Creates new asynctask which runs in background and tries to create new user
             */
            loginRequest.makeApiRequest(jsonObject.toString());

        }
    }

    @Override
    public void loginFB() {
        //TODO: login() OR registerUser()
    }

    @Override
    public void closeProgressDialog() {
        loginView.closeProgressDialog();
    }

    @Override
    public void showApiResponse(String apiResponse) {
        loginView.showApiRequestMessage(apiResponse);
    }


}