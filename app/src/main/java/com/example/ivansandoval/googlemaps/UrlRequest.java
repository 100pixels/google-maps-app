package com.example.ivansandoval.googlemaps;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cenidet.cc.publictransit.dto.Stop;

public class UrlRequest {

    private String outputFormat="json";
    private String origin;
    private String destination;
    private String str_url="https://maps.googleapis.com/maps/api/directions/";


    public UrlRequest(Stop origin, Stop destination) {
        this.outputFormat = outputFormat;
        this.origin = "origin="+origin.getLatitude()+","+origin.getLongitude();
        this.destination = "destination="+destination.getLatitude()+","+destination.getLongitude();

        this.str_url+=outputFormat+"?"+this.origin+"&"+this.destination;
    }

    public String getUrlFormed(){
        return str_url;
    }

    @Override
    public String toString() {
        return str_url;
    }

    public String readResponse() {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(this.str_url);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            Log.d("downloadUrl", data.toString());
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            try{
                iStream.close();
                urlConnection.disconnect();
            }catch(Exception e){
                Log.d("Exception", e.toString());
            }
        }
        return data;
    }
}
