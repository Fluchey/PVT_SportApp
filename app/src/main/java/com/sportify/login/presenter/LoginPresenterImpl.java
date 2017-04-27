package com.sportify.login.presenter;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Patterns;

import com.facebook.AccessToken;
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
    private SharedPreferences sharedPref;
    private String token = "";

    public LoginPresenterImpl(LoginView loginView, SharedPreferences sharedPref) {
        this.loginView = loginView;
        this.sharedPref = sharedPref;
        this.token = sharedPref.getString("Token", "");
        loginRequest = new LoginRequestImpl(this, token);
    }

    @Override
    public void loginUser() {
        String email = loginView.getEmail();
        String password = loginView.getPassword();

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
            loginRequest.makeApiRequest(jsonObject.toString(), "https://pvt15app.herokuapp.com/api/login");
        }
    }

    @Override
    public void requestFacebookLong(AccessToken accessToken) {
        Log.d(TAG, "Facebook userid" + accessToken.getUserId().toString());
        Log.d(TAG, "Facebook token" + accessToken.getToken().toString());
        /**
         *  Convert to JSON object
         */
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("fbtoken", accessToken.getToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        loginRequest.makeApiRequest(jsonObject.toString(), "https://pvt15app.herokuapp.com/api/requestFacebookLong");
    }

    @Override
    public void closeProgressDialog() {
        loginView.closeProgressDialog();
    }

    @Override
    public void showApiResponse(String apiResponse, String responseOk) {
        /* response code 200 maps to successful login */
        if(responseOk.equals("200")){
            Log.d(TAG, "showApiResponse: " + apiResponse);
            SharedPreferences.Editor editor = sharedPref.edit();            //Initializes the editor
            editor.putString("Token", apiResponse);                         //Adds the string SharedPref with key "Token"
            editor.apply();                                                 //Saves changes to SharedPref
            Log.d(TAG, "Saved Token: " + sharedPref.getString("Token", ""));
            loginView.launchUserActivity();
        }else {
            loginView.showApiRequestMessage(apiResponse);
        }
    }

}
