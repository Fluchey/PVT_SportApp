package com.sportify.createEvent.createEventPageOne.activity;

import java.util.ArrayList;

/**
 * Created by Maja on 2017-04-18.
 */

public interface CreateEventPageOneView {

    String getEventName();

    String getEventPlace();

    String getEventPrice();

    String getEventStartDate();

    String getEventEndDate();

    String getEventStartTime();

    String getEventEndTime();

    String getEventType();

    String getEventMaxAttendance();

    String getEventDescription();

    boolean getPrivateEvent();

    void showEventNameEmptyError(int resId);

    void showEventPriceWrongFormatError(int resId);

    void showEventStartDateEmptyError(int resId);

    void showEventEndDateEmptyError(int resId);

    //TODO: Ska vi ha denna check för tiderna också? Beror ju på hur det kommer läggas in i fortsättningen / om det ska formateras automatiskt
    void showEventStartDateFormatError(int resId);

    void showEventEndDateFormatError(int resId);

    void showEventStartTimeEmptyError(int resId);

    void showEventEndTimeEmptyError(int resId);

    void showEventTypeEmptyError(int resId);

    void showEventPlaceEmptyError(int resId);

    void clearAllErrors();

    void showToastToUser(String apiResponse);

    void updatePlaceAdapter(ArrayList<String> arr);

    boolean getUserWroteSearch();

    void setEventID(int eventID);

}
