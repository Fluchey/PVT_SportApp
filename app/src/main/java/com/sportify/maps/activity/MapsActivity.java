package com.sportify.maps.activity;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
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
    @Inject
    MapsPresenter mapsPresenter;

    private static LatLng STHLM = new LatLng(59.3293, 18.0686);
    private static float MIN_ZOOM = 10;
    private static float DEFAULT_ZOOM = 12;

    private GoogleMap mMap;
    private ArrayList<Marker> markers;

    private EditText wantToGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        markers = new ArrayList<>();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        wantToGo = (EditText) (findViewById(R.id.etWantToGo));
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

        // Add a marker and move the camera
        LatLng user = STHLM;
        LatLng test = new LatLng(59.30449679409284, 18.07552995325442);

        try {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            user = new LatLng(location.getLatitude(), location.getLongitude());
            test = new LatLng(location.getLatitude(), location.getLongitude());
        } catch (SecurityException e) {
            Log.e("Permission error", "Location access permission denied");
        }

//        Marker userMarker = mMap.addMarker(new MarkerOptions().position(user).title("Your position"));
//        Marker testMark = mMap.addMarker(new MarkerOptions().position(test).title("Test Eriksdal"));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(user, DEFAULT_ZOOM));
        // mMap.setMinZoomPreference(MIN_ZOOM);
    }

    @Override
    public void removeMarkers() {

    }

    @Override
    public void showMarkerAt(String eventName, double latitude, double longitude) {
        markers.add(createMarker(eventName, latitude, longitude));
    }

    private Marker createMarker(String eventName, double latitude, double longitude){
        return mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(eventName));
    }

    private void goToLocation(double lat, double lon, float zoom){
        LatLng ll = new LatLng(lat, lon);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mMap.moveCamera(update);
    }

    public void geoLocate(View view) throws IOException {
        String location = wantToGo.getText().toString();

        Geocoder gc = new Geocoder(this);
        List<Address> list = gc.getFromLocationName(location, 1);

        Address address = list.get(0);

        String locality = address.getLocality();

        Toast.makeText(this, locality, Toast.LENGTH_LONG).show();
        goToLocation(address.getLatitude(), address.getLongitude(), 15);


    }
}
