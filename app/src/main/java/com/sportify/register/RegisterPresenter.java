package com.sportify.register;

/**
 * Created by fluchey on 2017-04-16.
 */

public interface RegisterPresenter {
    public void createAccount(String username, String password, String firstName, String lastName, String phoneNumber, String email);
}
