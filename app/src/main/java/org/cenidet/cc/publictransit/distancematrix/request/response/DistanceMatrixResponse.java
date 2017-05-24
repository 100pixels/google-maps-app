package org.cenidet.cc.publictransit.distancematrix.request.response;


import java.util.ArrayList;
import java.util.Arrays;

public class DistanceMatrixResponse {

    private String[] destination_addresses;
    private String[] origin_addresses;
    private Row[] rows;
    private String status;


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
