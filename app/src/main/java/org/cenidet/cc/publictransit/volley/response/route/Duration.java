package org.cenidet.cc.publictransit.volley.response.route;

/**
 * Created by Ivan Sandoval on 17/05/2017.
 */

public class Duration {

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
        return "Duration{" +
                "text='" + text + '\'' +
                ", value=" + value +
                '}';
    }

}
