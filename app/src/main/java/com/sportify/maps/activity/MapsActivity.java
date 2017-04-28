package com.sportify.maps.activity;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import sportapp.pvt_sportapp.R;

public class MapsActivity extends FragmentActivity implements MapsView, OnMapReadyCallback {
    private MapsPresenter mapsPresenter;
    private SharedPreferences sharedPref;

    private static LatLng STHLM;
    private static float DEFAULT_ZOOM = 10;

    private GoogleMap mMap;

    private EditText category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        mapsPresenter = new MapsPresenterImpl(this, sharedPref);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        category = (EditText) (findViewById(R.id.etEnterCategory));
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

    private void goToLocation(double lat, double lon, float zoom){
        LatLng ll = new LatLng(lat, lon);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mMap.moveCamera(update);
    }

    public void geoLocate(View view) throws IOException {
//        String location = wantToGo.getText().toString();

        Geocoder gc = new Geocoder(this);
//        List<Address> list = gc.getFromLocationName(location, 1);

//        Address address = list.get(0);

//        goToLocation(address.getLatitude(), address.getLongitude(), 15);
    }

    public void showFootBallFields(View view) {
        mMap.clear();
        createMarker("Älvsjö IP", "Fotboll", 59.274508847095404, 18.009372266458495);
        createMarker("Gröndal Bollplan", "Fotboll", 59.31491551417768, 18.000463709042712);
    }

    public void showSwimPools(View view) {
        mMap.clear();
//        createMarker("Eriksdalsbadet", "Simhall", 59.30449679409284, 18.07552995325442);
        createMarker("Västertorp simhall", "Simhall", 59.29335865879633, 17.977220258441527);
    }

    public void showCategory(View view) {
        mapsPresenter.getMarkersForCategory();
    }

    @Override
    public String getCategory() {
        return category.getText().toString();
    }
}
