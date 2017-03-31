package cenidet.cc.publictransit.ws;


import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Leg extends CommonRouteInfo{

    private String startAddress;
    private String endAddress;
    private ArrayList<Step> steps;

    public Leg(JSONObject legs){
        try{
            initLegs(legs);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    private void initLegs(JSONObject leg) throws JSONException{
        setCommonRouteInfo(leg);
        startAddress = leg.getString("start_address");
        endAddress = leg.getString("end_address");
        setSteps(leg.getJSONArray("steps"));
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }

    public void setSteps(JSONArray jsonSteps) throws JSONException{
        steps = new ArrayList<Step>();
        for(int x = 0; x < jsonSteps.length(); x++){
            JSONObject jsonStep = (JSONObject)jsonSteps.get(0);
            steps.add(new Step(jsonStep));
        }
    }

    @Override
    public String toString() {
        return "Leg{" +
                "startAddress='" + startAddress + '\'' +
                ", endAddress='" + endAddress + '\'' +
                ", steps=" + steps +
                '}';
    }

}
