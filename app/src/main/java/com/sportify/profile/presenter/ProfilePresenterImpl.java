package com.sportify.profile.presenter;

import android.content.SharedPreferences;

import com.sportify.profile.activity.ProfileView;
import com.sportify.profile.request.ProfileRequest;
import com.sportify.profile.request.ProfileRequestImpl;

/**
 * Created by peradrianbergman on 2017-05-05.
 */

public class ProfilePresenterImpl implements ProfilePresenter, ProfileRequest.OnCreateProfileFinishedListener {
    private static final String TAG = "ProfilePresenterImpl";
    private ProfileView profileView;
    private ProfileRequest profileRequest;
    private SharedPreferences sharedPref;
    private String token = "";

    public ProfilePresenterImpl(ProfileView profileView, SharedPreferences sharedPref){
        this.profileView = profileView;
        this.sharedPref = sharedPref;
        this.token = sharedPref.getString("jwt", "");
        profileRequest = new ProfileRequestImpl(this, token);
    }


    @Override
    public void createProfile() {

    }

    @Override
    public void addProfilePicture() {

    }
}
