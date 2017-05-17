package com.example.ivansandoval.googlemaps;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

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

public class MyGoogleMap {

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


    public void drawPublicTransitRoute(){
        //10.175.121.113
        String url = "http://10.0.0.6:8085/PublicTransitWS/publictransit/getStopsByJourneyId/2";

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
                    Log.i(LOG_TAG, error.getMessage().toString());
                }

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
