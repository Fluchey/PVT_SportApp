package com.sportify.storage;

/**
 * Created by fluchey on 2017-05-09.
 */

public class Event {
    private int id;
    private String eventName;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private String eventDescription;
    private String placeId;
    private int price;
    private String eventType;
    private int maxAttendance;
    private boolean privateEvent;

    public Event(int id, String eventName, String startDate, String endDate, String startTime, String endTime, String eventDescription, String placeName, int price, String eventType, int maxAttendance, boolean privateEvent) {
        this.id = id;
        this.eventName = eventName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventDescription = eventDescription;
        this.placeId = placeName;
        this.price = price;
        this.eventType = eventType;
        this.maxAttendance = maxAttendance;
        this.privateEvent = privateEvent;
    }

    public Event() {

    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getPlaceName() {
        return placeId;
    }

    public void setPlaceName(String placeName) {
        this.placeId = placeName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getMaxAttendance() {
        return maxAttendance;
    }

    public void setMaxAttendance(int maxAttendance) {
        this.maxAttendance = maxAttendance;
    }

    public boolean isPrivateEvent() {
        return privateEvent;
    }

    public void setPrivateEvent(boolean privateEvent) {
        this.privateEvent = privateEvent;
    }
}
