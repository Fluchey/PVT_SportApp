package com.sportify.profile.presenter;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.sportify.profile.activity.ProfileView;
import com.sportify.profile.request.ProfileRequest;
import com.sportify.profile.request.ProfileRequestImpl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public void updateBaseProfileInfo(int userID) {
        String firstname = profileView.getProfileFirstName();
        String lastname = profileView.getProfileLastName();
        String dateOfBirth = profileView.getDateOfBirth();
        String userBio = profileView.getUserBio();
        List<String> interests = new ArrayList<>(profileView.getInterests());
        Bitmap image = profileView.getProfileImage();
        String imageBase64 = "";

        if (image!=null && profileView.userSelectedImage()) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] imageBytes = bos.toByteArray();
            imageBase64 = Base64.encodeToString(imageBytes, Base64.NO_WRAP);
        }

        if (firstname.isEmpty()) {
            profileView.showFirstNameEmptyError(R.string.firstName_Empty_error);
        } else if (lastname.isEmpty()) {
            profileView.showLastNameEmptyError(R.string.lastName_Empty_error);

            //Current design decision states no dateOfBirth required
//        } else if (dateOfBirth.isEmpty()){
//            profileView.showDateOfBirthEmptyError(R.string.dateOfBirth_Empty_error);

          } else if (!dateOfBirth.isEmpty() && !validDateFormat(dateOfBirth)){
            profileView.showDateOfBirthWrongFormatError(R.string.dateOfBirth_wrongFormat_error);
        } else if (interests.isEmpty()) {
            profileView.showNoInterestCheckedError(R.string.interests_Empty_Error);
        } else {
            /**
             *  Convert to JSON object
             */
            JSONObject jsonObject = new JSONObject();
            Log.d(TAG, "profileID: " + userID);
            Log.d(TAG, "firstName: " + firstname);
            Log.d(TAG, "lastName: " + lastname);
            Log.d(TAG, "dateOfBirth: " + dateOfBirth);
            Log.d(TAG, "userBio: " + userBio);
            Log.d(TAG, "interests: " + interests);
            Log.d(TAG, "imageBase64: " + imageBase64);
            try {
                jsonObject.put("profileID", userID);
                jsonObject.put("firstName", firstname);
                jsonObject.put("lastName", lastname);
                jsonObject.put("dateOfBirth", dateOfBirth);
                jsonObject.put("userBio", userBio);
                jsonObject.put("interests", interests);
                jsonObject.put("imageBase64", imageBase64);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            profileRequest.makeApiRequest(jsonObject.toString(), "https://pvt15app.herokuapp.com/api/updateProfileInfo");
        }
    }

    @Override
    public void showApiResponse(String... params) {

    }

    @Override
    public void closeProgressDialog() {

    }

    private boolean validDateFormat(String date){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        dateFormat.setLenient(false);

        try{
            dateFormat.parse(date);
        }catch (ParseException e){
            return false;
        }
        return true;
    }
}
