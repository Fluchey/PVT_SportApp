package com.sportify.createEvent.createEventPageThree.presenter;

import android.content.SharedPreferences;
import android.util.Log;

import com.sportify.showFriends.Profile;
import com.sportify.createEvent.createEventPageThree.activity.CreateEventInviteFriendsView;
import com.sportify.createEvent.createEventPageThree.request.CreateEventInviteFriendsRequest;
import com.sportify.createEvent.createEventPageThree.request.CreateEventInviteFriendsRequestImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import sportapp.pvt_sportapp.R;

/**
 * Created by Maja on 2017-04-27.
 */

public class CreateEventInviteFriendsPresenterImpl implements CreateEventInviteFriendsPresenter, CreateEventInviteFriendsRequest.OnShowFriendsFinishedListener{

    private CreateEventInviteFriendsView createEventInviteFriendsView;
    private CreateEventInviteFriendsRequest createEventInviteFriendsRequest;
    private SharedPreferences sharedPref;
    private String token = "";

    public CreateEventInviteFriendsPresenterImpl(CreateEventInviteFriendsView createEventInviteFriendsView, SharedPreferences sharedPref){
        this.createEventInviteFriendsView = createEventInviteFriendsView;
        this.sharedPref = sharedPref;
        this.token = sharedPref.getString("jwt", "");
        this.createEventInviteFriendsRequest = new CreateEventInviteFriendsRequestImpl(this, token);
    }

    @Override
    public void showFriends() {
        //TODO: Skickar nu bara tomt Json för att jag inte får GET att funka, gör fortf med POST i Heroku.
        createEventInviteFriendsRequest.makeApiRequest("{}");
    }

    public void getFriends(String jsonMessage) {

        System.out.println("Jsonmessage " + jsonMessage);
        JSONObject json = null;
        JSONArray array = null;

        if(jsonMessage == null){
            System.out.println("Fel format på Json");
            return;
        }
        try {
            json = new JSONObject(jsonMessage);
            array = json.getJSONArray("friendList");
            Log.d("JsonArr: ", array.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(json == null || array == null){
            return;
        }

        try{
            ArrayList<Profile> friends = new ArrayList<>();

            for(int i=0; i < array.length(); i++){
                JSONObject jsonObject = array.getJSONObject(i);
                //TODO: Byt ut facebook icon till profilbild
                String firstname = jsonObject.getString("firstname");

                Profile friend = new Profile(firstname, firstname, R.drawable.com_facebook_button_icon_blue);

                friends.add(friend);
            }

            createEventInviteFriendsView.showFriends(friends);

        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void showApiResponse(String... params) {
//        createEventInviteFriendsView.showApiRequestMessage(params[0]);
        getFriends(params[0]);
    }
}
