package com.sportify.profile.presenter;

import android.content.SharedPreferences;

import com.sportify.profile.activity.ProfileView;
import com.sportify.profile.request.ProfileRequest;
import com.sportify.profile.request.ProfileRequestImpl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import sportapp.pvt_sportapp.R;

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
    public void updateBaseProfileInfo() {
        String firstname = profileView.getProfileFirstName();
        String lastname = profileView.getProfileLastName();
        String dateOfBirth = profileView.getDateOfBirth();
        String userBio = profileView.getUserBio();
        List<String> interests = new ArrayList<>(profileView.getInterests());



        if (firstname.isEmpty()) {
            profileView.showFirstNameEmptyError(R.string.name_Empty_error);
        } else if (lastname.isEmpty()) {
            profileView.showLastNameEmptyError(R.string.name_Empty_error);
        } else if (dateOfBirth.isEmpty()){
            profileView.showDateOfBirthEmptyError(R.string.dateOfBirth_Empty_error);
//        } else if (){ //TODO: write method to check format if necessary.
//            profileView.showDateOfBirthWrongFormatError(R.string.dateOfBirth_wrongFormat_error);
        } else if (interests.isEmpty()) {
            profileView.showNoInterestCheckedError(R.string.interests_Empty_Error);
        } else {
            /**
             *  Convert to JSON object
             */
            JSONObject jsonObject = new JSONObject();
            try {
                //jsonObject.put("profileID", profileID);
                jsonObject.put("firstName", firstname);
                jsonObject.put("lastName", lastname);
                jsonObject.put("dateOfBirth", dateOfBirth);
                jsonObject.put("userBio", userBio);
                //jsonObject.put("interests", interests);
                //TODO: add picture to json request
            } catch (JSONException e) {
                e.printStackTrace();
            }

            profileRequest.makeApiRequest(jsonObject.toString(), "https://pvt15app.herokuapp.com/api/updateProfileInfo");
        }
    }

    @Override
    public void addProfilePicture() {

    }

    @Override
    public void showApiResponse(String... params) {

    }

    @Override
    public void closeProgressDialog() {

    }
}
