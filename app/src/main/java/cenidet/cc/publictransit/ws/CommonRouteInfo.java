package cenidet.cc.publictransit.ws;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

public class CommonRouteInfo {

    protected LatLng endLocation;
    protected LatLng startLocation;
    protected double distanceKm;
    protected String strDistance;
    protected double durationMin;
    protected String strDuration;

    protected void setCommonRouteInfo(JSONObject jsonObject) throws JSONException{
        JSONObject aux;

        aux = jsonObject.getJSONObject("distance");
        strDistance = aux.getString("text");
        distanceKm = aux.getInt("value");

        aux = jsonObject.getJSONObject("duration");
        strDuration = aux.getString("text");
        durationMin = aux.getInt("value");

        aux = jsonObject.getJSONObject("start_location");
        startLocation = new LatLng(aux.getDouble("lat"), aux.getDouble("lng"));

        aux = jsonObject.getJSONObject("end_location");
        endLocation = new LatLng(aux.getDouble("lat"), aux.getDouble("lng"));
    }

    public LatLng getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(LatLng endLocation) {
        this.endLocation = endLocation;
    }

    public LatLng getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(LatLng startLocation) {
        this.startLocation = startLocation;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(double distanceKm) {
        this.distanceKm = distanceKm;
    }

    public String getStrDistance() {
        return strDistance;
    }

    public void setStrDistance(String strDistance) {
        this.strDistance = strDistance;
    }

    public double getDurationMin() {
        return durationMin;
    }

    public void setDurationMin(double durationMin) {
        this.durationMin = durationMin;
    }

    public String getStrDuration() {
        return strDuration;
    }

    public void setStrDuration(String strDuration) {
        this.strDuration = strDuration;
    }

    @Override
    public String toString() {
        return "CommonRouteInfo{" +
                "endLocation=" + endLocation +
                ", startLocation=" + startLocation +
                ", intDistance=" + distanceKm +
                ", strDistance='" + strDistance + '\'' +
                ", intDuration=" + durationMin +
                ", strDuration='" + strDuration + '\'' +
                '}';
    }
}
