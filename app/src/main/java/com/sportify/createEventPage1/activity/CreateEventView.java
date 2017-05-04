package com.sportify.createEvent.activity;

/**
 * Created by Maja on 2017-04-18.
 */

public interface CreateEventView {

    String getEventName();

    String getEventPlace();

    String getEventPrice();

    String getEventDate();

    String getEventStartTime();

    String getEventEndTime();

    String getEventType();

    String getEventMaxAttendance();

    String getEventDescription();

    boolean getPrivateEvent();

    void showEventNameEmptyError();

    void showEventPriceWrongFormatError();

    void showEventDateEmptyError();

    //TODO: Ska vi ha denna check för tiderna också? Beror ju på hur det kommer läggas in i fortsättningen / om det ska formateras automatiskt
    void showEventDateFormatError();

    void showEventStartTimeEmptyError();

    void showEventEndTimeEmptyError();

    void showEventTypeEmptyError();

    void showEventPlaceEmptyError();

    void clearMessageTv();

    void showApiRequestMessage(String apiResponse);

}
