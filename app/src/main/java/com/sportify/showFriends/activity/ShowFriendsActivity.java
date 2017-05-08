package com.sportify.showFriends.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sportify.showFriends.Profile;
import com.sportify.showFriends.presenter.ShowFriendsPresenter;
import com.sportify.showFriends.presenter.ShowFriendsPresenterImpl;

import java.util.ArrayList;

import sportapp.pvt_sportapp.R;

public class ShowFriendsActivity extends AppCompatActivity implements ShowFriendsView {

    private ListView friendList;
    private ArrayList friendArray;
    private ShowFriendsPresenter showFriendsPresenter;
    private MyArrayAdapter myArrayAdapter;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_friends);

        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        showFriendsPresenter = new ShowFriendsPresenterImpl(this, sharedPref);
        friendList = (ListView) findViewById(R.id.lvShowFriends);
        myArrayAdapter = new MyArrayAdapter(this, R.layout.friend_list_item, null);

        showFriendsPresenter.showFriends();
//        friendList.setAdapter(myArrayAdapter);
    }

    @Override
    public void showFriends(ArrayList<Profile> friendlist) {
        friendArray = friendlist;
        myArrayAdapter = new MyArrayAdapter(this, R.layout.create_event_friend_list_item, friendArray);
        friendList.setAdapter(myArrayAdapter);
    }

    private class MyArrayAdapter extends ArrayAdapter<Profile> {

        private ArrayList<Profile> friends = new ArrayList<>();

        public MyArrayAdapter(Context context, int rowId, ArrayList<Profile> friends) {
            super(context, rowId, friends);
            this.friends = friends;
        }

        /**
         * @param position    - position in listView
         * @param convertView - row to be converted
         * @param parent      - parent view
         * @return
         */
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View row = convertView;

            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.friend_list_item, null);
            final TextView friendName = (TextView) row.findViewById(R.id.tvFriend);

            ImageView imageView = (ImageView) row.findViewById(R.id.profilePicture);
            imageView.setImageResource(friends.get(position).getProfilePicture());

            String firstName = friends.get(position).getFirstname();
            String lastName = friends.get(position).getLastname();
            friendName.setText(firstName + " " + lastName);

            return row;
        }
    }
}
