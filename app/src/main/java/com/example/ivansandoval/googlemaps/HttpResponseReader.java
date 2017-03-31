package com.example.ivansandoval.googlemaps;


import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;

public class HttpResponseReader extends AsyncTask<UrlRequest, Void, String>{

    private String httpResponse;
    private GoogleMap map;

    public HttpResponseReader(GoogleMap map) {
        this.map = map;
    }

    @Override
    protected String doInBackground(UrlRequest... urlRequests) {
        // For storing data from web service
        String httpResponse = "";

        try {
            // Fetching the data from web service in json format
            httpResponse = urlRequests[0].readResponse();
            Log.d("Background Task data", httpResponse.toString());
        } catch (Exception e) {
            Log.d("Background Task", e.toString());
        }
        return httpResponse;
    }

    @Override
    protected void onPostExecute(String httpResponse) {
        super.onPostExecute(httpResponse);
        //new DrectionsApiResponseParser(map).execute(httpResponse);
    }

    public String getHttpResponse() {
        return httpResponse;
    }
}
