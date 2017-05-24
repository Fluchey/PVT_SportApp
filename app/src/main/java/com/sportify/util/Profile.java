package com.sportify.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by peradrianbergman on 2017-05-13.
 * Reusing ProfileRequest class.
 */

public class Profile {

//        // image needs to be stored as string
//        editor.putString("imageBase64", imageBase64);


    //Takes a bitmap, encodes it and returns base64String
    public static String encodeBitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] arr = baos.toByteArray();
        return Base64.encodeToString(arr, Base64.NO_WRAP);
    }

    //Takes a base64String, decodes and returns Bitmap
    public static Bitmap decodeStringToBitmap(String input) {
        byte[] decodedString = Base64.decode(input, Base64.NO_WRAP);
        InputStream inputStream  = new ByteArrayInputStream(decodedString);
        Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }

    //Converts the json string "interests" to List<String
    public static List<String> getInterestsListFromString(String interestsString) {
        interestsString = interestsString.replace("[", "");
        interestsString = interestsString.replace("]", "");
        return new ArrayList<>(Arrays.asList(interestsString.split(", ")));
    }

    public static int getAge(String dateOfBirth) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        String[] strArray = dateOfBirth.split("-");
        int[] intArray = new int[strArray.length];
        for (int i = 0; i < strArray.length; i++) {
            intArray[i] = Integer.parseInt(strArray[i]);
        }

        int year = intArray[0];
        int month = intArray[1];
        int day = intArray[2];
        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        return age+1;
    }
}
