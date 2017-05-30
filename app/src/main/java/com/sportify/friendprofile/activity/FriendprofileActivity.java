package com.sportify.friendprofile.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sportify.createEvent.createEventPageOne.activity.CreateEventPageOneActivity;
import com.sportify.eventArea.activity.EventAreaActivity;
import com.sportify.friendprofile.presenter.FriendprofilePresenter;
import com.sportify.friendprofile.presenter.FriendprofilePresenterImpl;
import com.sportify.maps.activity.MapsActivity;
import com.sportify.notifications.activity.NotificationActivity;
import com.sportify.showFriends.activity.ShowFriendsActivity;
import com.sportify.storage.Event;
import com.sportify.userArea.CustomList;
import com.sportify.userArea.activity.UserAreaActivity;
import com.sportify.util.Profile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sportapp.pvt_sportapp.R;

public class FriendprofileActivity extends AppCompatActivity implements FriendprofileView {

    private FriendprofilePresenter presenter;

    private SharedPreferences sharedPref;
    private int friendId;

    private TextView nameView;
    private TextView ageView;
    private TextView interestsView;
    private TextView descriptionView;
    private ImageView pictureView;
    private ImageButton addButton;
    private ListView eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendprofile);

        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        presenter = new FriendprofilePresenterImpl(this, sharedPref);

        nameView = (TextView) findViewById(R.id.tvFriendName);
        ageView = (TextView) findViewById(R.id.tvFriendAge);
        interestsView =(TextView) findViewById(R.id.tvFriendintressen);
        descriptionView = (TextView) findViewById(R.id.tvFriendDescription);
        pictureView = (ImageView) findViewById(R.id.ivProfilePicture);
        addButton = (ImageButton) findViewById(R.id.ibAddFriend);
        eventList = (ListView) findViewById(R.id.lvFriendEvents);

        Bundle b = getIntent().getExtras();
        if(b != null){
            friendId = b.getInt("friendId");
        } else friendId = -1;

        presenter.updateInfo();
    }

    @Override
    public int getFriendId(){ return friendId; }

    @Override
    public void setNameView(String name) { nameView.setText(name);}

    @Override
    public void setAgeView(String age) { ageView.setText(Profile.getAge(age) + ""); }

    @Override
    public void setInterestsView(List<String> interests) {
        String formattedInterests = "";
        for(String interest : interests){
            formattedInterests += interest + ", ";
        }
        if (formattedInterests.length() > 0){
            formattedInterests.substring(0, formattedInterests.length() - 3);
        }
        interestsView.setText(formattedInterests);
    }

    @Override
    public void setDescriptionView(String description) { descriptionView.setText(description);}

    @Override
    public void setPictureView(String img) { pictureView.setImageBitmap(Profile.decodeStringToBitmap(img)); }

    @Override
    public void alreadyFriend(){
        addButton.setImageBitmap(null);
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
        CustomList adapter = new CustomList(FriendprofileActivity.this, nameArr, eventArr, creator, placeName, eventImages);
        eventList = (ListView) findViewById(R.id.lvFriendEvents);
        eventList.setAdapter(adapter);
        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent goToEventAreaIntent = new Intent(FriendprofileActivity.this, EventAreaActivity.class);
                goToEventAreaIntent.putExtra("eventId", presenter.getEvents().get(position).getId());
                FriendprofileActivity.this.startActivity(goToEventAreaIntent);
            }
        });
    }

    public void ToNoteActivityFromF(View v) {
        Intent goToNotificationsViewIntent = new Intent(FriendprofileActivity.this, NotificationActivity.class);
        FriendprofileActivity.this.startActivity(goToNotificationsViewIntent);
    }

    public void createEventButtonClickFromF(View v) {
        Intent createEventIntent = new Intent(FriendprofileActivity.this, CreateEventPageOneActivity.class);
        FriendprofileActivity.this.startActivity(createEventIntent);
    }

    public void startMapActivityFromF(View view) {
        Intent startMapActivityIntent = new Intent(FriendprofileActivity.this, MapsActivity.class);
        FriendprofileActivity.this.startActivity(startMapActivityIntent);
    }

    public void goToUserAreaActivityFromF(View v) {
        Intent goToUserAreaViewIntent = new Intent(FriendprofileActivity.this, UserAreaActivity.class);
        FriendprofileActivity.this.startActivity(goToUserAreaViewIntent);
    }

    public void goToFriendListButtonClickFromF(View v) {
        Intent goToFriendListIntent = new Intent(FriendprofileActivity.this, ShowFriendsActivity.class);
        FriendprofileActivity.this.startActivity(goToFriendListIntent);
    }
}
