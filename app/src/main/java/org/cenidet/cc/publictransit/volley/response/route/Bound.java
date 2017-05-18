package org.cenidet.cc.publictransit.volley.response.route;

/**
 * Created by Ivan Sandoval on 17/05/2017.
 */

public class Bound {

    private Location northeast;
    private Location southwest;

    public Location getNortheast() {
        return northeast;
    }

    public void setNortheast(Location northeast) {
        this.northeast = northeast;
    }

    public Location getSouthwest() {
        return southwest;
    }

    public void setSouthwest(Location southwest) {
        this.southwest = southwest;
    }

    @Override
    public String toString() {
        return "Bound{" +
                "northeast=" + northeast +
                ", southwest=" + southwest +
                '}';
    }
}
