package org.cenidet.cc.publictransit.dao;


import android.content.Context;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.cenidet.cc.publictransit.graph.Grafo;
import org.cenidet.cc.publictransit.volley.request.VolleyService;
import org.cenidet.cc.publictransit.volley.response.VolleyResponseCallback;
import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;

import cenidet.cc.publictransit.dto.Stop;
import static android.R.attr.type;

public class GraphDAO {

    private VolleyService volleyService;
    private Context context;
    private Gson gson;

    public GraphDAO(Context context) {
        this.context = context;
        this.gson = new Gson();
    }

    public Grafo getVerticesGrafo(){

        /*
        String url = "http://10.0.0.6:8085/PublicTransitWS/db/paradas/grafo";

        VolleyResponseCallback volleyResponseCallback = new VolleyResponseCallback() {
            @Override
            public void notifySuccess(String requestType, Object response) {
                JSONArray jsonArray = (JSONArray)response;
                Type type = new TypeToken<ArrayList<Stop>>() {}.getType();
                ArrayList<Stop> stops = gson.fromJson(jsonArray.toString(), type);
            }
            @Override
            public void notifyError(String requestType, VolleyError error) {

            }
        }
        //*/

        /*
        Type type = new TypeToken<ArrayList<Stop>>() {}.getType();
        ArrayList<Stop> stops = gson.fromJson(response, type);
        //*/
        return null;
    }

}
