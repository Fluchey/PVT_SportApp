package com.sportify.editEvent.activity.activity;

import android.graphics.Bitmap;
import android.view.View;

import com.sportify.storage.Place;

import java.util.ArrayList;

public interface EditEventView {

    void editEvent(View v);
    void goToEventArea();
    void goToUserArea();
    void deleteEvent(View v);
    void deleteEventWithoutView(int eventID);

    /*
     * GETTERS
     */
    Bitmap getEventImage();
    String getImageBase64();
    int getEventID();
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

    /*
     * SETTERS
     */
    void setEventImage(String imageBase64);
    void setEventName(String eventName);
    void setEventPlace(String eventPlace);
    void setDate(String date);
    void setStartTime(String startTime);
    void setEndTime(String endTime);
    void setEventType(String eventType);
    void setMaxAttendance(int maxAttendance);
    void setPrice(int price);
    void setPrivate(boolean privateEvent);

    /*
     * EMPTY ERROR
     */
    void showEventNameEmptyError(int resId);
    void showEventStartTimeEmptyError(int resId);
    void showEventEndTimeEmptyError(int resId);
    void showEventTypeEmptyError(int resId);
    void showEventDateEmptyError(int resId);
    void showEventPlaceEmptyError(int resId);

    /*
     * WRONG FORMAT ERROR
     */
    void showEventPriceWrongFormatError(int resId);
    void showEventDateFormatError(int resId);

    void clearAllErrors();
    void showToastToUser(String apiResponse);
    void updatePlaceAdapter(ArrayList<Place> arr);
    boolean getUserWroteSearch();

    /**
     * IMAGES
     */
    void eventPictureButtonClick(View v);
    Boolean userSelectedImage();
}
