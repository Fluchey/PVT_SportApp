package com.sportify.arrayAdapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.sportify.createEvent.createEventInviteFriends.activity.CreateEventInviteFriendsView;
import com.sportify.showFriends.Profile;

import java.util.ArrayList;
import java.util.HashMap;

import sportapp.pvt_sportapp.R;

/**
 * Created by Maja on 2017-05-09.
 */

public class MyArrayAdapterInviteFriends extends ArrayAdapter {

    CreateEventInviteFriendsView inviteFriendsView;
    private HashMap<Integer, Boolean> checkedFriends = new HashMap<Integer, Boolean>();
    private ArrayList<Profile> friends = new ArrayList<>();

    public MyArrayAdapterInviteFriends(Context context, int rowId, ArrayList<Profile> friends, CreateEventInviteFriendsView inviteFriendsView) {
        super(context, rowId, friends);
        this.friends = friends;
        this.inviteFriendsView = inviteFriendsView;

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
        final TextView friendName = (TextView) row.findViewById(R.id.ctvInviteFriendName);

        ImageView imageView = (ImageView) row.findViewById(R.id.inviteFriendsProfilePicture);
//        imageView.setImageResource(friends.get(position).getProfilePicture());
        String imageBase64 = friends.get(position).getProfilePicture();
        System.out.println("Profilbild " + imageBase64);

        if (!imageBase64.isEmpty()) {
            Bitmap bitmap = com.sportify.util.Profile.decodeStringToBitmap(imageBase64);
            imageView.setImageBitmap(bitmap);
        }

        friendName.setText(friends.get(position).toString());

        final CheckBox checkBox = (CheckBox) row.findViewById(R.id.checktest);

        checkBox.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                checkedFriends.put(position, checkBox.isChecked());
            }
        });
        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkBox.toggle();
                checkedFriends.put(position, checkBox.isChecked());
            }
        });
        friendName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox.toggle();
                checkedFriends.put(position, checkBox.isChecked());
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

        if(!markedFriends.isEmpty()){
            if(markedFriends.size() == 1){
                String friendName = markedFriends.get(0).getFirstname();
                toastText += " " + friendName + " " + getContext().getString(R.string.one_friend_has_been_choosen);
            }else{
                for(int i = 0; i<markedFriends.size(); i++){
                    if(i == markedFriends.size()-1){
                        toastText += " och " + markedFriends.get(i).getFirstname() + " ";
                    }else if(i == markedFriends.size()-2){
                        toastText += markedFriends.get(i).getFirstname() + " ";
                    }else{
                        toastText += markedFriends.get(i).getFirstname() + ", ";
                    }
                }
                toastText += getContext().getString(R.string.several_friends_have_been_choosen);
            }
        }else{
            toastText += getContext().getString(R.string.no_friends_have_been_choosen);
        }
        inviteFriendsView.showToastToUser(toastText);
        return markedFriends;
    }
}
