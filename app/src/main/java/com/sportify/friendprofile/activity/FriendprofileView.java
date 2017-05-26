package com.sportify.friendprofile.activity;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rasmu on 25/05/2017.
 */

public interface FriendprofileView {

    public int getFriendId();
    public void setNameView(String name);
    public void setAgeView(String age);
    public void setInterestsView(List<String> interests);
    public void setDescriptionView(String description);
    public void setPictureView(String img);
}
