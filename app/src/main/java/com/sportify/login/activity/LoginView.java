package com.sportify.login.activity;

/**
 * Created by fluchey on 2017-04-16.
 */

public interface LoginView {
    String getEmail();
    String getPassword();

    void showEmailEmptyError(int resId);
    void showEmailWrongFormatError(int resId);
    void showPasswordEmptyError(int resId);
    void showApiRequestMessage(String message);
    void showProgressDialog();
    void closeProgressDialog();
    void launchUserActivity();
    void launchProfileActivity(int userID);
}
