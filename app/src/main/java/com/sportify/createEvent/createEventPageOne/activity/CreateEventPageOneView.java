package com.sportify.createEvent.createEventPageOne.activity;

import android.graphics.Bitmap;
import android.view.View;

import com.sportify.storage.Place;

import java.util.ArrayList;

/**
 * Created by Maja on 2017-04-18.
 */

public interface CreateEventPageOneView {

    void goToInviteFriends();

    /**
     * GETTERS
     */
    Bitmap getEventImage();
    String getEventName();
    String getEventPlace();
    String getEventPlaceId();
    String getEventPrice();
    String getEventDate();
    String getEventStartTime();
    String getEventEndTime();
    String getEventType();
    String getEventMaxAttendance();
    String getEventDescription();
    boolean getPrivateEvent();

    /**
     * ERROR MESSAGES
     */
    void showEventNameEmptyError(int resId);
    void showEventPriceWrongFormatError(int resId);
    void showEventDateEmptyError(int resId);
    //TODO: Ska vi ha denna check för tiderna också? Beror ju på hur det kommer läggas in i fortsättningen / om det ska formateras automatiskt
    void showEventDateFormatError(int resId);
    void showEventStartTimeEmptyError(int resId);
    void showEventEndTimeEmptyError(int resId);
    void showEventTypeEmptyError(int resId);
    void showEventPlaceEmptyError(int resId);

    void clearAllErrors();

    void showToastToUser(String apiResponse);

    void updatePlaceAdapter(ArrayList<Place> arr);

    boolean getUserWroteSearch();

    void setEventID(int eventID);

    void eventPictureButtonClick(View v);

    Boolean userSelectedImage();
}
