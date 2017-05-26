package com.sportify.friendprofile.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.sportify.friendprofile.presenter.FriendprofilePresenter;
import com.sportify.friendprofile.presenter.FriendprofilePresenterImpl;
import com.sportify.util.Profile;

import java.util.ArrayList;
import java.util.List;

import sportapp.pvt_sportapp.R;

public class FriendprofileActivity extends AppCompatActivity implements FriendprofileView {

    private FriendprofilePresenter presenter;

    private SharedPreferences sharedPref;
    private int friendId;

    private TextView nameView;
    private TextView ageView;
    private TextView interestsView;
    private TextView descriptionView;
    private ImageView pictureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendprofile);

        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        presenter = new FriendprofilePresenterImpl(this, sharedPref);

        nameView = (TextView) findViewById(R.id.tvFriendName);
        ageView = (TextView) findViewById(R.id.tvFriendAge);
        interestsView =(TextView) findViewById(R.id.tvFriendintressen);
        descriptionView = (TextView) findViewById(R.id.tvFriendDescription);
        pictureView = (ImageView) findViewById(R.id.ivProfilePicture);

        Bundle b = getIntent().getExtras();
        if(b != null){
            b.getInt("friendId");
        } else friendId = -1;
    }

    @Override
    public int getFriendId(){ return friendId; }

    @Override
    public void setNameView(String name) { nameView.setText(name);}

    @Override
    public void setAgeView(String age) { ageView.setText(Profile.getAge(age));}

    @Override
    public void setInterestsView(List<String> interests) {
        String formattedInterests = "";
        for(String interest : interests){
            formattedInterests += interest + ", ";
        }
        if (formattedInterests.length() > 0){
            formattedInterests.substring(0, formattedInterests.length() - 3);
        }
        interestsView.setText(formattedInterests);
    }

    public void setDescriptionView(String description) { descriptionView.setText(description);}

    public void setPictureView(String img) { pictureView.setImageBitmap(Profile.decodeStringToBitmap(img)); }
}
