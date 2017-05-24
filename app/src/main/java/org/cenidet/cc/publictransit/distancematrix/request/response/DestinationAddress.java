package org.cenidet.cc.publictransit.distancematrix.request.response;


public class DestinationAddress {

    private String destinationAddress;

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    @Override
    public String toString() {
        return "DestinationAddress{" +
                "destinationAddress='" + destinationAddress + '\'' +
                '}';
    }
}
