package com.sportify.placearea.activity;

import android.view.View;

import java.util.ArrayList;

/**
 * Created by fluchey on 2017-05-18.
 */

public interface PlaceAreaView {
    void setPlaceName(String placeName);
    void setInterests(ArrayList<String> interests);
    void goToWriteReviewActivity(View v);
    void goToUserAreaFromRead(View v);
}
