package org.cenidet.cc.publictransit.classes.map;

import android.content.Context;
import android.graphics.Camera;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ivansandoval.googlemaps.R;
import com.example.ivansandoval.googlemaps.UrlRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.cenidet.cc.publictransit.volley.VolleyQueue;
import org.cenidet.cc.publictransit.volley.request.CustomVolleyRequest;
import org.cenidet.cc.publictransit.volley.response.GoogleMapsResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import cenidet.cc.publictransit.dto.Stop;
import cenidet.cc.publictransit.ws.DataParser;


public class GMap implements OnMapReadyCallback {

    private static GMap gMap;
    private GoogleMap googleMap;
    private CopyOnWriteArrayList<Polyline> polylines;
    private RequestQueue volleyQueue;
    private Gson gson;
    private final String LOG_TAG = "MyGoogleMap";

    private int idViaje;

    private GMap(Context context){
        polylines = new CopyOnWriteArrayList<Polyline>();
        volleyQueue = VolleyQueue.getInstance(context).getRequestQueue();
        gson = new Gson();
    }

    public static GMap getInstance(Context context){
        if(gMap == null){
            gMap = new GMap(context);
        }
        return gMap;
    }

    public void init(FragmentManager fragmentManager){
        SupportMapFragment mapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void init(MapView mapView){
        mapView.getMapAsync(this);
    }

    public void addMarker(Stop stop){
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(stop.getLatitude(), stop.getLongitude()))
                .title(stop.getId()+".- "+ stop.getLatitude()+" , "+ stop.getLongitude()))
                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.bus_stop));
    }

    public void clear(){
        googleMap.clear();
    }

    public void addMarker(LatLng location/*, String description*/){
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(location.latitude, location.longitude)))
                //.title(description))
                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.bus_stop));
    }

    public void addMarker(LatLng location, float color){

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(location.latitude, location.longitude));
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(color));

        googleMap.addMarker(markerOptions);
    }

    public void setCameraBounds(LatLng southwest, LatLng northwest){
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(southwest);
        builder.include(northwest);
        LatLngBounds bounds = builder.build();
        int padding = 100;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        googleMap.animateCamera(cameraUpdate);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        this.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(18.911301,  -99.181239), ZOOM.STREET_LEVEL));
    }

    public void drawPublicTransitRoute(int idViaje){
        //10.175.121.113
        this.idViaje = idViaje;
        String url = "http://10.0.0.6:8085/PublicTransitWS/db/paradas/paradasByIdViaje/"+idViaje;

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
                if(error == null){
                    Log.i(LOG_TAG, "Ocurrio un problema con la red");
                }else{
                    Log.i(LOG_TAG, "Error en la peticion HTTP: "+ error.getMessage());
                }

            }
        });
        volleyQueue.add(stringRequest);
    }


    public void drawAllPublicTransitRoute(){
        //10.175.121.113
        String url = "http://10.175.121.109:8085/PublicTransitWS/publictransit/getStopsByJourneyId";

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
                if(error == null){
                    Log.i(LOG_TAG, "Ocurrio un problema con la red");
                }else{
                    Log.i(LOG_TAG, error.toString());
                }

            }
        });
        volleyQueue.add(stringRequest);
    }
    //*/

    // Consume web service de google

    public void drawRoute(ArrayList<Stop> stops){
        PolylineOptions polylineOptions = new PolylineOptions();
        for(int x = 0; x< stops.size(); x++){
            Stop stop = stops.get(x);
            if(x != stops.size() -1){
                drawRouteBetweenTwoStops(stops.get(x), stops.get(x+1));
            }
            addMarker(stop);
        }
    }
    //*/

    // Consulta la BD
/*
    public void drawRoute(ArrayList<Stop> stops){
        PolylineOptions polylineOptions = new PolylineOptions();
        for(int x = 0; x< stops.size(); x++){
            Stop stop = stops.get(x);
            if(x != stops.size() -1){
                ArrayList<LatLng> routePoints = DataParser.decodePolyline(stop.getPolyline());
                googleMap.addPolyline(polylineOptions.addAll(routePoints));
            }
            addMarker(stop);
        }
    }
    ///*

    //*/

    private void drawRouteBetweenTwoStops(Stop stop1, Stop stop2){

        UrlRequest urlRequest = new UrlRequest(stop1, stop2);
        //ArrayList<LatLng> puntos;

        CustomVolleyRequest customVolleyRequest = new CustomVolleyRequest(urlRequest.toString(), GoogleMapsResponse.class, null, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                GoogleMapsResponse mapsResponse = (GoogleMapsResponse)response;
                PolylineOptions polylineOptions = mapsResponse.getPolylineOptions();
                googleMap.addPolyline(polylineOptions);

                Log.i("GoogleMapsResponse",mapsResponse.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("GoogleMapsResponse", "An erros has occured while making the request");
            }
        });
        volleyQueue.add(customVolleyRequest);
    }

    public static class ZOOM{
        public static final int WORLD_LEVEL = 1;
        public static final int CONTINENT_LEVEL = 5;
        public static final int CITY_LEVEL = 10;
        public static final int STREET_LEVEL = 15;
        public static final int BUILDING_LEVEL = 20;
    }

}
