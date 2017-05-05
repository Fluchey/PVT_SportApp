package com.sportify.createEvent.createEventPageThree.activity;

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
import android.widget.Toast;

import com.sportify.showFriends.Profile;
import com.sportify.createEvent.createEventPageThree.presenter.CreateEventInviteFriendsPresenter;
import com.sportify.createEvent.createEventPageThree.presenter.CreateEventInviteFriendsPresenterImpl;

import java.util.ArrayList;
import java.util.HashMap;

import sportapp.pvt_sportapp.R;

public class CreateEventInviteFriendsActivity extends AppCompatActivity implements CreateEventInviteFriendsView {

    private Button showMarkedFriends;
    private ListView friendList;
    private ArrayList friendArray;
    private CreateEventInviteFriendsPresenter createEventInviteFriendsPresenter;
    private MyArrayAdapter myArrayAdapter;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event_invite_friends);

        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        createEventInviteFriendsPresenter = new CreateEventInviteFriendsPresenterImpl(this, sharedPref);
        showMarkedFriends = (Button) findViewById(R.id.btShowMarkedFriends);
        friendList = (ListView) findViewById(R.id.lvCreateEventFriends);
        myArrayAdapter = new MyArrayAdapter(this, R.layout.create_event_friend_list_item, null);

        friendList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        createEventInviteFriendsPresenter.showFriends();

        showMarkedFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMarkedFriends();
            }
        });
    }

    @Override
    public void showFriends(ArrayList friends) {
        friendArray = friends;
        myArrayAdapter = new MyArrayAdapter(this, R.layout.create_event_friend_list_item, friends);
        friendList.setAdapter(myArrayAdapter);
    }

    @Override
    public ArrayList getMarkedFriends() {
        return myArrayAdapter.getMarkedFriends();
    }

    private class MyArrayAdapter extends ArrayAdapter<Profile> {

        private HashMap<Integer, Boolean> checkedFriends = new HashMap<Integer, Boolean>();
        private ArrayList<Profile> friends = new ArrayList<>();

        public MyArrayAdapter(Context context, int rowId, ArrayList<Profile> friends) {
            super(context, rowId, friends);
            this.friends = friends;

            if (friends != null) {
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
            row = layoutInflater.inflate(R.layout.create_event_friend_list_item, null);
            final CheckedTextView friendName = (CheckedTextView) row.findViewById(R.id.ctvInviteFriendName);

            ImageView imageView = (ImageView) row.findViewById(R.id.inviteFriendsProfilePicture);
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
            String toastText = "";
            ArrayList<Profile> markedFriends = new ArrayList<>();
            for (int i = 0; i < friends.size(); i++) {
                if (checkedFriends.get(i)) {
                    markedFriends.add(friends.get(i));
                }
            }

            for(int i = 0; i < markedFriends.size(); i++){
                toastText += markedFriends.get(i).getFirstname();
                if(i<markedFriends.size()-1){
                    toastText += ", ";
                }
            }
            if (!markedFriends.isEmpty()) {
                toastText += " " + getText(R.string.have_been_choosen);
            }else {
                toastText += getText(R.string.no_friends_have_been_choosen);
            }
            Toast.makeText(CreateEventInviteFriendsActivity.this, toastText, Toast.LENGTH_LONG).show();
            return markedFriends;
        }
    }
}



