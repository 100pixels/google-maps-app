package org.cenidet.cc.publictransit.volley.request;


import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.cenidet.cc.publictransit.volley.VolleyQueue;
import org.cenidet.cc.publictransit.volley.response.GoogleMapsResponse;
import org.cenidet.cc.publictransit.volley.response.VolleyResponseCallback;
import org.json.JSONArray;
import org.json.JSONObject;




public class VolleyService {

    public static final String GET_REQUEST = "GET";
    public static final String POST_REQUEST = "POST";

    private VolleyResponseCallback volleyResponseCallback;
    private RequestQueue requestQueue;
    private Context context;

    public VolleyService(VolleyResponseCallback volleyResponseCallback, Context context) {
        this.volleyResponseCallback = volleyResponseCallback;
        this.context = context;
        requestQueue = VolleyQueue.getInstance(context).getRequestQueue();
    }

    public void makePostRequest(final String requestType, String url,JSONObject sendObj) throws Exception{
            CustomVolleyRequest gsonRequest = new CustomVolleyRequest(url, GoogleMapsResponse.class, null, new Response.Listener() {
                @Override
                public void onResponse(Object response) {
                    if(volleyResponseCallback != null)
                        volleyResponseCallback.notifySuccess(requestType,response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(volleyResponseCallback != null)
                        volleyResponseCallback.notifyError(requestType,error);
                }
            });
            requestQueue.add(gsonRequest);
    }

    public void makeGetRequest(final String requestType, String url, Class clazz){
        CustomVolleyRequest gsonRequest = new CustomVolleyRequest(url, clazz, null, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                if(volleyResponseCallback != null)
                    volleyResponseCallback.notifySuccess(requestType,response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(volleyResponseCallback != null)
                    volleyResponseCallback.notifyError(requestType,error);
            }
        });
        requestQueue.add(gsonRequest);
    }

    public void makeJSONArrayRequest(final String requestType, String url){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        if(volleyResponseCallback != null)
                            volleyResponseCallback.notifySuccess(requestType,jsonArray);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if(volleyResponseCallback != null)
                            volleyResponseCallback.notifyError(requestType,volleyError);
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }



}
