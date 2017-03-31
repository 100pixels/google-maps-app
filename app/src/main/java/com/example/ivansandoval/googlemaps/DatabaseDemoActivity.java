package com.example.ivansandoval.googlemaps;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;




public class DatabaseDemoActivity extends AppCompatActivity {

    private static final int REQUEST_INTERNET_PERMISSION = 10;
    private TextView lblResopnse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_demo);

        lblResopnse = (TextView)findViewById(R.id.lblResponse);

        requestInternetPermission();

    }

    public void registerCustomer(View view){

    }




    public void requestInternetPermission(){
        Log.d("internet","on requestAccessFineLocationPermission()");
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_INTERNET_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("Internet","on onRequestPermissionsResult()");
        if(requestCode == REQUEST_INTERNET_PERMISSION){
            if(grantResults.length == 1 && grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Location permission granted", Toast.LENGTH_LONG).show();
                exitApplication();
            }
        }
    }

    private void exitApplication(){
        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
