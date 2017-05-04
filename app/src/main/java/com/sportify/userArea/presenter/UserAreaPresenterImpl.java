package com.sportify.userArea.presenter;

import android.content.SharedPreferences;

import com.sportify.userArea.activity.UserAreaView;
import com.sportify.userArea.request.UserAreaRequest;
import com.sportify.userArea.request.UserAreaRequestImpl;

/**
 * Created by fluchey on 2017-04-20.
 */

public class UserAreaPresenterImpl implements UserAreaPresenter {
    private UserAreaView userAreaView;
    private UserAreaRequest userAreaRequest;
    private SharedPreferences sharedPref;
    private String token = "";

    public UserAreaPresenterImpl(UserAreaView userAreaView, SharedPreferences sharedPref){
        this.userAreaView = userAreaView;
        this.sharedPref = sharedPref;
        this.token = sharedPref.getString("jwt", "");
        userAreaRequest = new UserAreaRequestImpl(token);
    }

}
