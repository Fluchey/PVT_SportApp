package com.sportify.event.activity;

/**
 * Created by Maja on 2017-04-18.
 */

public interface CreateEventView {

    String getEventName();

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

    void showEventStartTimeEmptyError();

    void showEventEndTimeEmptyError();

    void showEventTypeEmptyError();

    void clearMessageTv();

    void showApiRequestMessage(String apiResponse);

}
