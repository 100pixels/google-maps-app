package org.cenidet.cc.publictransit.distancematrix.request.request;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import cenidet.cc.publictransit.dto.Stop;

public class DistanceMatrixRequest {

    private static String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=";
    private static String mode = "&mode=walking";

    public static String getUrl(LatLng location, ArrayList<Stop>stops){
        String origins = location.latitude +","+location.longitude;
        String destinations = "&destinations=";

        for(int x = 0; x < stops.size(); x++){
            Stop stop = stops.get(x);
            destinations += stop.getLatitude()+","+stop.getLongitude();
            if(x != stops.size() -1){
                destinations+= "|";
            }
        }
        return url+ origins+destinations+mode;
    }


}
