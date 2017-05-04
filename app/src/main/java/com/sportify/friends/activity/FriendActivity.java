package com.sportify.friends.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.ListView;

import com.sportify.friends.Profile;
import com.sportify.friends.presenter.FriendPresenter;
import com.sportify.friends.presenter.FriendPresenterImpl;

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
                System.out.println("Test!");
                ArrayList<Profile> markedFriends = myArrayAdapter.getMarkedFriends();
                if(markedFriends.isEmpty()){
                    System.out.println("Tomt!");
                }else {
                    for (int i = 0; i < markedFriends.size(); i++) {
                        System.out.println(markedFriends.get(i).getFirstname());
                    }
                }
            }
        });
    }

    @Override
    public void showFriends(ArrayList friends) {
        friendArray = friends;
        myArrayAdapter = new MyArrayAdapter(this, R.layout.friend_list_item, friends);
//        friendList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        friendList.setAdapter(myArrayAdapter);

    }


    private class MyArrayAdapter extends ArrayAdapter<String> {

        private HashMap<Integer, Boolean> myChecked = new HashMap<Integer, Boolean>();
        private ArrayList<Profile> friends = new ArrayList<>();

        public MyArrayAdapter(Context context, int rowId, ArrayList friends) {
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
        public View getView(int position, View convertView, ViewGroup parent) {

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
                }
            });
            return row;
        }

        public ArrayList<Profile> getMarkedFriends() {
            ArrayList<Profile> friendsToReturn = new ArrayList<>();
            SparseBooleanArray checkedFriends = friendList.getCheckedItemPositions();
            System.out.println("sparse " + checkedFriends.toString());

            for (int i = 0; i < checkedFriends.size(); i++) {
                if (checkedFriends.get(i) == true) {
                    friendsToReturn.add(friends.get(i));
                }
            }
            return friendsToReturn;
        }
    }
}



