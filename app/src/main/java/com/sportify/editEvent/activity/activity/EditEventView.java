package com.sportify.editEvent.activity.activity;

import com.sportify.storage.Place;

import java.util.ArrayList;

public interface EditEventView {

    void goToEventArea();

    /*
     * GETTERS
     */
    //TODO: Sätt bild
    int getEventID();
    String getEventName();
    String getEventPlace();
    String getEventPlaceId();
    String getEventPrice();
    String getEventStartDate();
    String getEventEndDate();
    String getEventStartTime();
    String getEventEndTime();
    String getEventType();
    String getEventMaxAttendance();
    String getEventDescription();
    boolean getPrivateEvent();

    /*
     * SETTERS
     */
    void setEventName(String eventName);
    void setEventPlace(String eventPlace);
    void setStartDate(String startDate);
    void setEndDate(String endDate);
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
    void showEventStartDateEmptyError(int resId);
    void showEventEndDateEmptyError(int resId);
    void showEventPlaceEmptyError(int resId);

    /*
     * WRONG FORMAT ERROR
     */
    void showEventPriceWrongFormatError(int resId);
    void showEventStartDateFormatError(int resId);
    void showEventEndDateFormatError(int resId);


    void clearAllErrors();
    void showToastToUser(String apiResponse);
    void updatePlaceAdapter(ArrayList<Place> arr);
    boolean getUserWroteSearch();
}
