package com.example.damia.map;

import android.Manifest;
import android.app.Notification;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.security.Provider;
import java.util.Random;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Button buttonChangeColor;
    boolean success;
    boolean clicked = false;
    private LocationManager locationManager;
    private Location location;
    public Provider provider;
    LocationProvider locationProvider;
    public LatLng newe;
    public String test;
    private FusedLocationProviderClient mFusedLocationClient;
    Task task;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1; // 1 minute
    //private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        buttonChangeColor = (Button) findViewById(R.id.buttonChangeColor);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        buttonChangeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!clicked) {
                    try {
                        success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getApplicationContext(), R.raw.mapstyle_dark));//podpięcie layoutu do mapy (pliku json)
                        if (!success) Log.e("MapsActivity", "Style parsing failed.");
                        clicked = true;
                        buttonChangeColor.setText("Bright");
                        buttonChangeColor.setBackground(getDrawable(R.drawable.custom_button_bright));
                    } catch (Resources.NotFoundException e) {
                        Log.e("MapsActivity", "Can't find style. Error: ", e);
                    }
                } else {
                    try {
                        success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getApplicationContext(), R.raw.mapstyle));//podpięcie layoutu do mapy (pliku json)
                        if (!success) Log.e("MapsActivity", "Style parsing failed.");
                        clicked = false;
                        buttonChangeColor.setText("Dark");
                        buttonChangeColor.setBackground(getDrawable(R.drawable.custom_button));
                    } catch (Resources.NotFoundException e) {
                        Log.e("MapsActivity", "Can't find style. Error: ", e);
                    }
                }
            }
        });

        try {
            success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle));//podpięcie layoutu do mapy (pliku json)
            if (!success) Log.e("MapsActivity", "Style parsing failed.");
        } catch (Resources.NotFoundException e) {
            Log.e("MapsActivity", "Can't find style. Error: ", e);
        }


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            Log.d("sprawdzam", "location = !null");
                            Log.d("sprawdzam", String.valueOf(location.getLatitude()) + " i " + String.valueOf(location.getLongitude()));
                            // Logic to handle location object
                            newe = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(newe).title("Marker in Rzeszow"));//ustawienie "punktu startowego" mapy
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(newe));
                        } else {
                            Log.d("sprawdzam", "location = null");
                        }
                    }
                });
        // Add a marker in Sydney and move the camera
        //Log.d("sprawdzam", String.valueOf(newe.latitude) + "   " + String.valueOf(newe.longitude));
        LatLng sydney = new LatLng(-34, 151);
        LatLng rzeszow = new LatLng(50.0413200,21.9990100); //stworzenie nowego miejsca o podanych wspolrzednych
        //mMap.addMarker(new MarkerOptions().position(rzeszow).title("Marker in Rzeszow"));//ustawienie "punktu startowego" mapy
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(rzeszow));

    }
}