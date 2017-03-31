package cenidet.cc.publictransit.ws;

import android.graphics.Color;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DrectionsApiResponseParser {

    private GoogleMap map;
    private DirectionsApiResponse apiResponse;
    private String jsonResponse;

    public DrectionsApiResponseParser(DirectionsApiResponse apiResponse, String jsonResponse) {
        this.apiResponse = apiResponse;
        this.jsonResponse = jsonResponse;

        try{
            MyMethodParser(this.jsonResponse);
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

    public DrectionsApiResponseParser(GoogleMap map) {
        this.map = map;
    }


    private List<List<HashMap<String,String>>> doInBackground(String... httpResponse){
        List<List<HashMap<String,String>>> routes=null;
        try{
            routes=parse(new JSONObject(httpResponse[0]));
        }catch(Exception e){
            e.printStackTrace();
        }
        return routes;
    }

    private void onPostExecute(List<List<HashMap<String,String>>> routes){
        ArrayList<LatLng> points;
        PolylineOptions polyline=null;

    for(int x = 0; x < routes.size(); x++){
        points = new ArrayList<>();
        polyline = new PolylineOptions();

        List<HashMap<String,String>> path = routes.get(x);

        for(int y = 0; y < path.size(); y++){
            HashMap<String,String> point = path.get(y);
            double lat = Double.parseDouble(point.get("lat"));
            double lng = Double.parseDouble(point.get("lng"));

            points.add(new LatLng(lat,lng));
        }

        polyline.addAll(points);
        polyline.width(10);
        polyline.color(Color.RED);
    }

        if(polyline != null){
            map.addPolyline(polyline);
        }

    }

    /** Receives a JSONObject and returns a list of lists containing latitude and longitude */
    public List<List<HashMap<String,String>>> parse(JSONObject jObject){

        List<List<HashMap<String, String>>> routes = new ArrayList<>() ;
        JSONArray jRoutes;
        JSONArray jLegs;
        JSONArray jSteps;

        try {

            jRoutes = jObject.getJSONArray("routes");

            /** Traversing all routes */
            for(int i=0;i<jRoutes.length();i++){

                JSONObject routeElements = ((JSONObject)jRoutes.get(i));

                String copyrights = routeElements.getString("copyrights");
                String summary = routeElements.getString("summary");
                //String polyline = routeElements.
                jLegs = routeElements.getJSONArray("legs");

                List path = new ArrayList<>();

                /** Traversing all legs */
                for(int j=0;j<jLegs.length();j++){
                    jSteps = ( (JSONObject)jLegs.get(j)).getJSONArray("steps");

                    /** Traversing all steps */
                    for(int k=0;k<jSteps.length();k++){
                        String polyline = "";
                        polyline = (String)((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                        List<LatLng> list = decodePoly(polyline);

                        /*Traversing all points */
                        for(int l=0;l<list.size();l++){
                            HashMap<String, String> hm = new HashMap<>();
                            hm.put("lat", Double.toString((list.get(l)).latitude) );
                            hm.put("lng", Double.toString((list.get(l)).longitude) );
                            path.add(hm);
                        }
                    }
                    routes.add(path);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return routes;
    }

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    private void MyMethodParser(String jsonResponse) throws JSONException{

        JSONObject distance;
        String strDistance;
        int intDistance;

        JSONObject duration;
        String strDuration;
        int intDuration;

        JSONObject startLocation;
        double startLocationLat;
        double startLocationLng;

        JSONObject endLocation;
        double endLocationLat;
        double endLocationLng;

        String points;

        JSONObject response = new JSONObject(jsonResponse);
        JSONArray routes = response.getJSONArray("routes");
        String status = response.getString("status");

        for (int x = 0 ; x < routes.length(); x++){

            JSONObject routeElements = (JSONObject)routes.get(x);

            String copyrights = routeElements.getString("copyrights");
            String summary = routeElements.getString("summary");
            points = routeElements.getJSONObject("overview_polyline").getString("points");

            JSONArray legs = routeElements.getJSONArray("legs");

            for (int y = 0 ; y < legs.length(); y++){

                JSONObject legsElements = (JSONObject)legs.get(0);

                distance = legsElements.getJSONObject("distance");
                strDistance = distance.getString("text");
                intDistance = distance.getInt("value");

                duration = legsElements.getJSONObject("duration");
                strDuration= duration.getString("text");
                intDuration = duration.getInt("value");

                String startAddress = legsElements.getString("start_address");
                String endAddress = legsElements.getString("end_address");

                startLocation = legsElements.getJSONObject("start_location");
                startLocationLat = startLocation.getDouble("lat");
                startLocationLng = startLocation.getDouble("lng");

                endLocation = legsElements.getJSONObject("end_location");
                endLocationLat = startLocation.getDouble("lat");
                endLocationLng = startLocation.getDouble("lng");

                JSONArray steps = legsElements.getJSONArray("steps");

                    for (int z = 0 ; z < steps.length(); z++){

                        JSONObject stepElements = (JSONObject)steps.get(0);

                        distance = stepElements.getJSONObject("distance");
                        strDistance = distance.getString("text");
                        intDistance = distance.getInt("value");

                        duration = stepElements.getJSONObject("duration");
                        strDuration= duration.getString("text");
                        intDuration = duration.getInt("value");

                        startLocation = stepElements.getJSONObject("start_location");
                        startLocationLat = startLocation.getDouble("lat");
                        startLocationLng = startLocation.getDouble("lng");

                        endLocation = stepElements.getJSONObject("end_location");
                        endLocationLat = startLocation.getDouble("lat");
                        endLocationLng = startLocation.getDouble("lng");

                        String htmlInstructions = stepElements.getString("html_instructions");
                        String travelMode = stepElements.getString("travel_mode");

                        points = stepElements.getJSONObject("polyline").getString("points");

                    }

            }

        }

    }


}
