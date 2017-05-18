package org.cenidet.cc.publictransit.volley;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyQueue {

    private static VolleyQueue instance;
    private static Context context;
    private RequestQueue requestQueue;

    private VolleyQueue(Context context){
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized  VolleyQueue getInstance(Context context){
        if(instance == null){
                instance = new VolleyQueue(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addRequest(Request<T> request){
        getRequestQueue().add(request);
    }


}
