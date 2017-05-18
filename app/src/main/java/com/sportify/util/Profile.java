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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by peradrianbergman on 2017-05-13.
 * Reusing ProfileRequest class.
 */

public class Profile{
//        //need to convert interests to set to store it
//        Set<String> set = new HashSet<>(interests);
//        editor.putStringSet("interests", set);
//
//        // image needs to be stored as string
//        editor.putString("imageBase64", imageBase64);


    //Util methods to retreive from SharedPreferences and auto-convert types
    public static String getFirstNameFromSharedPreferences(SharedPreferences sharedPref) {
        return sharedPref.getString("firstName", "");
    }

    public static String getLastNameFromSharedPreferences(SharedPreferences sharedPref) {
        return sharedPref.getString("lastName", "");
    }

    public static String getDateOfBirthFromSharedPreferences(SharedPreferences sharedPref) {
        return sharedPref.getString("dateOfBirth", "");
    }

    public static String getUserBioFromSharedPreferences(SharedPreferences sharedPref) {
        return sharedPref.getString("userBio", "");
    }

    public static List<String> getInterestsFromSharedPreferences(SharedPreferences sharedPref) {
        Set<String> set = new HashSet<>();
        set = sharedPref.getStringSet("interests", null);
        List<String> interests = new ArrayList<>(set);
        return interests;
    }

    public static Bitmap getProfilePictureBitMapFromSharedPreferences(SharedPreferences sharedPref) {
        String imageBase64 = sharedPref.getString("imageBase64", "");
        return decodeStringToBitmap(imageBase64);
    }

    //Takes a bitmap, encodes it and returns base64String
    public static String encodeBitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] arr = baos.toByteArray();
        return Base64.encodeToString(arr, Base64.NO_WRAP);
    }

    //Takes a base64String, decodes and returns Bitmap
    public static Bitmap decodeStringToBitmap(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    //Converts the json string "interests" to List<String
    public static List<String> getInterestsListFromString(String interestsString) {
        interestsString = interestsString.replace("[", "");
        interestsString = interestsString.replace("]", "");
        return new ArrayList<>(Arrays.asList(interestsString.split(", ")));

    }

    public static int getAge(String dateOfBirth){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        String[] strArray = dateOfBirth.split("-");
        int[] intArray = new int[strArray.length];
        for(int i = 0; i < strArray.length; i++) {
            intArray[i] = Integer.parseInt(strArray[i]);
        }
        int year = intArray[0];
        int month = intArray[1];
        int day = intArray[2];

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        return age;
    }
}
