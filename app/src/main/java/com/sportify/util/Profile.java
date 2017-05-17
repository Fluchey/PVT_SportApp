package com.sportify.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.sportify.profile.request.ProfileRequest;
import com.sportify.profile.request.ProfileRequestImpl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by peradrianbergman on 2017-05-13.
 * Reusing ProfileRequest class.
 */

public class Profile implements ProfileRequest.OnCreateProfileFinishedListener {
    private ProfileRequest profileRequest;
    private SharedPreferences sharedPref;
    private String token;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String userBio;
    private List<String> interests;
    private Bitmap image;
    //Boolean userSelectedImage;

    //TODO: Include the view in constructor IF closeProgressDialog is required
    //TODO: keep as object retreived, add option to save to pref and get from pref as static
    public Profile userProfile(int profileID, SharedPreferences sharedPref){
        this.sharedPref = sharedPref;
        this.token = sharedPref.getString("jwt", "");
        profileRequest = new ProfileRequestImpl(this, token);
        getProfileInfo(profileID, token);
        return this;
    }

    public void getProfileInfo(int profileID, String token){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("profileID", profileID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        profileRequest.makeApiRequest(jsonObject.toString(), "https://pvt15app.herokuapp.com/api/getProfileInfo");
    }

    /**
     * @param params
     *        params[0] = Json Body text
     *        params[1] = ResponseCode (200,201..)
     */
    @Override
    public void showApiResponse(String... params) {
        String interestsString = "";
        String imageBase64 = "";
        Log.d("Params [1]", params[1]);
        JSONObject jsonObject = null;
        if(params[1].equals("201")){
            try {
                jsonObject = new JSONObject(params[0]);
                this.firstName = jsonObject.get("firstName").toString();
                this.lastName =jsonObject.get("lastName").toString();
                this.dateOfBirth = jsonObject.get("dateOfBirth").toString();
                this.userBio = jsonObject.get("userBio").toString();
                interestsString = jsonObject.get("interests").toString();
                imageBase64 = jsonObject.get("imageBase64").toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (!interestsString.isEmpty()){
                interestsString = interestsString.replace("[", "");
                interestsString = interestsString.replace("]", "");
                this.interests = new ArrayList<String>(Arrays.asList(interestsString.split(", ")));
            }
            byte[] imageBytes = null;
            if (!imageBase64.isEmpty()) {
                imageBytes = Base64.decode(imageBase64, 0);
            }
            this.image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

        }

    }


    public String getProfileFirstName(){
        return firstName;
    }

    public String getProfileLastName(){
        return lastName;
    }

    private String getDateOfBirth(){
        return dateOfBirth;
    }

    private String getUserBio(){
        return userBio;
    }

    private List<String> getInterests(){
        return interests;
    }
    private Bitmap getProfileImage(){
        return image;
    }

    //Not used
    @Override
    public void closeProgressDialog() {

    }
}
