package com.sportify.arrayAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sportify.showFriends.Profile;

import java.util.ArrayList;

import sportapp.pvt_sportapp.R;

/**
 * Created by Maja on 2017-05-09.
 */

public class MyArrayAdapterShowFriends extends ArrayAdapter {


    private ArrayList<Profile> friends = new ArrayList<>();

    public MyArrayAdapterShowFriends(Context context, int rowId, ArrayList<Profile> friends) {
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
        friendName.setText(firstName + " " + lastName.charAt(0));

        return row;
    }
}
