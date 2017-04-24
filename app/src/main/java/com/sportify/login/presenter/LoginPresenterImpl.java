package com.sportify.login.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Patterns;

import com.sportify.login.activity.LoginActivity;
import com.sportify.login.activity.LoginView;
import com.sportify.login.request.LoginRequest;
import com.sportify.login.request.LoginRequestImpl;

import org.json.JSONException;
import org.json.JSONObject;

import sportapp.pvt_sportapp.R;

import static com.sportify.login.activity.LoginActivity.MyPREFERENCES;

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
//        Log.d(TAG, "loginUser: " + email);
//        Log.d(TAG, "loginPW: " + password);

        if (email.isEmpty()) {
            loginView.showEmailEmptyError(R.string.email_Empty_error);
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginView.showEmailWrongFormatError(R.string.email_wrongFormat_error);
        } else if (password.isEmpty()) {
            loginView.showPasswordEmptyError(R.string.password_empty_error);
        } else {
            /**
             *  Convert to JSON object
             */
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("mail", email);
                jsonObject.put("password", password);
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
    public void showApiResponse(String apiResponse, String responseOk) {
        /* responsecode 200 maps to successful login */
        if(responseOk.equals("200")){
            //TODO: store String apiResponse JSONwebtoken in SharePreferences
            Log.d(TAG, "showApiResponse: " + apiResponse);
            // SharedPreferences pref = loginView.getSharedPreferences("MyPrefs",Context.MODE_PRIVATE); //Optimal solution
            SharedPreferences sharedPref = loginView.getSharedPreferences();
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("Token", apiResponse);
            editor.apply();

            loginView.launchUserActivity();
        }else {
            loginView.showApiRequestMessage(apiResponse);
        }
    }

}
