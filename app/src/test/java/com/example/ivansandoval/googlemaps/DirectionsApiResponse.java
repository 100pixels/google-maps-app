package com.example.ivansandoval.googlemaps;


import java.util.List;

public class DirectionsApiResponse {

    private String response;
    private List<Route> routes;
    private String status;

    public DirectionsApiResponse(String response) {
        this.response = response;
    }

    public List<Route> getRoute() {
        return routes;
    }

    public void setRoute(List<Route> routes) {
        this.routes = routes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
