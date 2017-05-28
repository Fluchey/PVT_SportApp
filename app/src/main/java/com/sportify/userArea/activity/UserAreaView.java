package com.sportify.userArea.activity;

import com.sportify.storage.Event;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by fluchey on 2017-04-20.
 */

public interface UserAreaView {


    void showEvents(ArrayList<Event> events, HashMap<Integer, String> creator, HashMap<Integer, String> placeName, ArrayList<String> eventImage);
}
