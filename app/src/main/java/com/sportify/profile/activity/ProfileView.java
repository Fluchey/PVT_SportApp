package com.sportify.profile.activity;

/**
 * Created by peradrianbergman on 2017-05-05.
 */

public interface ProfileView {
    String getName();
    String getDateOfBirth();
    String[] getInterests();

    void showNameEmptyError(int resId);
    void showDateOfBirthEmptyError(int resId);
    void showDateOfBirthWrongFormatError(int resId);
    void launchUserActivity();
}
