package com.sportify.eventArea.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.widget.ShareDialog;
import com.sportify.arrayAdapters.MyArrayAdapterParticipants;
import com.sportify.editEvent.activity.activity.EditEventActivity;
import com.sportify.eventArea.presenter.EventAreaPresenter;
import com.sportify.eventArea.presenter.EventAreaPresenterImpl;
import com.sportify.placearea.activity.PlaceAreaActivity;
import com.sportify.storage.Participant;
import com.sportify.storage.Place;
import com.sportify.userArea.activity.UserAreaActivity;
import com.sportify.util.Profile;

import java.util.ArrayList;

import sportapp.pvt_sportapp.R;

public class EventAreaActivity extends AppCompatActivity implements EventAreaView{
    private EventAreaPresenter presenter;
    private int eventId;
    private SharedPreferences sharedPref;

    private MyArrayAdapterParticipants arrayAdapterParticipants;

    private TextView hostNameTv;
    private TextView eventNameTv;
    private TextView placeNameTv;
    private TextView descriptionTv;
    private TextView eventDateTv;
    private TextView startTimeTv;
    private TextView endTimeTv;
    private TextView priceTv;
    private TextView maxAttTv;
    private TextView dateTv;
    private ImageView eventImage;
    private RadioButton interestedRb;
    private RadioButton comingRb;
    private RadioButton notComingRb;
    private ImageView privateEventImage;
    private ListView participantList;


    /*
     * Event information
     */
    private String imageBase64;
    private Place place;
    private String eventDate;
    private String startTime;
    private String endTime;
    private String eventType;
    private int price;
    private boolean privateEvent;
    private String description;
    private int maxAttendance;

    private String eventName;
//    private String placeName;

