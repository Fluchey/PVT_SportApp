package com.sportify.eventArea.activity;

import android.view.View;

/**
 * Created by antonfluch on 2017-05-15.
 */

public interface EventAreaView {
    void setEventName(String eventName);
    void setPlaceName(String placeName);
    void setHostName(String firstName, String lastName);
    void setStartTime(String startTime);
    void setEndTime(String endTime);
    void setStartDate(String startDate);
    void setEndDate(String endDate);
    void setPrice(int price);
    void setDescription(String description);

    void sendResponsEventInvite(View v);
}
