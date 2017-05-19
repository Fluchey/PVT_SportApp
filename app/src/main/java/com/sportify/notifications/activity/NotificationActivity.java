package com.sportify.notifications.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.sportify.arrayAdapters.MyArrayAdapterNotifications;
import com.sportify.createEvent.createEventPageOne.activity.CreateEventPageOnePageOneActivity;
import com.sportify.eventArea.activity.EventAreaActivity;
import com.sportify.maps.activity.MapsActivity;
import com.sportify.notifications.EventNotification;
import com.sportify.notifications.FriendRequestNotification;
import com.sportify.notifications.Notification;
import com.sportify.notifications.presenter.NotificationPresenter;
import com.sportify.notifications.presenter.NotificationPresenterImpl;
import com.sportify.showFriends.activity.ShowFriendsActivity;
import com.sportify.userArea.activity.UserAreaActivity;

import java.util.ArrayList;

import sportapp.pvt_sportapp.R;

public class NotificationActivity extends AppCompatActivity implements NotificationView {

    private NotificationPresenter notificationPresenter;
    private MyArrayAdapterNotifications arrayAdapter;
    private ListView notificationList;
    private SharedPreferences sharedPref;
    private int eventId;

    private ArrayList<Notification> notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        notificationPresenter = new NotificationPresenterImpl(this, sharedPref);

        notificationList = (ListView) findViewById(R.id.lvNotifications);

        notificationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(notificationList.getItemAtPosition(position).getClass() == EventNotification.class) {

                    EventNotification eventNotification = (EventNotification) notificationList.getItemAtPosition(position);
                    eventId = eventNotification.getEventID();

                    toEventAreaActivity();
                }
                if(notificationList.getItemAtPosition(position).getClass() == FriendRequestNotification.class){

                }

            }
        });
    }

    public void ToUserAreaFromNoteActivity(View v){
        Intent goToUserAreaViewIntent = new Intent(NotificationActivity.this, UserAreaActivity.class);
        NotificationActivity.this.startActivity(goToUserAreaViewIntent);
    }

    public void toEventAreaActivity(){
        Intent goToEventAreaIntent = new Intent(NotificationActivity.this, EventAreaActivity.class);

        goToEventAreaIntent.putExtra("eventId", eventId);
        NotificationActivity.this.startActivity(goToEventAreaIntent);
    }

    /**
     * Usage for the buttons
     */
    public void createEventfromNoteActivity(View v) {
        Toast.makeText(this, "Clicked Create Event", Toast.LENGTH_LONG).show();
        Intent createEventIntent = new Intent(NotificationActivity.this, CreateEventPageOnePageOneActivity.class);
        NotificationActivity.this.startActivity(createEventIntent);
    }

    public void toMapFromNoteActivity(View view) {
        Intent startMapActivityIntent = new Intent(NotificationActivity.this, MapsActivity.class);
        NotificationActivity.this.startActivity(startMapActivityIntent);
    }

    public void toFriendsListfromNoteActivity(View v){
        Intent goToFriendListIntent = new Intent(NotificationActivity.this, ShowFriendsActivity.class);
        NotificationActivity.this.startActivity(goToFriendListIntent);
    }


    @Override
    public void showNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
        arrayAdapter = new MyArrayAdapterNotifications(this, R.layout.notification_list_item, notifications);
        notificationList.setAdapter(arrayAdapter);
    }
}