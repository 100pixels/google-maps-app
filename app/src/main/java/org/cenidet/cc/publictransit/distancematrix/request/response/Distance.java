package org.cenidet.cc.publictransit.distancematrix.request.response;


public class Distance implements Comparable<Distance>{

    private String text;
    private double value;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Distance{" +
                "text='" + text + '\'' +
                ", value=" + value +
                '}';
    }

    @Override
    public int compareTo(Distance param) {
        if(this.value > param.getValue()){
            return 1;
        }else if(this.value < param.getValue()){
            return -1;
        }else{
            return 0;
        }
    }

}
