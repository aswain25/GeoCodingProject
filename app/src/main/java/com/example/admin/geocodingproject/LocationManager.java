package com.example.admin.geocodingproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationManager
{
    static final String TAG = LocationManager.class.getSimpleName();

    FusedLocationProviderClient locationProviderClient;
    LocationCallback mLocationCallback;
    Context context;
    private Location location;
    private Handler handler;

    public LocationManager(Context context, Handler handler)
    {
        this.context = context;
        this.handler = handler;
    }

    public Location getCurrentLocation()
    {
        return location;
    }

    private void setLocation(Location location) {
        this.location = location;
    }

    @SuppressLint("MissingPermission")
    public void getLocation()
    {
        locationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        locationProviderClient.getLastLocation().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e.toString());
            }
        }).addOnSuccessListener(new OnSuccessListener<Location>()
        {
            @Override
            public void onSuccess(Location location)
            {
                //Log.d(TAG, "onSuccess: " + location.toString());
                setLocation(location);
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putParcelable("location", location);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });
    }

    @SuppressLint("MissingPermission")
    public void startLocationUpdates()
    {
        final LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(100);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mLocationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationRequest == null)
                {
                    Log.d(TAG, "onLocationResult: is null");
                    return;
                }
                for (Location location : locationResult.getLocations())
                {
                    Log.d(TAG, "onLocationResult: " + location.toString());
                }
            }
        };

        locationProviderClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.getMainLooper());
    }

    public void soptLocationUpdates()
    {
        locationProviderClient.removeLocationUpdates(mLocationCallback);
    }
}
