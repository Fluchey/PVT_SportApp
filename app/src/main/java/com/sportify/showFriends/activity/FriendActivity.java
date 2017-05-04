package com.sportify.showFriends.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.ListView;

import com.sportify.showFriends.Profile;
import com.sportify.showFriends.presenter.FriendPresenter;
import com.sportify.showFriends.presenter.FriendPresenterImpl;

import java.util.ArrayList;
import java.util.HashMap;

import sportapp.pvt_sportapp.R;

public class FriendActivity extends AppCompatActivity implements FriendView {

    Button showMarkedFriends;
    ListView friendList;
    ArrayList friendArray;
    FriendPresenter friendPresenter;
    MyArrayAdapter myArrayAdapter;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        friendPresenter = new FriendPresenterImpl(this, sharedPref);
        showMarkedFriends = (Button) findViewById(R.id.btShowMarkedFriends);
        friendList = (ListView) findViewById(R.id.lvFriends);
        myArrayAdapter = new MyArrayAdapter(this, R.layout.friend_list_item, null);

        friendList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        friendPresenter.showFriends();

        showMarkedFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myArrayAdapter.getMarkedFriends();
            }
        });
    }

    @Override
    public void showFriends(ArrayList friends) {
        friendArray = friends;
        myArrayAdapter = new MyArrayAdapter(this, R.layout.friend_list_item, friends);
        friendList.setAdapter(myArrayAdapter);
    }

    private class MyArrayAdapter extends ArrayAdapter<Profile> {

        private HashMap<Integer, Boolean> checkedFriends = new HashMap<Integer, Boolean>();
        private ArrayList<Profile> friends = new ArrayList<>();

        public MyArrayAdapter(Context context, int rowId, ArrayList<Profile> friends) {
            super(context, rowId, friends);
            this.friends = friends;

            if(friends != null) {
                for (int i = 0; i < friends.size(); i++) {
                    checkedFriends.put(i, false);
                }
            }
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
            final CheckedTextView friendName = (CheckedTextView) row.findViewById(R.id.ctvFriend);

            ImageView imageView = (ImageView) row.findViewById(R.id.profilePicture);
            imageView.setImageResource(friends.get(position).getProfilePicture());

            String firstName = friends.get(position).getFirstname();
            String lastName = friends.get(position).getLastname();
            friendName.setText(firstName + " " + lastName);


            friendName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    friendName.toggle();
                    checkedFriends.put(position, friendName.isChecked());
                }
            });
            return row;
        }

        public ArrayList<Profile> getMarkedFriends() {
            ArrayList<Profile> markedFriends = new ArrayList<>();
            for(int i=0; i<friends.size(); i++){
                if(checkedFriends.get(i)){
                    markedFriends.add(friends.get(i));
                }
            }
            System.out.println(markedFriends.toString());
            return markedFriends;
        }
    }
}



