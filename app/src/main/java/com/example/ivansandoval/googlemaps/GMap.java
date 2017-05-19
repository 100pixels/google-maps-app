package com.example.ivansandoval.googlemaps;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
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


public class GMap implements OnMapReadyCallback {

    private static GMap gMap;
    private GoogleMap googleMap;
    //private PolylineOptions polylineOptions;
    private CopyOnWriteArrayList<Polyline> polylines;
    //private ArrayList<LatLng> routePoints;
    private RequestQueue volleyQueue;
    //private VolleyQueue volleyQueue;
    private Gson gson;
    private final String LOG_TAG = "MyGoogleMap";

    private int idViaje;

    private GMap(Context context){
        //polylineOptions = new PolylineOptions();
        polylines = new CopyOnWriteArrayList<Polyline>();
        //routePoints = new ArrayList<LatLng>();
        //queue = Volley.newRequestQueue(context);
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

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        this.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(18.911301,  -99.181239), 14));
/*
        // Add a marker in Sydney and move the camera
        LatLng home = new LatLng(18.867056, -99.185562);
        googleMap.addMarker(new MarkerOptions().position(home).title("Marker at home"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(home));
        */
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
                    Log.i(LOG_TAG, "Error en la peticion HTTP ");
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

    private void removePolylines(){
        for(Polyline polyline: polylines){
            polyline.remove();
            polylines.remove(polyline);
        }
    }
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

                //Log.i("GoogleMapsResponse",mapsResponse.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        volleyQueue.add(customVolleyRequest);
    }

    public void addMarker(Stop stop){
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(stop.getLatitude(), stop.getLongitude()))
                .title(stop.getStopId()+".- "+ stop.getLatitude()+" , "+ stop.getLongitude()))
                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.bus_stop));
    }

}
