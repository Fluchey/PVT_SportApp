package com.sportify.friendprofile.presenter;

import android.content.SharedPreferences;

import com.sportify.friendprofile.activity.FriendprofileView;
import com.sportify.friendprofile.request.FriendprofileRequest;
import com.sportify.friendprofile.request.FriendprofileRequestImpl;
import com.sportify.storage.PlaceReview;
import com.sportify.storage.Profile;

import java.util.ArrayList;

/**
 * Created by rasmu on 25/05/2017.
 */

public class FriendprofilePresenterImpl implements FriendprofilePresenter, FriendprofileRequest.onRequestFinishedListener {
    private SharedPreferences sharedPref;

    private FriendprofileRequest request;
    private FriendprofileView activity;

    public FriendprofilePresenterImpl(FriendprofileView activity, SharedPreferences sharedPref) {
        this.sharedPref = sharedPref;

        request = new FriendprofileRequestImpl(this, "");
        this.activity = activity;
    }

    public void updateInfo(){
        request.updateProfile(activity.getFriendId());
    }

    public void showFriendInfo(Profile info){
        activity.setNameView(info.getFirstname() + " " + info.getLastname());
        activity.setAgeView(info.getAge() + "");
        activity.setDescriptionView(info.getDescription());
        activity.setInterestsView(info.getInterests());
        activity.setPictureView(info.getPicture());
    }

    @Override
    public void showApiResponse(String command, String... params) {
        if (params[0] == null) {
            return;
        }
        switch (command) {
            case "showInfo":
                request.setProfile(params[0]);
                showFriendInfo(request.getProfile());
                break;
        }
    }
}
