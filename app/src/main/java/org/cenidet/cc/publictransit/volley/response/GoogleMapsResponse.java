package org.cenidet.cc.publictransit.volley.response;

import org.cenidet.cc.publictransit.volley.response.route.Route;

import java.util.Arrays;



public class GoogleMapsResponse {

    private GeocodedWaypoint[] geocoded_waypoints;
    private Route[] routes;
    private String status;

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

    @Override
    public String toString() {
        return "GoogleMapsResponse{" +
                "geocoded_waypoints=" + Arrays.toString(geocoded_waypoints) +
                ", routes=" + Arrays.toString(routes) +
                ", status='" + status + '\'' +
                '}';
    }
}
