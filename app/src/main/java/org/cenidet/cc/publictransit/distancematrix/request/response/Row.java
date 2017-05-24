package org.cenidet.cc.publictransit.distancematrix.request.response;

import java.util.ArrayList;
import java.util.Arrays;


public class Row {

    private Element[] elements;

    public Element[] getElements() {
        return elements;
    }

    public void setElements(Element[] elements) {
        this.elements = elements;
    }

    public ArrayList<Distance> getDistanceList(){
        ArrayList<Distance> distanceList = new ArrayList<Distance>();
        for(Element e : elements){
            distanceList.add(e.getDistance());
        }
        return distanceList;
    }


    @Override
    public String toString() {
        return "Row{" +
                "elements=" + Arrays.toString(elements) +
                '}';
    }
}
