package com.sportify.register.presenter;

import android.util.Patterns;

import com.sportify.register.request.RegisterRequest;
import com.sportify.register.request.RegisterRequestImpl;
import com.sportify.register.activity.RegisterView;

import org.json.JSONException;
import org.json.JSONObject;

import sportapp.pvt_sportapp.R;

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
    public void createAccount() {
        String username = registerView.getUsername();
        String email = registerView.getMail();
        String password = registerView.getPassword();


        if (username.isEmpty()) {
            registerView.showUsernameEmptyError(R.string.username_empty_error);
        } else if (password.isEmpty()) {
            registerView.showPasswordEmptyError(R.string.password_empty_error);
        } else if (email.isEmpty()) {
            registerView.showEmailEmptyError(R.string.email_Empty_error);
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            registerView.showEmailWrongFormatError(R.string.email_wrongFormat_error);
        } else {
            registerView.showProgressDialog();


            /**
             *  Convert to JSON object
             */
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("mail", email);
                jsonObject.put("username", username);
                jsonObject.put("password", password);
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