package com.sportify.event.activity;

/**
 * Created by Maja on 2017-04-18.
 */

public interface CreateEventView {

    String getEventName();

    String getEventPrice();

    String getEventDescription();

    void showEventNameEmptyError();

    void showEventPriceWrongFormatError();

    void clearMessageTv();

    void showApiRequestMessage(String apiResponse);

}
