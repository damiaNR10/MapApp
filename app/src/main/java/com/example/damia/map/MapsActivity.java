package com.example.damia.map;

import android.app.Notification;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Random;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Button buttonChangeColor;
    boolean success;
    boolean clicked = false;

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
                if(!clicked){
                try {
                    Log.d("sprawdzam", "dark");
                    success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getApplicationContext(), R.raw.mapstyle_dark));//podpięcie layoutu do mapy (pliku json)
                    if (!success) Log.e("MapsActivity", "Style parsing failed.");
                    clicked = true;
                    buttonChangeColor.setText("Bright");
                    buttonChangeColor.setBackground(getDrawable(R.drawable.custom_button_bright));
                } catch (Resources.NotFoundException e) {
                    Log.e("MapsActivity", "Can't find style. Error: ", e);
                }}

                else{
                    try {
                        Log.d("sprawdzam", "dark");
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
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        LatLng rzeszow = new LatLng(50.0413200,21.9990100); //stworzenie nowego miejsca o podanych wspolrzednych
        mMap.addMarker(new MarkerOptions().position(rzeszow).title("Marker in Rzeszow"));//ustawienie "punktu startowego" mapy
        mMap.moveCamera(CameraUpdateFactory.newLatLng(rzeszow));
    }
}