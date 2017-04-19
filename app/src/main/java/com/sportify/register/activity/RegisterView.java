package com.sportify.register.activity;

/**
 * Created by fluchey on 2017-04-16.
 */

public interface RegisterView {
//    String getUserName();

    String getFirstName();

    String getLastName();

    String getMail();

//    String getPhoneNumber();

    String getPassword();

//    void showUsernameEmptyError(int username_empty_error);

//    void showFirstNameEmptyError(int resId);

//    void showLastNameEmptyError(int resId);

//    void showPhoneNumberEmptyError(int resId);

    void showPasswordEmptyError(int resId);

    void showEmailEmptyError(int resId);

    void showEmailWrongFormatError(int resId);

    void showApiRequestMessage(String message);

    void showProgressDialog();

    void closeProgressDialog();
}
