package com.sportify.arrayAdapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sportify.storage.Participant;

import java.util.ArrayList;

import sportapp.pvt_sportapp.R;

/**
 * Created by Maja on 2017-05-30.
 */

public class MyArrayAdapterParticipants extends ArrayAdapter{

    private ArrayList<Participant> participants;

    public MyArrayAdapterParticipants(Context context, int rowId, ArrayList<Participant> participants){
        super(context, rowId, participants);
        this.participants = participants;
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
        row = layoutInflater.inflate(R.layout.participant_list_item, null);
        final TextView participantName = (TextView) row.findViewById(R.id.participantName);

        ImageView participantProfilePicture = (ImageView) row.findViewById(R.id.participantProfilePicture);

        TextView participantStatus = (TextView) row.findViewById(R.id.particpantStatus);

        String imageBase64 = participants.get(position).getProfilePicture();

        if (!imageBase64.isEmpty()) {
            Bitmap bitmap = com.sportify.util.Profile.decodeStringToBitmap(imageBase64);
            participantProfilePicture.setImageBitmap(bitmap);
        }

        participantName.setText(participants.get(position).toString());
        participantStatus.setText(participants.get(position).getStatus());

        return row;
    }
}
