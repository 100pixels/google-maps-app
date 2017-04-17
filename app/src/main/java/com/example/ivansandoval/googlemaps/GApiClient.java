package com.example.ivansandoval.googlemaps;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class GApiClient implements GoogleApiClient.ConnectionCallbacks,
                                                                    GoogleApiClient.OnConnectionFailedListener{

    private static GApiClient gApiClient;
    private static GoogleApiClient googleApiClient;

    private LocationRequest locationRequest;
    private Context context;
    private LocationListener locationListener;

    private GApiClient(){
        locationRequest = GLocationRequest.getInstance().initLocationRequest();
    }

    public static GApiClient getInstance(){
        if(gApiClient == null){
            gApiClient = new GApiClient();
        }
        return gApiClient;
    }

    public GoogleApiClient initApiClient(Context context, LocationListener locationListener){
        this.context = context;
        this.locationListener = locationListener;

        if(googleApiClient == null){
            googleApiClient= new GoogleApiClient.Builder(this.context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        return googleApiClient;
    }

    public void startLocationUpdates(){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, locationListener);
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
