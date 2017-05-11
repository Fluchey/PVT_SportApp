package com.sportify.arrayAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;

import com.sportify.showFriends.Profile;

import java.util.ArrayList;
import java.util.HashMap;

import sportapp.pvt_sportapp.R;

/**
 * Created by Maja on 2017-05-09.
 */

public class MyArrayAdapterInviteFriends extends ArrayAdapter {


    private HashMap<Integer, Boolean> checkedFriends = new HashMap<Integer, Boolean>();
    private ArrayList<Profile> friends = new ArrayList<>();

    public MyArrayAdapterInviteFriends(Context context, int rowId, ArrayList<Profile> friends) {
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
        System.out.println(toastText);
//        if (!markedFriends.isEmpty()) {
//            toastText += " " + getText(R.string.have_been_choosen);
//        }else {
//            toastText += getText(R.string.no_friends_have_been_choosen);
//        }
//        Toast.makeText(CreateEventInviteFriendsActivity.this, toastText, Toast.LENGTH_LONG).show();
        return markedFriends;
    }
}
