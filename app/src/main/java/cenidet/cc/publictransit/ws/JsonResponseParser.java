package cenidet.cc.publictransit.ws;


        import android.graphics.Color;
        import android.os.AsyncTask;

        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.PolylineOptions;

        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;


public class JsonResponseParser extends AsyncTask<String, Integer, List<List<HashMap<String,String>>>> {

    private GoogleMap map;

    public JsonResponseParser(GoogleMap map) {
        this.map = map;
    }

    @Override
    protected List<List<HashMap<String,String>>> doInBackground(String... httpResponse){
        List<List<HashMap<String,String>>> routes=null;
        try{
            routes=new DataParser().parse(new JSONObject(httpResponse[0]));
        }catch(Exception e){
            e.printStackTrace();
        }
        return routes;
    }

    @Override
    protected void onPostExecute(List<List<HashMap<String,String>>> routes){
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

}