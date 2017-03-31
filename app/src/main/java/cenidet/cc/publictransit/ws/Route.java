package cenidet.cc.publictransit.ws;


import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Route {

    private String copyrights;
    private String polyline;
    private String summary;
    private ArrayList<Leg> legs;
    private ArrayList<LatLng> routePoints;

    public Route(JSONObject route){
        try{
            initRoute(route);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void initRoute(JSONObject route) throws JSONException {
        this.copyrights = route.getString("copyrights");
        this.summary = route.getString("summary");
        this.polyline = route.getJSONObject("overview_polyline").getString("points");
        this.routePoints = DataParser.decodePolyline(this.polyline);
        setLegs(route.getJSONArray("legs"));
    }

    public String getCopyrights() {
        return copyrights;
    }

    public void setCopyrights(String copyrights) {
        this.copyrights = copyrights;
    }

    public String getPolyline() {
        return polyline;
    }

    public void setPolyline(String polyline) {
        this.polyline = polyline;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public ArrayList<Leg> getLegs() {
        return legs;
    }

    public void setLegs(ArrayList<Leg> legs) {
        this.legs = legs;
    }

    public ArrayList<LatLng> getRoutePoints() {
        return routePoints;
    }

    public void setRoutePoints(ArrayList<LatLng> routePoints) {
        this.routePoints = routePoints;
    }

    public void setLegs(JSONArray jsonLegs) throws JSONException{
        legs = new ArrayList<Leg>();
        for(int x = 0; x < jsonLegs.length(); x++){
                JSONObject jsonLeg = (JSONObject)jsonLegs.get(0);
                legs.add(new Leg(jsonLeg));
        }
    }

    @Override
    public String toString() {
        return "Route{" +
                "copyrights='" + copyrights + '\'' +
                ", polyline='" + polyline + '\'' +
                ", summary='" + summary + '\'' +
                ", legs=" + legs +
                '}';
    }

}

