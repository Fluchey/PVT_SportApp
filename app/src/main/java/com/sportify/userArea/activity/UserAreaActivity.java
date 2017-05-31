package com.sportify.userArea.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sportify.calendar.activity.CalendarActivity;
import com.sportify.createEvent.createEventPageOne.activity.CreateEventPageOneActivity;
import com.sportify.eventArea.activity.EventAreaActivity;
import com.sportify.maps.activity.MapsActivity;
import com.sportify.notifications.activity.NotificationActivity;
import com.sportify.placearea.activity.PlaceAreaActivity;
import com.sportify.profile.activity.EditProfileActivity;
import com.sportify.settings.activity.SettingsActivity;
import com.sportify.showFriends.activity.ShowFriendsActivity;
import com.sportify.storage.Event;
import com.sportify.userArea.CustomList;
import com.sportify.userArea.presenter.UserAreaPresenter;
import com.sportify.userArea.presenter.UserAreaPresenterImpl;
import com.sportify.util.Profile;

import java.util.ArrayList;
import java.util.HashMap;

import sportapp.pvt_sportapp.R;

//import com.sportify.showFriends.activity.ShowFriendsActivity;

public class UserAreaActivity extends AppCompatActivity implements UserAreaView {
    private UserAreaPresenter userAreaPresenter;
    private SharedPreferences sharedPref;
    private TextView userName, userAge, userBio, userInterests;
    private ImageView userProfilePicture;

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);
        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userAreaPresenter = new UserAreaPresenterImpl(this, sharedPref);
        userName = (TextView) findViewById(R.id.UserInfoNameText);
        userName.setText(sharedPref.getString("firstName", "") + " " + sharedPref.getString("lastName", ""));
        userAge = (TextView) findViewById(R.id.UserInfoAgeText);
        String dateOfBirth = sharedPref.getString("dateOfBirth", "");
        Log.d("UserAreaActivity", "dob: " + dateOfBirth);
        int age = -1;
        if (!dateOfBirth.isEmpty()) {
            age = Profile.getAge(dateOfBirth);
            userAge.setText("" + age + " år");
        } else {
            userAge.setText("");
        }
        userBio = (TextView) findViewById(R.id.profileText);
        userBio.setText(sharedPref.getString("userBio", ""));
        userInterests = (TextView) findViewById(R.id.UserInfoInterestText);
        String interestsString = (sharedPref.getString("interests", ""));
        interestsString = interestsString.replaceAll("\"", "");
        interestsString = interestsString.replace("[", "#");
        interestsString = interestsString.replace("]", "");
        interestsString = interestsString.replaceAll(",", " #");
        userInterests.setText(interestsString);
        String imageBase64 = sharedPref.getString("imageBase64", "");
        Log.d("UserAreaActivity", "imageBase64: " + imageBase64);
        userProfilePicture = (ImageView) findViewById(R.id.profilePicView);
        if (!imageBase64.isEmpty()) {
            Bitmap bitmap = Profile.decodeStringToBitmap(imageBase64);
            userProfilePicture.setImageBitmap(bitmap);
        }
        Log.d("UserAreaActivity", "imageBase64: " + sharedPref.getString("imageBase64", ""));

        /**
         * MINA EVENT LISTVIEW
         */
//        CustomList adapter = new
//                CustomList(UserAreaActivity.this, web, imageId);
//        list = (ListView) findViewById(R.id.userAreaEventList);
//        list.setAdapter(adapter);
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                Toast.makeText(UserAreaActivity.this, "You Clicked at " + web[+position], Toast.LENGTH_SHORT).show();
//
//            }
//        });
    }


    public void ToNoteFromUserAreaActivity(View v) {
        Intent goToNotificationsViewIntent = new Intent(UserAreaActivity.this, NotificationActivity.class);
        UserAreaActivity.this.startActivity(goToNotificationsViewIntent);
    }

    public void createEventButtonClick(View v) {
        Intent createEventIntent = new Intent(UserAreaActivity.this, CreateEventPageOneActivity.class);
        UserAreaActivity.this.startActivity(createEventIntent);
    }

    public void startMapActivity(View view) {
        Intent startMapActivityIntent = new Intent(UserAreaActivity.this, MapsActivity.class);
        UserAreaActivity.this.startActivity(startMapActivityIntent);
    }

    public void goToSettingsActivity(View v) {
        Intent goToSettingsViewIntent = new Intent(UserAreaActivity.this, SettingsActivity.class);
        UserAreaActivity.this.startActivity(goToSettingsViewIntent);
    }

    public void goToFriendListButtonClick(View v) {
        Intent goToFriendListIntent = new Intent(UserAreaActivity.this, ShowFriendsActivity.class);
        UserAreaActivity.this.startActivity(goToFriendListIntent);
    }

    public void goToCalendarActivity(View v) {
        Intent goToCalendarIntent = new Intent(UserAreaActivity.this, CalendarActivity.class);
        UserAreaActivity.this.startActivity(goToCalendarIntent);
    }

    public void goToEditProfileFromUser(View v) {
        Intent goToEditProfileIntent = new Intent(UserAreaActivity.this, EditProfileActivity.class);
        UserAreaActivity.this.startActivity(goToEditProfileIntent);
    }

    public void goToEventAreaActivity(View v) {
        Intent goToEventAreaIntent = new Intent(UserAreaActivity.this, EventAreaActivity.class);
        goToEventAreaIntent.putExtra("eventId", 322);
        UserAreaActivity.this.startActivity(goToEventAreaIntent);
    }

    public void goToReadReviewActivity(View v) {
        Intent placeAreaIntent = new Intent(UserAreaActivity.this, PlaceAreaActivity.class);
        placeAreaIntent.putExtra("placeId", "2220");
        UserAreaActivity.this.startActivity(placeAreaIntent);
    }

    @Override
    public void showEvents(ArrayList<Event> events, HashMap<Integer, String> creator, HashMap<Integer, String> placeName, ArrayList<String> eventImage) {
        ArrayList<String> eventNames = new ArrayList<>();
        for(Event e: events){
            eventNames.add(e.getEventName());
        }
        String[] nameArr = eventNames.toArray(new String[0]);
        Event[] eventArr = events.toArray(new Event[0]);
        ArrayList<String> eventImages = eventImage;
        CustomList adapter = new CustomList(UserAreaActivity.this, nameArr, eventArr, creator, placeName, eventImages);
        list = (ListView) findViewById(R.id.userAreaEventList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent goToEventAreaIntent = new Intent(UserAreaActivity.this, EventAreaActivity.class);
                goToEventAreaIntent.putExtra("eventId", userAreaPresenter.getEventsFromStorage().get(position).getId());
                UserAreaActivity.this.startActivity(goToEventAreaIntent);
            }
        });
    }
}
