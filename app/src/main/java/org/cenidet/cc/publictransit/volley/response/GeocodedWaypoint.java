package org.cenidet.cc.publictransit.volley.response;

public class GeocodedWaypoint {

    private String geocoder_status;
    private boolean partial_match;
    private String place_id;

    public String getGeocoder_status() {
        return geocoder_status;
    }

    public void setGeocoder_status(String geocoder_status) {
        this.geocoder_status = geocoder_status;
    }

    public boolean isPartial_match() {
        return partial_match;
    }

    public void setPartial_match(boolean partial_match) {
        this.partial_match = partial_match;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    @Override
    public String toString() {
        return "GeocodedWaypoint{" +
                "geocoder_status='" + geocoder_status + '\'' +
                ", partial_match=" + partial_match +
                ", place_id='" + place_id + '\'' +
                '}';
    }
}
