package com.sportify.editEvent.activity.activity;

import java.util.ArrayList;

public interface EditEventView {

    void goToEventArea();

    String getEventName();

    String getEventPlace();

    int getEventPlaceId();

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

    void showEventStartTimeEmptyError(int resId);

    void showEventEndTimeEmptyError(int resId);

    void showEventTypeEmptyError(int resId);

    void clearAllErrors();

    void showEventPriceWrongFormatError(int resId);

    void showEventStartDateFormatError(int resId);

    void showEventStartDateEmptyError(int resId);

    void showEventEndDateFormatError(int resId);

    void showEventEndDateEmptyError(int resId);

    void showEventPlaceEmptyError(int resId);

    void showToastToUser(String apiResponse);

    void updatePlaceAdapter(ArrayList<String> arr);

    boolean getUserWroteSearch();

    int getEventID();

}
