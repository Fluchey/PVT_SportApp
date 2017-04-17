package com.sportify.register.activity;

/**
 * Created by fluchey on 2017-04-16.
 */

public interface RegisterView {
    String getUserName();

    String getFirstName();

    String getLastName();

    String getMail();

    String getPhoneNumber();

    String getPassword();

    void showEnterAllFieldsError();

    void showPasswordEmptyError();

    void showEmailWrongFormatError();

    void showApiRequestMessage(String message);

    void showProgressDialog();

    void closeProgressDialog();

    void emptyErrorMessage();
}
