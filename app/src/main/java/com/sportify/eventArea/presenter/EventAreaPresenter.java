package com.sportify.eventArea.presenter;

/**
 * Created by fluchey on 2017-05-17.
 */

public interface EventAreaPresenter {
    void getEventFromDb(int eventId);
    void shareEventToFacebook();
    void sendResponsEventInvite(String response, int eventId);
}
