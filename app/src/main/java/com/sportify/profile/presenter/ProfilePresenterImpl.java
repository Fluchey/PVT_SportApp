package com.sportify.profile.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.sportify.profile.activity.ProfileView;
import com.sportify.profile.request.ProfileRequest;
import com.sportify.profile.request.ProfileRequestImpl;
import com.sportify.util.Profile;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
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
            imageBase64 = Profile.encodeBitMapToString(image);
        }

        if (firstname.isEmpty()) {
            profileView.showFirstNameEmptyError(R.string.firstName_Empty_error);
        } else if (lastname.isEmpty()) {
            profileView.showLastNameEmptyError(R.string.lastName_Empty_error);

        } else if (dateOfBirth.isEmpty()){
            profileView.showDateOfBirthEmptyError(R.string.dateOfBirth_Empty_error);

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
            profileView.showProgressDialog();
            profileRequest.makeApiRequest(jsonObject.toString(), "https://pvt15app.herokuapp.com/api/updateProfileInfo");
        }
    }

    /**
     * @param params
     *        params[0] = Json Body text
     *        params[1] = ResponseCode (200,201..)
     */
    @Override
    public void showApiResponse(String... params) {
        //Log.d("Params [0]", params[0]);
        if(params[1].equals("201")){
            Toast.makeText((Context) profileView, "Profilinformation inmatad!", Toast.LENGTH_SHORT).show();
            saveToPreferences();
            profileView.goToLoginActivity();
        } else {
            Toast.makeText((Context) profileView, params[0], Toast.LENGTH_SHORT).show();
        }
    }

    private void saveToPreferences() {
        //Save the updated info down to profile so we can display it on UserAreaActivity
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("firstName", profileView.getProfileFirstName());
        editor.putString("lastName", profileView.getProfileLastName());
        editor.putString("dateOfBirth", profileView.getDateOfBirth());
        editor.putString("userBio", profileView.getUserBio());

        //Retrieve and store ProfilePic as StringBase64
        Bitmap image = profileView.getProfileImage();
        String imageBase64 = "";
        if (image!=null && profileView.userSelectedImage()) {
            imageBase64 = Profile.encodeBitMapToString(image);
            editor.putString("imageBase64", imageBase64);
        }

        //Convert to get same format as when sending back from Heroku
        List<String> interests = new ArrayList<>(profileView.getInterests());
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("interests", interests);
            String interestsString = jsonObject.getString("interests");
            editor.putString("interests", interestsString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        editor.apply();
        Log.d(TAG, "LOGIN PRESENTER imageBase64: " + sharedPref.getString("imageBase64", ""));
    }

    @Override
    public void closeProgressDialog() {
        profileView.closeProgressDialog();
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
