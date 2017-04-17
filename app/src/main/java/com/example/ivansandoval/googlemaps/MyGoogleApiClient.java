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
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class MyGoogleApiClient  implements
                                                                        GoogleApiClient.ConnectionCallbacks,
                                                                        GoogleApiClient.OnConnectionFailedListener{

    private GoogleApiClient apiClient;
    private Context context;
    private final String LOG_TAG = "MyGoogleApiClient";

    public MyGoogleApiClient(Context context) {
        this.context = context;
        buildApiClient();
    }

    public GoogleApiClient getClient(){
        return apiClient;
    }

    private void buildApiClient() {
        apiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        Log.d(LOG_TAG, "GoogleApiClient builded");
    }

    /*
    * The ConnectionCallbacks interface is an optional implementation
     * just in case your app needs to know when the
    * automatically managed connection is established or suspended
    * */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(LOG_TAG,"onConnected");
    }
    @Override
    public void onConnectionSuspended(int i) {
        Log.d(LOG_TAG,"onConnectionSuspended");
    }

    // This method receive unresolvable connection errors
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {Log.d(LOG_TAG,"onConnectionFailed");}

}
