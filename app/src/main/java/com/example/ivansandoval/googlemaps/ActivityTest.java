package com.example.ivansandoval.googlemaps;

import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.ivansandoval.googlemaps.dialogs.NetworkStatusDialog;
import com.example.ivansandoval.googlemaps.dialogs.NetworkStatusDialog.NetworkStatusDialogListener;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import cenidet.cc.publictransit.ws.DirectionsApiResponse;


public class ActivityTest extends AppCompatActivity implements NetworkStatusDialogListener{

    private final int ACTION_SETTINGS_REQUEST=1;
    //private boolean mRequestPermissionsWithResult = false;

    //private SoapObject o;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_location_settings_test);
        try{
            String response ="{"+"\"geocoded_waypoints\":["+"{"+"\"geocoder_status\":\"OK\","+"\"place_id\":\"ChIJie_rje91zoURjKVdyqBHAds\","+"\"types\":[\"street_address\"]"+"},"+"{"+"\"geocoder_status\":\"OK\","+"\"place_id\":\"Ej9HcmFsLiBJZ25hY2lvIFphcmFnb3phIDEsIENlbnRybywgNjI1NTAgSml1dGVwZWMsIE1vci4sIE3DqXhpY28\","+"\"types\":[\"street_address\"]"+"}"+"],"+"\"routes\":["+"{"+"\"bounds\":{"+"\"northeast\":{"+"\"lat\":18.8783362,"+"\"lng\":-99.17464249999999"+"},"+"\"southwest\":{"+"\"lat\":18.8776628,"+"\"lng\":-99.1772107"+"}"+"},"+"\"copyrights\":\"Datosdelmapa©2017Google,INEGI\","+"\"legs\":["+"{"+"\"distance\":{"+"\"text\":\"0.3km\","+"\"value\":293"+"},"+"\"duration\":{"+"\"text\":\"1min\","+"\"value\":82"+"},"+"\"end_address\":\"Gral.IgnacioZaragoza1,Centro,62550Jiutepec,Mor.,México\","+"\"end_location\":{"+"\"lat\":18.8777382,"+"\"lng\":-99.17464249999999"+"},"+"\"start_address\":\"Calle20deNoviembreSur31,Centro,62550Jiutepec,Mor.,México\","+"\"start_location\":{"+"\"lat\":18.8782661,"+"\"lng\":-99.1772107"+"},"+"\"steps\":["+"{"+"\"distance\":{"+"\"text\":\"8m\","+"\"value\":8"+"},"+"\"duration\":{"+"\"text\":\"1min\","+"\"value\":2"+"},"+"\"end_location\":{"+"\"lat\":18.8783362,"+"\"lng\":-99.17718889999999"+"},"+"\"html_instructions\":\"html_instructions\","+"\"polyline\":{"+"\"points\":\"edfrBppi|QMC\""+"},"+"\"start_location\":{"+"\"lat\":18.8782661,"+"\"lng\":-99.1772107"+"},"+"\"travel_mode\":\"DRIVING\""+"},"+"{"+"\"distance\":{"+"\"text\":\"0.3km\","+"\"value\":276"+"},"+"\"duration\":{"+"\"text\":\"1min\","+"\"value\":73"+"},"+"\"end_location\":{"+"\"lat\":18.8776628,"+"\"lng\":-99.17466829999999"+"},"+"\"html_instructions\":\"html_instructions\","+"\"maneuver\":\"turn-right\","+"\"polyline\":{"+"\"points\":\"sdfrBlpi|QzAmIF]N_ARkA\""+"},"+"\"start_location\":{"+"\"lat\":18.8783362,"+"\"lng\":-99.17718889999999"+"},"+"\"travel_mode\":\"DRIVING\""+"},"+"{"+"\"distance\":{"+"\"text\":\"9m\","+"\"value\":9"+"},"+"\"duration\":{"+"\"text\":\"1min\","+"\"value\":7"+"},"+"\"end_location\":{"+"\"lat\":18.8777382,"+"\"lng\":-99.17464249999999"+"},"+"\"html_instructions\":\"html_instructions\","+"\"maneuver\":\"turn-left\","+"\"polyline\":{"+"\"points\":\"k`frBt`i|QOE\""+"},"+"\"start_location\":{"+"\"lat\":18.8776628,"+"\"lng\":-99.17466829999999"+"},"+"\"travel_mode\":\"DRIVING\""+"}"+"],"+"\"traffic_speed_entry\":[],"+"\"via_waypoint\":[]"+"}"+"],"+"\"overview_polyline\":{"+"\"points\":\"edfrBppi|QMCzAmIV}ARkAOE\""+"},"+"\"summary\":\"Tejalpa-Jojutla/Tejalpa-Zacatepec\","+"\"warnings\":[],"+"\"waypoint_order\":[]"+"}"+"],"+"\"status\":\"OK\""+"}";
            DirectionsApiResponse apiResponse = new DirectionsApiResponse(response);
            ArrayList<LatLng> routePoints = apiResponse.getRoutePoints();
            System.out.println(apiResponse.toString());
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }

    private void launchActionSettingsIntent(){
        startActivityForResult(new Intent(Settings.ACTION_SETTINGS), ACTION_SETTINGS_REQUEST);
    }

    private void showToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    private void showNetworkStatusDialog(){
        new NetworkStatusDialog().show(getSupportFragmentManager(),"networkStatusDialog");
    }

    private void exitApplication(){
        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        //showToast("onDialogPositiveClick");
        launchActionSettingsIntent();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        //showToast("onDialogNegativeClick");
        exitApplication();
    }

}