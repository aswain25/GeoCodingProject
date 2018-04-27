package com.example.admin.geocodingproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static android.location.GpsStatus.GPS_EVENT_STARTED;
import static android.location.GpsStatus.GPS_EVENT_STOPPED;

public class MainActivity extends AppCompatActivity implements PermissionManager.IPermissionManager, Handler.Callback
{
    public static final String TAG = MainActivity.class.getSimpleName();
    PermissionManager permissionManager;
    LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permissionManager = new PermissionManager(this);
        locationManager = new LocationManager(this, new Handler(this));

        permissionManager.checkPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionManager.checkResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onPermissionResult(boolean isGranted)
    {
        if (isGranted)
        {
            locationManager.getLocation();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        locationManager.startLocationUpdates();
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.soptLocationUpdates();
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.getData().getParcelable("location") != null)
        {
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            intent.putExtra("location", msg.getData().getParcelable("location"));
            startActivity(intent);
        }
        return  false;
    }
}