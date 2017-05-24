package org.cenidet.cc.publictransit.volley.response;


import com.android.volley.VolleyError;

public interface VolleyResponseCallback {

    public void notifySuccess(String requestType, Object response);
    public void notifyError(String requestType, VolleyError error);

}
