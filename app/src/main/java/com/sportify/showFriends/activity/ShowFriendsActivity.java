package com.sportify.showFriends.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sportify.showFriends.Profile;
import com.sportify.showFriends.presenter.ShowFriendsPresenter;
import com.sportify.showFriends.presenter.ShowFriendsPresenterImpl;

import java.util.ArrayList;

import sportapp.pvt_sportapp.R;

public class ShowFriendsActivity extends AppCompatActivity implements ShowFriendsView {

    private ShowFriendsPresenter showFriendsPresenter;
    private ArrayList<Profile> friendArray;
    private SharedPreferences sharedPref;

    /**
     *  ArrayAdapter to friend list
     */
    private MyArrayAdapter myArrayAdapterFriendList;
    private ListView friendList;

    /**
     *  ArrayAdapter to friend search in AutoCompleteTextView
     */
    private ArrayAdapter arrayAdapterSearch;
    private AutoCompleteTextView searchFriend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        showFriendsPresenter = new ShowFriendsPresenterImpl(this, sharedPref);
        friendList = (ListView) findViewById(R.id.lvShowFriends);

        /**
         *  Update friend list
         */
        myArrayAdapterFriendList = new MyArrayAdapter(this, R.layout.friend_list_item, null);

        /**
         *  Update search
         */
        searchFriend = (AutoCompleteTextView) findViewById(R.id.searchFrindAcTv);

        searchFriend.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        searchFriend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }

    @Override
    public void showFriends(ArrayList<Profile> friendlist) {
        friendArray = friendlist;
        myArrayAdapterFriendList = new MyArrayAdapter(this, R.layout.create_event_friend_list_item, friendArray);
        friendList.setAdapter(myArrayAdapterFriendList);
    }

    @Override
    public void updateFriendAdapter(ArrayList<Profile> friendList) {
        //TODO: Behöver troligtvis ändras till MyArrayAdapter senare
        arrayAdapterSearch = new ArrayAdapter(this, android.R.layout.simple_list_item_1, friendList);
//        arrayAdapterSearch = new MyArrayAdapter(this, R.layout.friend_list_item, friendList);
        searchFriend.setAdapter(arrayAdapterSearch);
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
