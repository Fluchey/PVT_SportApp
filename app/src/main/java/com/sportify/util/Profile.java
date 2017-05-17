package com.sportify.util;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.sportify.profile.request.ProfileRequest;
import com.sportify.profile.request.ProfileRequestImpl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private String imageBase64;

    //TODO: Include the view in constructor IF closeProgressDialog is required
    //TODO: keep as object retrieved, add option to save to pref and get from pref as static
    public Profile getUserProfile(int profileID, SharedPreferences sharedPref){
        this.sharedPref = sharedPref;
        this.token = sharedPref.getString("jwt", "");
        this.imageBase64 = "";
        profileRequest = new ProfileRequestImpl(this, token);
        getProfileInfo(profileID);
        return this;
    }

    private void getProfileInfo(int profileID){
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
        Log.d("Params [1]", params[1]);
        JSONObject jsonObject;
        if(params[1].equals("201")){
            try {
                jsonObject = new JSONObject(params[0]);
                this.firstName = jsonObject.get("firstName").toString();
                this.lastName =jsonObject.get("lastName").toString();
                this.dateOfBirth = jsonObject.get("dateOfBirth").toString();
                this.userBio = jsonObject.get("userBio").toString();
                interestsString = jsonObject.get("interests").toString();
                this.imageBase64 = jsonObject.get("imageBase64").toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (!interestsString.isEmpty()){
                interestsString = interestsString.replace("[", "");
                interestsString = interestsString.replace("]", "");
                this.interests = new ArrayList<String>(Arrays.asList(interestsString.split(", ")));
            }
            if (!imageBase64.isEmpty())
            this.image = decodeStringToBitmap(imageBase64);
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

    public void saveMyProfileToSharedPreferences(){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("firstName", firstName);
        editor.putString("lastName", lastName);
        editor.putString("dateOfBirth", dateOfBirth);
        editor.putString("userBio", userBio);

        //need to convert interests to set to store it
        Set<String> set = new HashSet<>(interests);
        editor.putStringSet("interests", set);

        // image needs to be stored as string
        editor.putString("imageBase64", imageBase64);

        editor.apply();
    }

    public static String getFirstNameFromSharedPreferences(SharedPreferences sharedPref){
        return sharedPref.getString("firstName", "");
    }

    public static String getLastNameFromSharedPreferences(SharedPreferences sharedPref){
        return sharedPref.getString("lastName", "");
    }

    public static String getDateOfBirthFromSharedPreferences(SharedPreferences sharedPref){
        return sharedPref.getString("dateOfBirth", "");
    }

    public static String getUserBioFromSharedPreferences(SharedPreferences sharedPref){
        return sharedPref.getString("userBio", "");
    }

    public static List<String> getInterestsFromSharedPreferences(SharedPreferences sharedPref){
        Set<String> set = new HashSet<>();
        set = sharedPref.getStringSet("interests", null);
        List<String> interests = new ArrayList<>(set);
        return interests;
    }

    public static Bitmap getProfilePictureBitMapFromSharedPreferences(SharedPreferences sharedPref){
        String imageBase64 = sharedPref.getString("imageBase64", "");
        return decodeStringToBitmap(imageBase64);
    }

    //Not used for now
    @Override
    public void closeProgressDialog() {

    }

    //Takes a bitmap, encodes it and returns base64String
    public static String encodeBitMapToString(Bitmap bitmap)
    {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte[] arr = baos.toByteArray();
        return Base64.encodeToString(arr, Base64.DEFAULT);
    }

    //Takes a base64String, decodes and returns Bitmap
    public static Bitmap decodeStringToBitmap (String input)
    {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0,   decodedByte.length);
    }
}
