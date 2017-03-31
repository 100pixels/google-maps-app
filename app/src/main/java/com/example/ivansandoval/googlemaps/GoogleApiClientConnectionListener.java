package com.example.ivansandoval.googlemaps;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/*
* Esta clase ya no se utiliza, las funciones que implementaba se encuentran
* en la clase MyGoogleApiClient
* */

public class GoogleApiClientConnectionListener implements
                                                                    GoogleApiClient.ConnectionCallbacks,
                                                                    GoogleApiClient.OnConnectionFailedListener{


    LocationRequest mLocationRequest;// Un objeto para hacer peticiones con parámetros de calidad de servicio


    private Context context;
    private MyGoogleApiClient apiClient;
    //private MyGoogleMap map;
    private LocationChangedListener locationChangedListener;
    private final String LOG_TAG = "LocationSeviceListener";

    public GoogleApiClientConnectionListener(Context context) {
        this.context = context;
    }

    public void setApiClient(MyGoogleApiClient apiClient) {
        this.apiClient = apiClient;
    }

    //public void setMap(MyGoogleMap map) {this.map = map;}

    public void setLocationChangedListener(LocationChangedListener locationChangedListener) {
        this.locationChangedListener = locationChangedListener;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(LOG_TAG,"onConnected");
        new LocationRequestManager().
                requestLocationUpdates(
                        context, apiClient.getClient(),
                                    locationChangedListener);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(LOG_TAG,"onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(
                @NonNull ConnectionResult connectionResult) {
                        Log.d(LOG_TAG,"onConnectionFailed");
    }

}
