package com.example.ivansandoval.googlemaps;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by Ivan Sandoval on 05/02/2017.
 */

public class NetworkState {

    private static ConnectivityManager conectivityManager;
    private static Context ctx;

    public static boolean isConnected(Context context){
        ctx=context;
        Log.d("STATE","ctx == null : " + (ctx == null) );
        conectivityManager = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        Log.d("STATE","conectivityManager == null : " + (conectivityManager == null) );
        NetworkInfo activeNetwork = conectivityManager.getActiveNetworkInfo();
        Log.d("STATE","activeNetwork == null : " + (activeNetwork == null) );
        //return activeNetwork.isConnectedOrConnecting();
        return activeNetwork != null;
    }

}
