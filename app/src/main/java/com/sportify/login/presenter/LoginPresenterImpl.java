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
        this.token = sharedPref.getString("jwt", "");
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
    public void loginUserFacebook(AccessToken accessToken) {
        Log.d(TAG, "Facebook user" + accessToken.getUserId().toString());
        Log.d(TAG, "Facebook token" + accessToken.getToken().toString());
        Log.d(TAG, "Facebook permissions" + accessToken.getPermissions().toString());
        /**
         *  Convert to JSON object
         */
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("fbtoken", accessToken.getToken());
            jsonObject.put("userId", accessToken.getUserId());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        loginRequest.makeApiRequest(jsonObject.toString(), "https://pvt15app.herokuapp.com/api/loginFacebook");

    }

    @Override
    public void closeProgressDialog() {
        loginView.closeProgressDialog();
    }

    @Override
    public void showApiResponse(String... params) {
        /* response code 200 maps to successful login and 201 to facebookLogin */

            if (params[1].equals("200") || params[1].equals("201")){
                saveToPreferences(params);
                loginView.launchUserActivity();
        }
    }

    public void saveToPreferences(String... params) {
        //TODO: When LOGOUT is created these values need to be set to ""
        String fbTokenLong = null;
        String jwt = null;


        JSONObject json = null;
        try {
            json = new JSONObject(params[0]);
            jwt = json.getString("JWT");
            if (params[1].equals("201")){
                fbTokenLong = json.getString("fbTokenLong");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (json != null) {
            SharedPreferences.Editor editor = sharedPref.edit();                    //Initializes the editor
            editor.putString("jwt", jwt);                                 //Adds the string SharedPref with key "jwt"
            if (fbTokenLong!=null) {editor.putString("facebook", fbTokenLong);}   //Adds the string SharedPref with key "facebook"
            editor.apply();
            Log.d(TAG, "From preferences file below." );
            Log.d(TAG, "jwt: " + sharedPref.getString("jwt", ""));
            Log.d(TAG, "facebook: " + sharedPref.getString("facebook", ""));
        }
    }

}
