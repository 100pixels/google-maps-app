package org.cenidet.cc.publictransit.volley.response.route.leg.step;


import app.itz.org.customvolleyrequest.response.route.Distance;
import app.itz.org.customvolleyrequest.response.route.Duration;
import app.itz.org.customvolleyrequest.response.route.Location;
import app.itz.org.customvolleyrequest.response.route.OverviewPolyline;

public class Step {

    private Distance distance;
    private Duration duration;
    private Location end_location;
    private String html_instructions;
    private String maneuver;
    private OverviewPolyline polyline;
    private Location start_location;
    private String travel_mode;

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Location getEnd_location() {
        return end_location;
    }

    public void setEnd_location(Location end_location) {
        this.end_location = end_location;
    }

    public String getHtml_instructions() {
        return html_instructions;
    }

    public void setHtml_instructions(String html_instructions) {
        this.html_instructions = html_instructions;
    }

    public String getManeuver() {
        return maneuver;
    }

    public void setManeuver(String maneuver) {
        this.maneuver = maneuver;
    }

    public OverviewPolyline getPolyline() {
        return polyline;
    }

    public void setPolyline(OverviewPolyline polyline) {
        this.polyline = polyline;
    }

    public Location getStart_location() {
        return start_location;
    }

    public void setStart_location(Location start_location) {
        this.start_location = start_location;
    }

    public String getTravel_mode() {
        return travel_mode;
    }

    public void setTravel_mode(String travel_mode) {
        this.travel_mode = travel_mode;
    }

    @Override
    public String toString() {
        return "Step{" +
                "distance=" + distance +
                ", duration=" + duration +
                ", end_location=" + end_location +
                ", html_instructions='" + html_instructions + '\'' +
                ", maneuver='" + maneuver + '\'' +
                ", polyline=" + polyline +
                ", start_location=" + start_location +
                ", travel_mode='" + travel_mode + '\'' +
                '}';
    }
}
