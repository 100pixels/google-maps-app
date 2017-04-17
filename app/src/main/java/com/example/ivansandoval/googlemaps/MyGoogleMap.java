package com.example.ivansandoval.googlemaps;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.reflect.Type;
import java.util.ArrayList;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

import cenidet.cc.publictransit.dto.Stop;
import cenidet.cc.publictransit.ws.DirectionsApiResponse;
import cenidet.cc.publictransit.ws.FetchUrl;
import cenidet.cc.publictransit.ws.JsonResponseParser;

public class MyGoogleMap implements
                                                        OnMapReadyCallback, LocationListener
                                                        /*GoogleMap.OnMapClickListener*/{

    private boolean isTheFirstTimeLocationIsRequested=true;
    private Context context;
    private GoogleMap map;
    private ArrayList<LatLng> positions;
    private Marker currentLocationMarker;
    private UrlRequest urlRequest;
    private RequestQueue queue;
    private Gson gson;
    private final String LOG_TAG = "MyGoogleMap";

    public MyGoogleMap(Context context) {
        this.context = context;
        positions=new ArrayList<LatLng>();
        queue = Volley.newRequestQueue(context);
        gson = new Gson();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(LOG_TAG,"on onMapReady()");
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //map.setOnMapClickListener(this);

        if (ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(LOG_TAG,"lat: "+location.getLatitude()+" ,  lng: "+ location.getLongitude());
        if(isTheFirstTimeLocationIsRequested){
            LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
            map.animateCamera(CameraUpdateFactory.zoomTo(11));
            isTheFirstTimeLocationIsRequested=false;
        }
        //displayCurrentLocation(location);
    }

    private void displayCurrentLocation(Location lastLocation){

        if (currentLocationMarker != null) {
            currentLocationMarker.remove();
        }

        LatLng currentLocation = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLocation);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        currentLocationMarker = map.addMarker(markerOptions);

        //move map camera
        map.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        map.animateCamera(CameraUpdateFactory.zoomTo(11));

        //stop location updates
        /*
        if (apiClient.getClient() != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(apiClient.getClient(), this);
            Log.d(LOG_TAG,"Location updates have been canceled");
        }
        */
    }


    public void drawPublicTransitRoute(){
        //10.175.121.113
        String url = "http://10.0.0.6:8085/PublicTransitWS/publictransit/getStopsByJourneyId/3";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                Type type = new TypeToken<ArrayList<Stop>>() {}.getType();
                ArrayList<Stop> stops = gson.fromJson(response, type);
                drawRoute(stops);
                Log.i(LOG_TAG, ""+stops);
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Log.i(LOG_TAG, error.getMessage().toString());
            }
        });
        queue.add(stringRequest);
    }

    public void drawRoute(ArrayList<Stop> stops){
        for(int x=0; x< stops.size(); x++){
            if( x != stops.size() - 1 ){
                drawRouteBetweenTwoStops(stops.get(x), stops.get(x+1));
            }
            drawMarker(stops.get(x));
        }
    }

    private void drawRouteBetweenTwoStops(Stop stop1, Stop stop2) {
        UrlRequest urlRequest = new UrlRequest(stop1, stop2);

        //FetchUrl fetchUrl = new FetchUrl(map);
        //fetchUrl.execute(urlRequest.toString());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlRequest.toString(), new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                DirectionsApiResponse apiResponse = new DirectionsApiResponse(response);
                map.addPolyline(apiResponse.getPolyline());
                //Log.i(LOG_TAG, apiResponse.toString());
                //new JsonResponseParser(map).execute(response);
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                //lblResopnse.setText(error.getMessage());
                Log.e(LOG_TAG, error.getMessage());
            }
        });
        queue.add(stringRequest);
    }


    public void drawMarker(Stop stop){
        map.addMarker(new MarkerOptions()
                .position(new LatLng(stop.getLatitude(), stop.getLongitude()))
                .title(stop.getStopId()+".- "+ stop.getLatitude()+" , "+ stop.getLongitude()));
    }
}

    /*
    @Override
    public void onMapClick(LatLng position) {
            if(positions.size() == 2){
                positions.clear();
                map.clear();
            }

        MarkerOptions marker=new MarkerOptions();
        positions.add(position);
        marker.position(position);

            if(positions.size() ==1){
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                map.addMarker(marker);
            }else if(positions.size() ==2){
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                map.addMarker(marker);

                // Aqui se sebe trazar la ruta una
                //LatLng origin=positions.get(0);
                //LatLng destination=positions.get(1);

                urlRequest=new UrlRequest(positions.get(0),positions.get(1));
                new HttpResponseReader(map).execute(urlRequest);

                map.moveCamera(CameraUpdateFactory.newLatLng(positions.get(0)));
                map.animateCamera(CameraUpdateFactory.zoomTo(11));

                Log.i(LOG_TAG,urlRequest.toString());

                //Log.i(LOG_TAG,origin.latitude+","+origin.longitude);
                //Log.i(LOG_TAG,destination.latitude+","+destination.longitude);
            }
    }
*/