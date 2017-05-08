package com.sportify.profile.activity;

import java.util.List;

/**
 * Created by peradrianbergman on 2017-05-05.
 */

public interface ProfileView {
    String getProfileName();
    String getDateOfBirth();
    String getUserBio();
    List<String> getInterests();

    void showNameEmptyError(int resId);
    void showDateOfBirthEmptyError(int resId);
    void showDateOfBirthWrongFormatError(int resId);
    void showNoInterestCheckedError(int resID);
    void launchUserActivity();
}
