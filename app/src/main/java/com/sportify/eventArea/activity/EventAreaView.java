package com.sportify.eventArea.activity;

import android.view.View;

import com.sportify.storage.Place;

/**
 * Created by antonfluch on 2017-05-15.
 */

public interface EventAreaView {
    void setEventName(String eventName);
    String getEventName();
    void setPlaceName(Place place);
    Place getPlaceName();
    void setHostName(String firstName, String lastName);
    String getHostName();
    void setStartTime(String startTime);
    String getStartTime();
    void setEndTime(String endTime);
    void setEventDate(String date);
    String getEventDate();
    void setEventType(String eventType);
    String getEventType();
    void setPrice(int price);
    int getPrice();
    void setMaxAttendance(int maxAttendance);
    int getMaxAttendance();
    void setPrivateEvent(boolean privateEvent);
    void setDescription(String description);
    String getDescription();
    void sendResponsEventInvite(View v);
}
