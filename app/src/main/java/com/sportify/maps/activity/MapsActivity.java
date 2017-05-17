package com.sportify.maps.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sportify.createEvent.createEventPageOne.activity.CreateEventPageOnePageOneActivity;
import com.sportify.maps.CustListFragment;
import com.sportify.maps.presenter.MapsPresenter;
import com.sportify.maps.presenter.MapsPresenterImpl;
import com.sportify.notifications.activity.NotificationActivity;
import com.sportify.settings.activity.SettingsActivity;
import com.sportify.showFriends.activity.ShowFriendsActivity;
import com.sportify.userArea.activity.UserAreaActivity;

import java.util.ArrayList;

import sportapp.pvt_sportapp.R;

public class MapsActivity extends FragmentActivity implements MapsView, OnMapReadyCallback {
    private MapsPresenter mapsPresenter;
    private SharedPreferences sharedPref;

    private LatLng STHLM;
    private LatLng CURRENT_LOCATION;
    private float DEFAULT_ZOOM;
    private float CURRENT_ZOOM;

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;

    private RadioGroup radioGroup;
    private RadioButton eventCheckBox;
    private RadioButton placesCheckBox;
    private boolean eventToggled;

    private ArrayAdapter<String> adapter;
    private EditText editTextSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        mapsPresenter = new MapsPresenterImpl(this, sharedPref);

        CURRENT_LOCATION = new LatLng(59.3293, 18.0686);
        CURRENT_ZOOM = 10;

        mapFragment = SupportMapFragment.newInstance();
        mapFragment.getMapAsync(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, mapFragment);
        fragmentTransaction.commit();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        adapter.setNotifyOnChange(true);

        editTextSearch = (EditText) (findViewById(R.id.etMapsSearch));
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mapsPresenter.updateSearchResult(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        radioGroup = (RadioGroup) findViewById(R.id.eventPlacesGroup);
        placesCheckBox = (RadioButton) findViewById(R.id.placesRadioButton);
        placesCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mapsPresenter.updateSearchResult(editTextSearch.getText().toString());
                eventToggled = false;
            }
        });
        eventCheckBox = (RadioButton) findViewById(R.id.EventRadioButton);
        eventCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mapsPresenter.updateSearchResult(editTextSearch.getText().toString());
                eventToggled = true;
            }
        });

        placesCheckBox.setEnabled(false);
        eventCheckBox.setEnabled(false);
//        eventToggled = true;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

            }
        });

        /* Location of Stockholm */
        goToLocation(CURRENT_LOCATION.latitude, CURRENT_LOCATION.longitude, CURRENT_ZOOM);
        mapsPresenter.showCurrentPlacesOnMap("");
        mapsPresenter.showCurrentEventsOnMap("");
        eventCheckBox.setEnabled(true);
        placesCheckBox.setEnabled(true);
    }

    @Override
    public void showPlaceMarkerAt(String placeName, String description, double latitude, double longitude) {
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(placeName).snippet(description).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
    }

    @Override
    public void showEventMarkerAt(String eventName, String category, double latitude, double longitude) {
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(eventName).snippet(category).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
    }

    @Override
    public void goToLocation(double lat, double lon, float zoom) {
        LatLng ll = new LatLng(lat, lon);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mMap.moveCamera(update);
    }

    @Override
    public void updatePlaceSearch(ArrayList<String> places) {
        adapter.addAll(places);
    }

    @Override
    public String getTextSearch() {
        return editTextSearch.getText().toString();
    }

    @Override
    public void setTextSearch(String text) {
        editTextSearch.setText(text);
    }

    @Override
    public void clearMarkers() {
        mMap.clear();
        adapter.clear();
    }

    @Override
    public void clearPlaces() {
        adapter.clear();
    }

    @Override
    public void switchToMapFragmentFromPresenter(double lat, double lon) {
        CURRENT_LOCATION = new LatLng(lat, lon);
        CURRENT_ZOOM = 15;
        mapFragment = SupportMapFragment.newInstance();
        mapFragment.getMapAsync(this);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        trans.replace(R.id.fragment_container, mapFragment);
        trans.commit();
    }

    public void switchToMapFragment(View view) {
        mapFragment = SupportMapFragment.newInstance();
        mapFragment.getMapAsync(this);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        trans.replace(R.id.fragment_container, mapFragment);
        trans.commit();
    }

    public void switchToListFragment(View view) {
        ListFragment listFragment = new CustListFragment();
        listFragment.setListAdapter(adapter);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        trans.replace(R.id.fragment_container, listFragment);
        trans.commit();
    }

    /**
     * This is called from inside the CustListFragments Onclick
     *
     * @param id
     */
    public void goFromListToMap(int id) {
        Log.d("Triggered", "Trigg");
        mapsPresenter.goFromListToMap(id);
    }

    @Override
    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public boolean placesIsChecked() {
        return placesCheckBox.isChecked();
    }

    @Override
    public boolean eventsIsChecked() {
        return eventCheckBox.isChecked();
    }

    @Override
    public GoogleMap getMap() {
        return mMap;
    }

    public void createEventfromMapActivity(View v) {
        Toast.makeText(this, "Clicked Create Event", Toast.LENGTH_LONG).show();
        Intent createEventIntent = new Intent(MapsActivity.this, CreateEventPageOnePageOneActivity.class);
        MapsActivity.this.startActivity(createEventIntent);
    }

    public void goToSettingsActivity(View v){
        Intent goToSettingsViewIntent = new Intent(MapsActivity.this, SettingsActivity.class);
        MapsActivity.this.startActivity(goToSettingsViewIntent);
    }

    public void toFriendsListfromMapActivity(View v){
        Intent goToFriendListIntent = new Intent(MapsActivity.this, ShowFriendsActivity.class);
        MapsActivity.this.startActivity(goToFriendListIntent);
    }

    public void ToUserAreaFromMapActivity(View v){
        Intent goToUserAreaIntent = new Intent(MapsActivity.this, UserAreaActivity.class);
        MapsActivity.this.startActivity(goToUserAreaIntent);
    }

    public void toNoteFromMapActivity(View v){
        Intent goToNoteIntent = new Intent(MapsActivity.this, NotificationActivity.class);
        MapsActivity.this.startActivity(goToNoteIntent);
    }

}
