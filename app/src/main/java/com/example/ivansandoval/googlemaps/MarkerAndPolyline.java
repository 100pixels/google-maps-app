package com.example.ivansandoval.googlemaps;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MarkerAndPolyline
                                extends FragmentActivity
                                                implements OnMapReadyCallback,
                                                                        GoogleMap.OnMapClickListener{

    private GoogleMap mMap;
    private MyGoogleApiClient apiClient;
    private LocationChangedListener locationChangedListener;
    private final String LOG_TAG = "MarkerAndPolyline";

    private final int REQUEST_ACCESS_FINE_LOCATION= 10;
    private LatLng previousLocation,currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_and_polyline);
        requestAccessFineLocationPermission();
        /*
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        */
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.setOnMapClickListener(this);

        locationChangedListener= new LocationChangedListener(mMap);
        apiClient=new MyGoogleApiClient(this);
        //apiClient.connect();

        //Log.d(LOG_TAG,"on onMapReady()-"+apiClient.isConnected());

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        Log.d(LOG_TAG,"on onMapReady()- Final de onMapReady() ");
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if(previousLocation != null){
            drawLine(previousLocation, latLng);
        }
        previousLocation = latLng;
        drawMarker(previousLocation);
    }

    private void drawMarker(LatLng previousLocation){
        mMap.addMarker(new MarkerOptions().position(previousLocation).title("lat: "+previousLocation.latitude+"lng: "+previousLocation.longitude));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(previousLocation));
    }

    private void drawLine(LatLng previousLocation, LatLng currentLocation){
        PolylineOptions lineOptions=new PolylineOptions();
        lineOptions.add(previousLocation);
        lineOptions.add(currentLocation);
        mMap.addPolyline(lineOptions);
    }

    public void requestAccessFineLocationPermission(){
        Log.d(LOG_TAG,"on requestAccessFineLocationPermission()");
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            /*
            Si el permiso ACCESS_FINE_LOCATION no ha sido otorgado significa que la app se
            ejecuta sobre Android 6.0 (API 23) o superior. Es necesario solicitar este permiso al usuario
            en tiempo de ejecución
            */
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_ACCESS_FINE_LOCATION);
        }else{
            /* Cuando la aplicación se ejecuta sobre una versión de android inferior a 6.0,
                el permiso ACCESS_FINE_LOCATION ya ha sido otorgado. Por lo tanto, es posible
                inicializar el mapa y solicitar la ubicación del dispositivo
             */
            initGoogleMap();
        }
    }

    public void initGoogleMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    /*
    * Este método es llamado de manera implícita después de haber solicitado un permiso
    * en tiempo de ejecución. Los parámetros que recibe permiten identificar la petición hecha,
     * así como también, la respuesta del usuario a la solicitud.
    * */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(LOG_TAG,"on onRequestPermissionsResult()");
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_ACCESS_FINE_LOCATION){
            if(grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Location permission granted", Toast.LENGTH_LONG).show();
                initGoogleMap();
            }else{
                // Documentar código
                this.finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }


}
