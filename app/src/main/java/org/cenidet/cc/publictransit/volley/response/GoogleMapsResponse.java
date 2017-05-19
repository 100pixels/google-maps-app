package org.cenidet.cc.publictransit.volley.response;

import android.graphics.Color;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.cenidet.cc.publictransit.volley.response.route.Route;

import java.util.ArrayList;
import java.util.Arrays;

import cenidet.cc.publictransit.ws.DataParser;


public class GoogleMapsResponse {

    private GeocodedWaypoint[] geocoded_waypoints;
    private Route[] routes;
    private String status;

    private PolylineOptions polylineOptions;

    public GeocodedWaypoint[] getGeocoded_waypoints() {
        return geocoded_waypoints;
    }

    public void setGeocoded_waypoints(GeocodedWaypoint[] geocoded_waypoints) {
        this.geocoded_waypoints = geocoded_waypoints;
    }

    public Route[] getRoutes() {
        return routes;
    }

    public void setRoutes(Route[] routes) {
        this.routes = routes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPolyline(){
        String polyline="";
        if(routes != null && routes.length >= 1){
                polyline = routes[0].getOverview_polyline().getPoints();
        }
        return polyline;
    }

    public ArrayList<LatLng> getLanLngPoints(){
        String polyline = getPolyline();
        ArrayList<LatLng> latLngPoints = DataParser.decodePolyline(polyline);
        return latLngPoints;
    }

    public PolylineOptions getPolylineOptions(){
        polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.MAGENTA);
        polylineOptions.addAll(getLanLngPoints());
        return this.polylineOptions;
    }

    private void initPolylineOptions(){

    }

    @Override
    public String toString() {
        return "GoogleMapsResponse{" +
                "geocoded_waypoints=" + Arrays.toString(geocoded_waypoints) +
                ", routes=" + Arrays.toString(routes) +
                ", status='" + status + '\'' +
                '}';
    }

}
