package com.example.admin.geocodingproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import static android.location.GpsStatus.GPS_EVENT_STARTED;
import static android.location.GpsStatus.GPS_EVENT_STOPPED;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, Handler.Callback {

    private GoogleMap mMap;
    private EditText etLat;
    private EditText etLon;
    private LatLng currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Location location = (Location) getIntent().getParcelableExtra("location");
        currentLocation = new LatLng(location.getLatitude(), location.getLongitude());

        etLat = findViewById(R.id.etLat);
        etLon = findViewById(R.id.etLon);

        startClient(currentLocation.latitude, currentLocation.longitude);
    }

    private void startClient(double latitude, double longitude)
    {
        String APIKey = "AIzaSyBbRgv2WPnuhrWRX-xKl3lYE5V-n4OtSBM";
        String URL = String.format("https://maps.googleapis.com/maps/api/geocode/json?latlng=%f,%f&key=%s", latitude, longitude, APIKey);
        NativeClient client = new NativeClient(URL, new Handler(this));
        client.start();
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        moveTo(currentLocation.latitude, currentLocation.longitude);
    }

    private void moveTo(double latitude, double longitude)
    {
        currentLocation = new LatLng(latitude, longitude);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(latitude, longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public boolean handleMessage(Message msg) {
        Gson gson = new Gson();
        com.example.admin.geocodingproject.model.Location location = gson.fromJson(msg.getData().getString("content"), com.example.admin.geocodingproject.model.Location.class);

        String address = "Address not available";
        if (location.getResults().size() > 0)
            address = location.getResults().get(0).getFormattedAddress();
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(currentLocation).title(address));
        return false;
    }

    public void btnGoto_Clicked(View view)
    {
        double lat = Double.parseDouble(etLat.getText().toString()), lon = Double.parseDouble(etLon.getText().toString());
        startClient(lat, lon);
        moveTo(lat, lon);
    }
}