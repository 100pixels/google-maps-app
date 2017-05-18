package org.cenidet.cc.publictransit.volley.response.route;


import org.cenidet.cc.publictransit.volley.response.route.leg.Leg;

import java.util.Arrays;



public class Route {

    private Bound bounds;
    private OverviewPolyline overview_polyline;
    private String copyrights;
    private Leg[] legs;
    private String summary;

    public Bound getBounds() {
        return bounds;
    }

    public void setBounds(Bound bounds) {
        this.bounds = bounds;
    }

    public OverviewPolyline getOverview_polyline() {
        return overview_polyline;
    }

    public void setOverview_polyline(OverviewPolyline overview_polyline) {
        this.overview_polyline = overview_polyline;
    }

    public String getCopyrights() {
        return copyrights;
    }

    public void setCopyrights(String copyrights) {
        this.copyrights = copyrights;
    }

    public Leg[] getLegs() {
        return legs;
    }

    public void setLegs(Leg[] legs) {
        this.legs = legs;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "Route{" +
                "bounds=" + bounds +
                ", overview_polyline=" + overview_polyline +
                ", copyrights='" + copyrights + '\'' +
                ", legs=" + Arrays.toString(legs) +
                ", summary='" + summary + '\'' +
                '}';
    }
}
