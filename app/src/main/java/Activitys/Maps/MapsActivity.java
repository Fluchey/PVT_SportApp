package Activitys.Maps;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import sportapp.pvt_sportapp.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static LatLng STHLM = new LatLng(59.3293, 18.0686);
    private static float MIN_ZOOM = 10;
    private static float DEFAULT_ZOOM = 15;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        try {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            user = new LatLng(location.getLatitude(), location.getLongitude());
        }
        catch (SecurityException e) {
            Log.e("Permission error", "Location access permission denied");
        }

        Marker userMarker = mMap.addMarker(new MarkerOptions().position(user).title("Your position"));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(user, DEFAULT_ZOOM));
        // mMap.setMinZoomPreference(MIN_ZOOM);
    }
}
