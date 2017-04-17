package com.example.ivansandoval.googlemaps;


import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


public class LocationRequestManager {

    private static final int REQUEST_CHECK_SETTINGS = 100;
    private  LocationRequest locationRequest;
    private  final int INTERVAL = 10000;
    private  final int FASTEST_INTERVAL = 5000;
    private  final int LOCATION_REQUEST_TYPE = LocationRequest.PRIORITY_HIGH_ACCURACY;
    private  final String LOG_TAG = "LocationRequestManager";

    public LocationRequestManager(){
        locationRequest = new LocationRequest();
        locationRequest.setInterval(INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        locationRequest.setPriority(LOCATION_REQUEST_TYPE);
    }

    public  LocationRequest getLocationRequest() {
        return locationRequest;
    }

    public  void requestLocationUpdates(Context context,
                                                                                        GoogleApiClient apiClient,
                                                                                                    LocationListener locationListener){
       if (ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            LocationServices.FusedLocationApi.requestLocationUpdates(
                    apiClient, locationRequest,
                    locationListener);
            Log.d(LOG_TAG,"Location updates have been requested");
        }

    }

}
