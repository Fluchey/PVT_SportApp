package com.sportify.maps.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sportify.maps.presenter.MapsPresenter;
import com.sportify.maps.presenter.MapsPresenterImpl;
import com.wang.avi.AVLoadingIndicatorView;

import sportapp.pvt_sportapp.R;

public class MapsActivity extends FragmentActivity implements MapsView, OnMapReadyCallback, AdapterView.OnItemClickListener {
    private MapsPresenter mapsPresenter;
    private SharedPreferences sharedPref;

    private static LatLng STHLM;
    private static float DEFAULT_ZOOM = 10;

    private GoogleMap mMap;

    private TextView categoryChosen;
    private AVLoadingIndicatorView loadingIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        mapsPresenter = new MapsPresenterImpl(this, sharedPref);

        ListView listview = (ListView) findViewById(R.id.mapsListView);
        listview.setOnItemClickListener(this);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        categoryChosen = (TextView) (findViewById(R.id.twCategoryChosen));
        loadingIndicator = (AVLoadingIndicatorView) (findViewById(R.id.mapLoadIndicator));
//        closeLoadIndicator();
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
        goToLocation(59.3293, 18.0686, DEFAULT_ZOOM);

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
        return mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(eventName).snippet(description));
    }

    @Override
    public void goToLocation(double lat, double lon, float zoom){
        LatLng ll = new LatLng(lat, lon);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mMap.moveCamera(update);
    }

    public void showCategory(View view) {
        mapsPresenter.getMarkersForCategory();
    }

    @Override
    public String getCategory() {
        return categoryChosen.getText().toString();
    }

    @Override
    public void showLoadIndicator() {
        loadingIndicator.show();
    }

    @Override
    public void closeLoadIndicator() {
        loadingIndicator.hide();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String [] arr = getResources().getStringArray(R.array.sections);
        categoryChosen.setText(arr[(int) id]);
    }
}
