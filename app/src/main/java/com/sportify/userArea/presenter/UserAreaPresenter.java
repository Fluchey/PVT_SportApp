package com.sportify.userArea.presenter;

import com.sportify.storage.Event;

import java.util.ArrayList;

/**
 * Created by fluchey on 2017-04-20.
 */

public interface UserAreaPresenter {
    ArrayList<Event> getEventsFromStorage();
}
