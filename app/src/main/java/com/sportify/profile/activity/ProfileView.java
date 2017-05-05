package com.sportify.profile.activity;

import java.util.ArrayList;

/**
 * Created by peradrianbergman on 2017-05-05.
 */

public interface ProfileView {
    String getProfileName();
    String getDateOfBirth();
    String getUserBio();
    ArrayList<String> getInterests();

    void showNameEmptyError(int resId);
    void showDateOfBirthEmptyError(int resId);
    void showDateOfBirthWrongFormatError(int resId);
    void launchUserActivity();
}
