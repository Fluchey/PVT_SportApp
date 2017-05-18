package com.sportify.profile.activity;


import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by peradrianbergman on 2017-05-05.
 */

public interface ProfileView {
    String getProfileFirstName();
    String getProfileLastName();
    String getDateOfBirth();
    String getUserBio();
    List<String> getInterests();
    Bitmap getProfileImage();
    Boolean userSelectedImage();

    void showFirstNameEmptyError(int resId);
    void showLastNameEmptyError(int resId);
    void showDateOfBirthEmptyError(int resId);
    void showDateOfBirthWrongFormatError(int resId);
    void showNoInterestCheckedError(int resID);

    void showProgressDialog();
    void closeProgressDialog();

    void goToLoginActivity();
}
