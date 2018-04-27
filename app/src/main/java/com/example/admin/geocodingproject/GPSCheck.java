package com.example.admin.geocodingproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class GPSCheck extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        android.location.LocationManager locationManager = (android.location.LocationManager) context.getSystemService(context.LOCATION_SERVICE);


        if(locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER))
        {
            Intent intent1 = new Intent(context.getApplicationContext(), MainActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }
        else
        {
            Intent intent1 = new Intent(context.getApplicationContext(), MainActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }



    }


}