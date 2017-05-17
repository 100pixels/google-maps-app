package com.example.ivansandoval.googlemaps;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.ivansandoval.googlemaps.dialogs.NetworkStatusDialog;
import com.example.ivansandoval.googlemaps.dialogs.NetworkStatusDialog.NetworkStatusDialogListener;
import com.google.android.gms.awareness.snapshot.LocationResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;


public class MapsActivity extends FragmentActivity
                                                                        implements NetworkStatusDialogListener,
                                                                                                LocationListener{

    private final String LOG_TAG = "MapsActivity";

    private final int REQUEST_ACCESS_FINE_LOCATION_PERMISSION= 10;
    private final int REQUEST_ACTION_SETTINGS=30;
    private final int REQUEST_CHECK_SETTINGS = 20;

    // New attributes added
    private Location lastLocation;
    private GApiClient gApiClient;
    private GoogleApiClient mGoogleApiClient;
    private GMap gMap;
    private int idViaje;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        idViaje = getIntent().getIntExtra("idViaje", 0);

        gMap = GMap.getInstance(this);
        gMap.init(getSupportFragmentManager());

        gApiClient = GApiClient.getInstance();
        mGoogleApiClient = gApiClient.initApiClient(this, this);

        if(NetworkManager.isDeviceConnectedOrConnecting(this)){
            requestAccessFineLocationPermission();
        }else{
            showNetworkStatusDialog();
        }
    }

    private void checkLocationSettings(){
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(gApiClient.getLocationRequest());

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,
                        builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {
                final Status status = locationSettingsResult.getStatus();
                int statusCode=status.getStatusCode();
                Log.i(LOG_TAG, "status.getStatusCode()="+statusCode);
                switch (statusCode) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i(LOG_TAG, "Configuración correcta, es posible hacer la solicitud");
                        //gMap.drawPublicTransitRoute(idViaje);
                        drawRoute();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            Log.i(LOG_TAG, "Se requiere actuación del usuario");
                            // Despliega el cuadro de dialogo para activar el servicio de ubicación
                            status.startResolutionForResult( MapsActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(LOG_TAG, "Error al intentar solucionar configuración de ubicación");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(LOG_TAG, "No se puede cumplir la configuración de ubicación necesaria");
                        //btnActualizar.setChecked(false);
                        break;
                }
            }
        });
    }

    private void drawRoute(){
        if(idViaje == 0){
            gMap.drawAllPublicTransitRoute();
        }else{
            gMap.drawPublicTransitRoute(idViaje);
        }
    }

    private void exitApplication(){
        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void launchActionSettingsIntent(){
        startActivityForResult(new Intent(Settings.ACTION_SETTINGS), REQUEST_ACTION_SETTINGS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case REQUEST_CHECK_SETTINGS:
                if(resultCode == Activity.RESULT_OK){
                    //gMap.drawPublicTransitRoute(idViaje);
                    drawRoute();
                }else{
                    exitApplication();
                }
                break;
            case REQUEST_ACTION_SETTINGS:
                requestAccessFineLocationPermission();
                break;
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        exitApplication();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        launchActionSettingsIntent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mGoogleApiClient != null){
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mGoogleApiClient != null){
            mGoogleApiClient .disconnect();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(LOG_TAG,"on onRequestPermissionsResult()");
        if(requestCode == REQUEST_ACCESS_FINE_LOCATION_PERMISSION){
            if(grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Location permission granted", Toast.LENGTH_LONG).show();
                checkLocationSettings();
            }else{
                exitApplication();
            }
        }
    }


    public void requestAccessFineLocationPermission(){
        Log.d(LOG_TAG,"on requestAccessFineLocationPermission()");
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            checkLocationSettings();
        } else{
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_ACCESS_FINE_LOCATION_PERMISSION);
        }
    }

    private void showNetworkStatusDialog(){
        new NetworkStatusDialog().show(getSupportFragmentManager(),"networkStatusDialog");
    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
        Log.i(LOG_TAG, "lat= "+ lastLocation.getLatitude()+" , "+ "lng= "+ lastLocation.getLongitude());
    }

    /*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    */

    /*
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        gMap.clear();
        //this.finish();
    }*/

}