package cenidet.cc.publictransit.ws;


import android.graphics.Color;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DirectionsApiResponse {

    private ArrayList<Route> routes;
    private String status;
    private PolylineOptions polylineOptions;

    public DirectionsApiResponse(String response) {
        polylineOptions = new PolylineOptions();
        polylineOptions.width(10);
        polylineOptions.color(Color.GREEN);

        try{
            JSONObject jsonResponse = new JSONObject(response);
            initDirectionsApiResponse(jsonResponse);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void initDirectionsApiResponse(JSONObject jsonResponse) throws JSONException{
            status = jsonResponse.getString("status");
            setRoutes(jsonResponse.getJSONArray("routes"));
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(ArrayList<Route> routes) {
        this.routes = routes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRoutes(JSONArray jsonRoutes) throws JSONException{
        routes = new ArrayList<Route>();
        for(int x = 0; x < jsonRoutes.length(); x++){
            JSONObject jsonRoute = (JSONObject)jsonRoutes.get(0);
            routes.add(new Route(jsonRoute));
        }
    }

    public ArrayList<LatLng> getRoutePoints(){
        return getRoutes().get(0).getRoutePoints();
    }

    public PolylineOptions getPolyline(){
        polylineOptions.addAll(getRoutePoints());
        return polylineOptions;
    }

    @Override
    public String toString() {
        return "DirectionsApiResponse{" +
                "routes=" + routes +
                ", status='" + status + '\'' +
                '}';
    }

}
