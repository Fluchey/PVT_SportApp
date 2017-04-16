package com.sportify.register;

import android.util.Log;
import android.util.Patterns;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by fluchey on 2017-04-16.
 */

public class RegisterPresenterImpl implements RegisterPresenter, RegisterRequest.OnCreateAccountFinishedListener {
    private RegisterView registerView;
    private RegisterRequest registerRequest;

    public RegisterPresenterImpl(RegisterView registerView) {
        this.registerView = registerView;
        registerRequest = new RegisterRequestImpl(this);
    }

    @Override
    public void createAccount(String username, String password, String firstName, String lastName, String phoneNumber, String email) {
        registerView.showProgressDialog();

        if (username.isEmpty()) {
            registerView.showUserNameEmptyError();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            registerView.showEmailWrongFormatError();
        } else if (password.isEmpty()) {
            registerView.showPasswordEmptyError();
        } else {
            registerView.emptyErrorMessage();

            /**
             *  Convert to JSON object
             */
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("user_id", username);
                jsonObject.put("firstname", firstName);
                jsonObject.put("lastname", lastName);
                jsonObject.put("mobilnummer", phoneNumber);
                jsonObject.put("user_mail", email);
                jsonObject.put("password_user", password);
                Log.d("JsonObject", jsonObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            /**
             * Creates new asynctask which runs in background and tries to create new user
             */
            registerRequest.makeApiRequest(jsonObject.toString());
        }
    }

    @Override
    public void closeProgressDialog() {
        registerView.closeProgressDialog();
    }

    @Override
    public void showApiResponse(String apiResponse) {
        registerView.showApiRequestMessage(apiResponse);
    }
}
