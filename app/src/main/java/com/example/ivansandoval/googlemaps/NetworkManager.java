package com.example.ivansandoval.googlemaps;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static android.content.Context.*;

public class NetworkManager {

    private static ConnectivityManager cm;
    private static NetworkInfo activeNetwork;

    public static boolean isDeviceConnectedOrConnecting(Context context){
        if(cm == null){
            cm =  (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

}
