package com.sportify.register;

/**
 * Created by fluchey on 2017-04-16.
 */

public interface RegisterView {
    void showUserNameEmptyError();

    void showPasswordEmptyError();

    void showEmailWrongFormatError();

    void showApiRequestMessage(String message);

    void showProgressDialog();

    void endProgressDialog();

    void emptyErrorMessage();
}
