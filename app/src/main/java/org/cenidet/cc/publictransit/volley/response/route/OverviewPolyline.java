package org.cenidet.cc.publictransit.volley.response.route;

/**
 * Created by Ivan Sandoval on 17/05/2017.
 */

public class OverviewPolyline {

    private String points;

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "OverviewPolyline{" +
                "points='" + points + '\'' +
                '}';
    }
}
