package org.cenidet.cc.publictransit.distancematrix.request.response;


import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DistanceMatrixResponse {

    private String[] destination_addresses;
    private String[] origin_addresses;
    private Row[] rows;
    private String status;

    //private ArrayList<DistanceMatrixObject> distanceMatrixObjects;


    public String[] getDestination_addresses() {
        return destination_addresses;
    }

    public void setDestination_addresses(String[] destination_addresses) {
        this.destination_addresses = destination_addresses;
    }

    public String[] getOrigin_addresses() {
        return origin_addresses;
    }

    public void setOrigin_addresses(String[] origin_addresses) {
        this.origin_addresses = origin_addresses;
    }

    public Row[] getRows() {
        return rows;
    }

    public void setRows(Row[] rows) {
        this.rows = rows;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Distance> getDistanceList(){
        return rows[0].getDistanceList();
    }

    public ArrayList<DistanceMatrixObject> convertResponseToDistanceMatrixObjects(){
        ArrayList<DistanceMatrixObject>  distanceMatrixObjects = new ArrayList<DistanceMatrixObject>();
        Element[] elements = rows[0].getElements();

        for(int x = 0; x < origin_addresses.length; x++){
            String originAddres = origin_addresses[x];
            for(int y = 0; y < destination_addresses.length; y++){
                String destinationAddres = destination_addresses[y];
                Element element = elements[y];

                DistanceMatrixObject object = new DistanceMatrixObject();
                object.setOriginAddress(originAddres);
                object.setDestinationAddress(destinationAddres);
                object.setElement(element);

                distanceMatrixObjects.add(object);

            }
        }
            return distanceMatrixObjects;
    }

    public DistanceMatrixObject getNearestLocationFrom(LatLng location){
        ArrayList<DistanceMatrixObject>  distanceMatrixObjects = convertResponseToDistanceMatrixObjects();
        return Collections.min(distanceMatrixObjects);
    }

    @Override
    public String toString() {
        return "DistanceMatrixResponse{" +
                "destination_addresses=" + Arrays.toString(destination_addresses) +
                ", origin_addresses=" + Arrays.toString(origin_addresses) +
                ", rows=" + Arrays.toString(rows) +
                ", status='" + status + '\'' +
                '}';
    }

}