    private ShareOpenGraphObject object;
    private ShareOpenGraphAction action;
    private ShareOpenGraphContent content;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_area);

        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        presenter = new EventAreaPresenterImpl(this, sharedPref);

        arrayAdapterParticipants = new MyArrayAdapterParticipants(this, R.layout.participant_list_item, null);

        Bundle bundle = getIntent().getExtras();
        eventId = (bundle.getInt("eventId"));
        presenter.getEventFromDb(eventId);

        hostNameTv = (TextView) findViewById(R.id.tvEventAreaHostName);
        eventNameTv = (TextView) findViewById(R.id.eventAreaHeader);
        placeNameTv = (TextView) findViewById(R.id.tvEventAreaPlaceText);
        eventDateTv = (TextView) findViewById(R.id.tvEventAreaDate);
        startTimeTv = (TextView) findViewById(R.id.tvEventAreaStartTime);
        endTimeTv = (TextView) findViewById(R.id.tvEventAreaEndTime);
        descriptionTv = (TextView) findViewById(R.id.tvEventAreaDescription);
        priceTv = (TextView) findViewById(R.id.tvEventAreaPrice);
        maxAttTv = (TextView) findViewById(R.id.tvMaxAtt);
        dateTv = (TextView) findViewById(R.id.tvEventAreaDate);
        eventImage = (ImageView) findViewById(R.id.eventImage);
        interestedRb = (RadioButton) findViewById(R.id.interestedRadioButton);
        comingRb = (RadioButton) findViewById(R.id.comingRadioButton);
        notComingRb = (RadioButton) findViewById(R.id.notComingRadioButton);
        privateEventImage = (ImageView) findViewById(R.id.viewLockedIcon);

        participantList = (ListView) findViewById(R.id.lvParticipants);
    }


    public void toUserAreaFromEvent(View v){
        Intent goToUserAreaViewIntent = new Intent(EventAreaActivity.this, UserAreaActivity.class);
        EventAreaActivity.this.startActivity(goToUserAreaViewIntent);
    }

    public void goToEditEventActivity(View v){
        Intent goToEditEventViewIntent = new Intent(EventAreaActivity.this, EditEventActivity.class);

        goToEditEventViewIntent.putExtra("eventId", eventId);
        goToEditEventViewIntent.putExtra("eventImage", imageBase64);
        goToEditEventViewIntent.putExtra("eventName", eventName);
        goToEditEventViewIntent.putExtra("place", place);
        goToEditEventViewIntent.putExtra("eventDate", eventDate);
        goToEditEventViewIntent.putExtra("startTime", startTime);
        goToEditEventViewIntent.putExtra("endTime", endTime);
        goToEditEventViewIntent.putExtra("eventType", eventType);
        goToEditEventViewIntent.putExtra("maxAttendance", maxAttendance);
        goToEditEventViewIntent.putExtra("price", price);
        goToEditEventViewIntent.putExtra("privateEvent", privateEvent);
        goToEditEventViewIntent.putExtra("description", description);


        EventAreaActivity.this.startActivity(goToEditEventViewIntent);
    }

    public void goToPlaceArea(View v){
        Intent goToPlaceAreaIntent = new Intent(EventAreaActivity.this, PlaceAreaActivity.class);

        goToPlaceAreaIntent.putExtra("placeId", place.getId());
        EventAreaActivity.this.startActivity(goToPlaceAreaIntent);
    }

    @Override
    public void showParticipants(ArrayList<Participant> participants) {
        System.out.println(participants.toString());
        arrayAdapterParticipants = new MyArrayAdapterParticipants(this, R.layout.participant_list_item, participants);
        participantList.setAdapter(arrayAdapterParticipants);

    }

    @Override
    public void setEventName(String eventName) {
        this.eventNameTv.setText(eventName);
        this.eventName = eventName;
    }

    @Override
    public String getEventName() {
        return eventNameTv.getText().toString();
    }

    @Override
    public void setPlaceName(Place place) {
        this.placeNameTv.setText("Plats: " + place.getName());
        this.place = place;
    }

    @Override
    public Place getPlaceName() {
        return place;
    }

    @Override
    public void setHostName(String firstName, String lastName) {
        this.hostNameTv.setText("Värd: " + firstName + " " + lastName);
    }

    @Override
    public String getHostName() {
        return hostNameTv.getText().toString();
    }

    @Override
    public void setStartTime(String startTime) {
        this.startTimeTv.setText("Starttid: " + startTime);
        this.startTime = startTime;
    }

    @Override
    public String getStartTime() {
        return startTime;
    }

    @Override
    public void setEndTime(String endTime) {
        this.endTimeTv.setText("Sluttid: " + endTime);
        this.endTime = endTime;
    }

    @Override
    public void setEventDate(String date) {
        dateTv.setText("Datum: " + date);
        this.eventDate = date;
    }

    @Override
    public String getEventDate() {
        return eventDate;
    }

    @Override
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public String getEventType() {
        return eventType;
    }

    @Override
    public void setPrice(int price) {
        this.priceTv.setText(getText(R.string.event_area_price) + String.valueOf(price));
        this.price = price;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public void setMaxAttendance(int maxAttendance) {
        this.maxAttTv.setText("Max deltagare: " + String.valueOf(maxAttendance));
        this.maxAttendance = maxAttendance;
    }

    @Override
    public int getMaxAttendance() {
        return maxAttendance;
    }

    @Override
    public void setPrivateEvent(boolean privateEvent) {
        this.privateEvent = privateEvent;
        if(privateEvent){
            privateEventImage.setImageResource(R.drawable.lockedicon);
        }else{
            privateEventImage.setImageResource(R.drawable.unlockedicon);
        }
    }

    @Override
    public void setDescription(String description) {
        this.descriptionTv.setText(description);
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setEventImage(String imageBase64) {
        this.imageBase64 = imageBase64;
        if(!imageBase64.isEmpty()){
            Bitmap image = Profile.decodeStringToBitmap(imageBase64);
            eventImage.setImageBitmap(image);
        }else{
            //TODO: Lägg in någon annan bild som default?
            eventImage.setImageResource(R.drawable.userareaflowswim);
        }
    }

    @Override
    public Bitmap getEventImage() {
        return null;
    }

    @Override
    public void sendResponsEventInvite(View v) {
        String response = v.getTag().toString();
        presenter.sendResponsEventInvite(response, eventId);
    }

    @Override
    public void setAttendance(String attendance) {
        if(attendance.equalsIgnoreCase("attending")) {
            comingRb.setChecked(true);
        }else if(attendance.equalsIgnoreCase("not_attending")){
            notComingRb.setChecked(true);
        }else if(attendance.equalsIgnoreCase("interested")){
            interestedRb.setChecked(true);
        }
    }


    public void shareToFacebook(View v){
//        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        if (accessToken==null || accessToken.isExpired()){

            // Format the date into for example: 30/05
            StringBuilder date = new StringBuilder(eventDate.subSequence(8,10) + "/" + eventDate.subSequence(5,7));
            // Format the date into for example: 30/5
            if (date.charAt(3)=='0') date.deleteCharAt(3);

            // Create an object
            this.object = new ShareOpenGraphObject.Builder()
                    .putString("og:type", "object")
                    .putString("og:title", eventName + " " + date)
                    .putString("og:description", description + "\n"
                            + startTime.subSequence(0,5) + " @" + place.getName())
                    .putString("og:image", "http://drive.google.com/uc?export=view&id=0B9pMqKohmtD4bm9XbXIxTGJpQkk")
                    .putString("og:url", "http://maps.google.com/?q=Stockholm+" + place.getName().replaceAll("\\s+","+"))
                    .build();

            // Create an action
            this.action = new ShareOpenGraphAction.Builder()
                    .setActionType("og.likes")
                    .putObject("object", object)
                    .build();

            // Create the content
            this.content = new ShareOpenGraphContent.Builder()
                    .setPreviewPropertyName("object")
                    .setAction(action)
                    .build();

            // Launch Facebook Hodoo
            ShareDialog.show(this, content);
    }
}
