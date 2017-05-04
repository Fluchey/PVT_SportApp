package com.sportify.maps.activity;

import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sportify.maps.presenter.MapsPresenter;
import com.sportify.maps.presenter.MapsPresenterImpl;
import com.wang.avi.AVLoadingIndicatorView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import sportapp.pvt_sportapp.R;

public class MapsActivity extends FragmentActivity implements MapsView, OnMapReadyCallback, AdapterView.OnItemClickListener {
    private MapsPresenter           mapsPresenter;
    private SharedPreferences       sharedPref;

    private LatLng                  STHLM;
    private float                   DEFAULT_ZOOM;

    private FragmentManager         fragmentManager;
    private FragmentTransaction     fragmentTransaction;
    private SupportMapFragment      mapFragment;
    private ListFragment            listFragment;
    private GoogleMap               mMap;

    private ArrayAdapter<String>    adapter;
    private EditText                editTextSearch;
    private String                  categoryChosen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        mapsPresenter = new MapsPresenterImpl(this, sharedPref);

        STHLM = new LatLng(59.3293, 18.0686);
        DEFAULT_ZOOM = 10;

        mapFragment = SupportMapFragment.newInstance();
        mapFragment.getMapAsync(this);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
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
                ArrayList<String> arr = new ArrayList<String>();
                arr.add(s.toString());
                updateAdapter(arr);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void updateAdapter(ArrayList<String> s) {
        ArrayList<String> test = new ArrayList<>(Arrays.asList("First item", "Second item", s.toString()));
        for(int i = 0; i < 1000; i++){
            test.add("Nu kör vi");
        }
        adapter.clear();
        adapter.addAll(test);
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

        /* Location of Stockholm */
//        goToLocation(STHLM.latitude, STHLM.longitude, DEFAULT_ZOOM);

    }

    @Override
    public void clearMarkers() {
        mMap.clear();
    }

    @Override
    public void showMarkerAt(String eventName, String description, double latitude, double longitude) {
        createMarker(eventName, description, latitude, longitude);
    }

    private Marker createMarker(String eventName, String description, double latitude, double longitude){
        return mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(eventName).snippet(description).draggable(true));
    }

    @Override
    public void goToLocation(double lat, double lon, float zoom){
        LatLng ll = new LatLng(lat, lon);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mMap.moveCamera(update);
    }

    @Override
    public String getCategory() {
        return categoryChosen;
    }

    @Override
    public String getPlaceName() {
        return editTextSearch.getText().toString();
    }

    @Override
    public void updatePlaceSearch(ArrayList<String> places) {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, places);
        Log.d("Count: ", String.valueOf(adapter.getCount()));
//        autoCompleteTextView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String [] arr = getResources().getStringArray(R.array.sections);
        categoryChosen = (arr[(int) id]);
        mapsPresenter.getMarkersForCategory();
    }

    public void showPlaceByName(View view) {
        mapsPresenter.showPlaceByName();
    }

    public void switchToMapFragment(View view) {
        mapFragment = SupportMapFragment.newInstance();
        mapFragment.getMapAsync(this);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        trans.replace(R.id.fragment_container, mapFragment);
        trans.commit();
        goToLocation(STHLM.latitude, STHLM.longitude, DEFAULT_ZOOM);
    }

    public void switchToListFragment(View view) {
        listFragment = new ListFragment();
        listFragment.setListAdapter(adapter);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        trans.replace(R.id.fragment_container, listFragment);
        trans.commit();
    }


}
