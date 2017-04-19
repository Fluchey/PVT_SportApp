package com.sportify.register.activity;

/**
 * Created by fluchey on 2017-04-16.
 */

public interface RegisterView {

    String getFirstName();

    String getLastName();

    String getMail();

    String getPassword();

    void showPasswordEmptyError(int resId);

    void showEmailEmptyError(int resId);

    void showEmailWrongFormatError(int resId);

    void showApiRequestMessage(String message);

    void showProgressDialog();

    void closeProgressDialog();
}
