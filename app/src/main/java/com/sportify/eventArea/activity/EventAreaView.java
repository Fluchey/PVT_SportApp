package com.sportify.eventArea.activity;

/**
 * Created by antonfluch on 2017-05-15.
 */

public interface EventAreaView {
    void setPlaceName(String placeName);
    void setHostName(String hostName);
    void setStartTime(String startTime);
    void setEndTime(String endTime);
    void setStartDate(String startDate);
    void setEndDate(String endDate);
    void setPrice(int price);
    void setDescription(String description);
}
