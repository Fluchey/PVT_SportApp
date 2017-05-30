package com.sportify.friendprofile.presenter;

import android.content.SharedPreferences;
import android.util.Log;

import com.sportify.friendprofile.activity.FriendprofileView;
import com.sportify.friendprofile.request.FriendprofileRequest;
import com.sportify.friendprofile.request.FriendprofileRequestImpl;
import com.sportify.storage.Event;
import com.sportify.storage.PlaceReview;
import com.sportify.storage.Profile;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by rasmu on 25/05/2017.
 */

public class FriendprofilePresenterImpl implements FriendprofilePresenter, FriendprofileRequest.onRequestFinishedListener {
    private SharedPreferences sharedPref;

    private FriendprofileRequest request;
    private FriendprofileView activity;
    private String token;

    public FriendprofilePresenterImpl(FriendprofileView activity, SharedPreferences sharedPref) {
        this.sharedPref = sharedPref;
        token = sharedPref.getString("jwt", "");

        request = new FriendprofileRequestImpl(this, sharedPref, token);
        this.activity = activity;
    }

    @Override
    public void updateInfo(){
        request.updateProfile(activity.getFriendId());
        request.updateEvents(activity.getFriendId());
    }

    @Override
    public void showFriendInfo(Profile info){
        activity.setNameView(info.getFirstname() + " " + info.getLastname());
        activity.setAgeView(info.getAge() + "");
        activity.setDescriptionView(info.getDescription());
        activity.setInterestsView(info.getInterests());
        if(info.getPicture() != null) activity.setPictureView(info.getPicture());
    }

    @Override
    public ArrayList<Event> getEvents(){
        return request.getEvents();
    }


    @Override
    public void addFriend() {
        JSONObject jsonObject = new JSONObject();
        Profile friend = request.getProfile();
        try{
            jsonObject.put("friendID", "" + friend.getId());
            jsonObject.put("friendName", friend.getFirstname());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("friend request json", jsonObject.toString());
        request.makeApiRequestPut(jsonObject.toString(), "friendrequest", "PUT", "friendrequest");
    }

    @Override
    public void showApiResponse(String command, String... params) {
        if (params[0] == null) {
            return;
        }
        switch (command) {
            case "showInfo":
                request.setProfile(params[0], activity.getFriendId());
                showFriendInfo(request.getProfile());
                break;
            case "geteventforuser":
                request.setEvents(params[0]);
                activity.showEvents(request.getEvents(), request.getCreator(), request.getPlaceName(), request.getEventImages());
                break;
            case "isfriend":
                Log.d("isFriend", request.getProfile().getFirstname() + " is " + request.isFriend(params[0]) + "!");
                if(request.isFriend(params[0])) activity.alreadyFriend();
                break;
            case "friendrequest":
                activity.showToastToUser(params[0]);
                break;
        }
    }
}
