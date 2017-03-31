package cenidet.cc.publictransit.ws;


import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Step extends CommonRouteInfo{

    private String htmlInstructions;
    private String polyline;
    private ArrayList<LatLng> stepPolyline;
    private String travelMode;


    public Step(JSONObject jsonStep){
        try{
            initStep(jsonStep);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void initStep(JSONObject step) throws JSONException{
        setCommonRouteInfo(step);
        htmlInstructions = step.getString("html_instructions");
        travelMode = step.getString("travel_mode");
        polyline = step.getJSONObject("polyline").getString("points");
        stepPolyline = DataParser.decodePolyline(polyline);
    }

    public String getHtmlInstructions() {
        return htmlInstructions;
    }

    public void setHtmlInstructions(String htmlInstructions) {
        this.htmlInstructions = htmlInstructions;
    }

    public String getPolyline() {
        return polyline;
    }

    public void setPolyline(String polyline) {
        this.polyline = polyline;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

    public ArrayList<LatLng> getStepPoints() {
        return stepPolyline;
    }

    public void setStepPolyline(ArrayList<LatLng> stepPolyline) {
        this.stepPolyline = stepPolyline;
    }

    @Override
    public String toString() {
        return "Step{" +
                "htmlInstructions='" + htmlInstructions + '\'' +
                ", polyline='" + polyline + '\'' +
                ", travelMode='" + travelMode + '\'' +
                '}';
    }
}
