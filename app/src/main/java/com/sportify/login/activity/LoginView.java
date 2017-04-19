package com.sportify.login.activity;


/**
 * Created by fluchey on 2017-04-16.
 */

public interface LoginView {
    String getUsername();
    String getPassword();

    void showUsernameEmptyError(int username_empty_error);
    void showPasswordEmptyError(int username_empty_error);
    void showApiRequestMessage(String message);
    void launchUserActivity();
}
