package cenidet.cc.publictransit.ws;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

public class CommonRouteInfo {

    protected LatLng endLocation;
    protected LatLng startLocation;
    protected int intDistance;
    protected String strDistance;
    protected int intDuration;
    protected String strDuration;

    protected void setCommonRouteInfo(JSONObject jsonObject) throws JSONException{
        JSONObject aux;

        aux = jsonObject.getJSONObject("distance");
        strDistance = aux.getString("text");
        intDistance = aux.getInt("value");

        aux = jsonObject.getJSONObject("duration");
        strDuration = aux.getString("text");
        intDuration = aux.getInt("value");

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

    public int getIntDistance() {
        return intDistance;
    }

    public void setIntDistance(int intDistance) {
        this.intDistance = intDistance;
    }

    public String getStrDistance() {
        return strDistance;
    }

    public void setStrDistance(String strDistance) {
        this.strDistance = strDistance;
    }

    public int getIntDuration() {
        return intDuration;
    }

    public void setIntDuration(int intDuration) {
        this.intDuration = intDuration;
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
                ", intDistance=" + intDistance +
                ", strDistance='" + strDistance + '\'' +
                ", intDuration=" + intDuration +
                ", strDuration='" + strDuration + '\'' +
                '}';
    }
}
