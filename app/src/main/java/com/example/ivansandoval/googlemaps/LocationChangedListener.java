package com.example.ivansandoval.googlemaps;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class LocationChangedListener implements LocationListener {

    Location mLastLocation;// Es una clase que representa una ubicación geografica
    Marker mCurrLocationMarker;//Es un icono que representa un punto en la superficie del mapa
    private GoogleMap map;
    private final String LOG_TAG = "LocationChangedListener";

    public LocationChangedListener() {}

    public LocationChangedListener(GoogleMap map) {
        this.map = map;
    }

    public void setMap(GoogleMap map) {
        this.map = map;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(LOG_TAG,"onLocationChanged");
/*
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        */

        //Place current location marker
        /*
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mCurrLocationMarker = map.addMarker(markerOptions);
        */

        //move map camera
        /*
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.animateCamera(CameraUpdateFactory.zoomTo(11));
        */

        //stop location updates
        /*
        if (apiClient.getClient() != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(apiClient.getClient(), this);
            Log.d(LOG_TAG,"Location updates have been canceled");
        }
        */
    }
}
