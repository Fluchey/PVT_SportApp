package com.sportify.eventArea.activity;

import android.view.View;

import com.sportify.storage.Place;

/**
 * Created by antonfluch on 2017-05-15.
 */

public interface EventAreaView {
    void setEventName(String eventName);
    void setPlaceName(Place place);
    void setHostName(String firstName, String lastName);
    void setStartTime(String startTime);
    void setEndTime(String endTime);
    void setEventDate(String date);
    void setEventType(String eventType);
    void setPrice(int price);
    void setMaxAttendance(int maxAttendance);
    void setPrivateEvent(boolean privateEvent);
    void setDescription(String description);

    void sendResponsEventInvite(View v);
}
