package org.cenidet.cc.publictransit.distancematrix.request.response;


public class DistanceMatrixObject implements Comparable<DistanceMatrixObject>{

    private String originAddress;
    private String destinationAddress;
    private Element element;

    public String getOriginAddress() {
        return originAddress;
    }

    public void setOriginAddress(String originAddress) {
        this.originAddress = originAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    @Override
    public String toString() {

        String obj = "origin: "+ originAddress + "\n"+
                "destination: "+ destinationAddress + "\n"+
                "distance: " + element.getDistance()+"\n"+
                "duration: "+ element.getDuration()+"\n";

        return obj;
    }

    public double getDistanceValue(){
        return element.getDistanceValue();
    }

    @Override
    public int compareTo(DistanceMatrixObject param) {
        if(this.getDistanceValue() > param.getDistanceValue()){
            return 1;
        }else if(this.getDistanceValue() < param.getDistanceValue()){
            return -1;
        }else{
            return 0;
        }
    }
}
