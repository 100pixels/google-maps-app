package com.example.ivansandoval.googlemaps;


import com.google.android.gms.location.LocationRequest;

public class GLocationRequest {

    private static GLocationRequest gLocationRequest;
    private LocationRequest locationRequest;

    private GLocationRequest(){}

    public static GLocationRequest getInstance(){
        if(gLocationRequest == null){
            gLocationRequest = new GLocationRequest();
        }
        return gLocationRequest;
    }

    public LocationRequest initLocationRequest(){
        if(locationRequest == null){
            locationRequest = new LocationRequest();
            locationRequest.setInterval(5000);
            locationRequest.setFastestInterval(2500);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        }
        return locationRequest;
    }

}
