package org.cenidet.cc.publictransit.volley.response.route.leg;

import java.util.Arrays;

import org.cenidet.cc.publictransit.volley.response.route.*;
import org.cenidet.cc.publictransit.volley.response.route.leg.step.Step;


public class Leg {

    private Distance distance;
    private Duration duration;
    private String end_address;
    private Location end_location;
    private String start_address;
    private Location start_location;
    private Step[] steps;

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

    public String getEnd_address() {
        return end_address;
    }

    public void setEnd_address(String end_address) {
        this.end_address = end_address;
    }

    public Location getEnd_location() {
        return end_location;
    }

    public void setEnd_location(Location end_location) {
        this.end_location = end_location;
    }

    public String getStart_address() {
        return start_address;
    }

    public void setStart_address(String start_address) {
        this.start_address = start_address;
    }

    public Location getStart_location() {
        return start_location;
    }

    public void setStart_location(Location start_location) {
        this.start_location = start_location;
    }

    public Step[] getSteps() {
        return steps;
    }

    public void setSteps(Step[] steps) {
        this.steps = steps;
    }

    @Override
    public String toString() {
        return "Leg{" +
                "distance=" + distance +
                ", duration=" + duration +
                ", end_address='" + end_address + '\'' +
                ", end_location=" + end_location +
                ", start_address='" + start_address + '\'' +
                ", start_location=" + start_location +
                ", steps=" + Arrays.toString(steps) +
                '}';
    }
}
