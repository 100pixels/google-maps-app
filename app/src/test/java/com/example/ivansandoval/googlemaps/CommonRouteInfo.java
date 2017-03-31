package com.example.ivansandoval.googlemaps;

import com.google.android.gms.maps.model.LatLng;

public class CommonRouteInfo {

    private LatLng endLocation;
    private LatLng startLocation;
    private int IntDistance;
    private String StrDistance;
    private int IntDuration;
    private String StrDuration;

    public LatLng getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(LatLng endLocation) {
        this.endLocation = endLocation;
    }

    public LatLng getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(LatLng startLocation) {
        this.startLocation = startLocation;
    }

    public int getIntDistance() {
        return IntDistance;
    }

    public void setIntDistance(int intDistance) {
        IntDistance = intDistance;
    }

    public String getStrDistance() {
        return StrDistance;
    }

    public void setStrDistance(String strDistance) {
        StrDistance = strDistance;
    }

    public int getIntDuration() {
        return IntDuration;
    }

    public void setIntDuration(int intDuration) {
        IntDuration = intDuration;
    }

    public String getStrDuration() {
        return StrDuration;
    }

    public void setStrDuration(String strDuration) {
        StrDuration = strDuration;
    }
}
