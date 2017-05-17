package com.example.ivansandoval.googlemaps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    private Spinner cbIdViaje;
    private Button btnAceptar;

    private String[] idsViaje={"0","1","2","3","4","5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAceptar = (Button)findViewById(R.id.btnAceptar);
        btnAceptar.setOnClickListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, idsViaje);
        cbIdViaje = (Spinner) findViewById(R.id.cbIdViaje);
        cbIdViaje.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {

        int idViaje = Integer.parseInt(cbIdViaje.getSelectedItem().toString());

        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        intent.putExtra("idViaje", idViaje);
        startActivity(intent);

    }
}
