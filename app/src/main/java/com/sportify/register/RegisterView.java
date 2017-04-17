package com.sportify.register;

/**
 * Created by fluchey on 2017-04-16.
 */

public interface RegisterView {
    void showEnterAllFieldsError();

    void showPasswordEmptyError();

    void showEmailWrongFormatError();

    void showApiRequestMessage(String message);

    void showProgressDialog();

    void closeProgressDialog();

    void emptyErrorMessage();
}
