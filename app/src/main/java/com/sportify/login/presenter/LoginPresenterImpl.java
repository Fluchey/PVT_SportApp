package com.sportify.login.presenter;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.sportify.login.activity.LoginView;
import com.sportify.login.request.LoginRequest;
import com.sportify.login.request.LoginRequestImpl;
import com.sportify.util.Profile;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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
            loginView.showProgressDialog();
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

//            loginRequest.makeApiRequest(jsonObject.toString(), "http://130.237.89.152:9000/api/login");

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


    /**
     * @param params
     *        params[0] = Json Body text
     *        params[1] = ResponseCode (200,201..)
     */
    @Override
    public void showApiResponse(String... params) {
        loginView.closeProgressDialog();
        /* response code 200 maps to successful login and 201 to facebookLogin */
        Log.d(TAG, "LoginPresenterImpl.showApiResponse(params[1]) " + params[1]);
        if (params[1].equals("200") || params[1].equals("201")){
            saveToPreferences(params);
            if (facebookNewUser(params)){
                loginView.launchProfileActivity();
            } else {
                loginView.launchUserActivity();
            }
        }else{
            loginView.showApiRequestMessage(params[0]);
        }
    }

    private boolean facebookNewUser(String... params){
        JSONObject json = null;
        Boolean facebookNewUser = false;
        try {
            json = new JSONObject(params[0]);
            facebookNewUser = json.getBoolean("newFacebook");
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return facebookNewUser;
    }

    private void saveToPreferences(String... params) {
        //TODO: When LOGOUT is created these values need to be set to ""
        String fbTokenLong = null;
        String jwt = null;
        int profileID = -1;
        String firstName = "";
        String lastName = "";
        String dateOfBirth = "";
        String userBio = "";
        String interestsString = "";
        String imageBase64 ="";



        JSONObject json = null;
        try {
            json = new JSONObject(params[0]);
            jwt = json.getString("JWT");
            if (params[1].equals("201")){
                fbTokenLong = json.getString("fbTokenLong");
            }
            profileID = json.getInt("profileID");
            firstName = json.getString("firstName");
            lastName = json.getString("lastName");
            dateOfBirth = json.getString("dateOfBirth");
            userBio = json.getString("userBio");
            interestsString = json.getString("interests");
            imageBase64 = json.getString("imageBase64");
            Log.d("interestsString: ", "" + interestsString);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (json != null) {
            SharedPreferences.Editor editor = sharedPref.edit();                    //Initializes the editor
            editor.putString("firstName", firstName);
            editor.putString("lastName", lastName);
            editor.putString("dateOfBirth", dateOfBirth);
            editor.putString("userBio", userBio);
            editor.putString("interests", interestsString);
            editor.putString("imageBase64", imageBase64);
            editor.putString("jwt", jwt);
            editor.putInt("profileID", profileID);

            if (fbTokenLong!=null) {editor.putString("facebook", fbTokenLong);}
            editor.apply();
            Log.d(TAG, "From preferences file below." );
            Log.d(TAG, "jwt: " + sharedPref.getString("jwt", ""));
            Log.d(TAG, "facebook: " + sharedPref.getString("facebook", ""));
            Log.d(TAG, "firstname: " + sharedPref.getString("firstName", ""));
        }
    }

}
