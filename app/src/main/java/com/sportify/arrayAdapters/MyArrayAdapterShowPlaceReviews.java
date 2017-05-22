package com.sportify.arrayAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.sportify.storage.PlaceReview;

import java.util.ArrayList;

import sportapp.pvt_sportapp.R;

public class MyArrayAdapterShowPlaceReviews extends ArrayAdapter {


    private ArrayList<PlaceReview> reviews = new ArrayList<>();

    public MyArrayAdapterShowPlaceReviews(Context context, int rowId, ArrayList<PlaceReview> reviews) {
        super(context, rowId, reviews);
        this.reviews = reviews;
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
        row = layoutInflater.inflate(R.layout.placereview_list_item, null);

        final TextView name = (TextView) row.findViewById(R.id.placereviewName);
        final TextView comment = (TextView) row.findViewById(R.id.placereviewComment);
        final TextView date = (TextView) row.findViewById(R.id.placereviewDate);
        RatingBar rating = (RatingBar) row.findViewById(R.id.placereviewRating);

        name.setText(reviews.get(position).getName());
        comment.setText(reviews.get(position).getComment());
        date.setText(reviews.get(position).getDate());
        rating.setRating(reviews.get(position).getRating());

        return row;
    }
}
