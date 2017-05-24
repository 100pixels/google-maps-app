package org.cenidet.cc.publictransit.fragments;

import android.util.Log;
import android.view.MotionEvent;
import android.widget.AdapterView.OnItemSelectedListener;
import android.view.View.OnTouchListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.ivansandoval.googlemaps.R;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.cenidet.cc.publictransit.classes.map.GMap;
import org.cenidet.cc.publictransit.classes.map.Location;
import org.cenidet.cc.publictransit.distancematrix.request.request.DistanceMatrixRequest;
import org.cenidet.cc.publictransit.distancematrix.request.response.Distance;
import org.cenidet.cc.publictransit.distancematrix.request.response.DistanceMatrixResponse;
import org.cenidet.cc.publictransit.graph.Grafo;
import org.cenidet.cc.publictransit.volley.VolleyQueue;
import org.cenidet.cc.publictransit.volley.request.CustomVolleyRequest;
import org.cenidet.cc.publictransit.volley.response.GoogleMapsResponse;
import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;

import cenidet.cc.publictransit.dto.Stop;

import static com.example.ivansandoval.googlemaps.R.id.lblResponse;

public class MapFragment extends Fragment{

    private MapView mapView;
    private GMap googleMap;

    private Button btnOk;
    private Spinner spinnerOrigen;
    private Spinner spinnerDestino;
    private ArrayList<Location> listaOrigen;
    private ArrayList<Location> listaDestino;

    private ArrayAdapter<String> spinnerOrigenAdapter;
    private ArrayAdapter<String> spinnerDestinoAdapter;

    private LatLng origen;
    private LatLng destino;
    private Grafo grafo;

    private String[] arrayOrigen = {"Desde", "Zocalo de Jiutepec","La calera","CBTis 166"};
    private String[] arrayDestino= {"Hacia", "Terminal Pullman La Selva","Mercado Adolfo Lopez Mateos","Hospital #3 IMSS"};

    private RequestQueue requestQueue;
    private Gson gson;

    private final String  LOG_TAG = "MapFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requestQueue = VolleyQueue.getInstance(getActivity()).getRequestQueue();
        gson = new Gson();

        initGraph();

        listaOrigen = initListaOrigen();
        listaDestino = initListaDestino();

        initViews(view, savedInstanceState);
        setSpinnerAdapters();
        setEventListeners();

    }


    private class ButtonEventHandler implements View.OnClickListener{

        @Override
        public void onClick(View v) {
              if(origen == null){
                  Toast.makeText(MapFragment.this.getContext(), "Selecciona lugar de partida", Toast.LENGTH_SHORT).show();
              }else if(destino == null){
                  Toast.makeText(MapFragment.this.getContext(), "Selecciona lugar de destino", Toast.LENGTH_SHORT).show();
              }else{
                  googleMap.clear();
                  googleMap.addMarker(origen, BitmapDescriptorFactory.HUE_BLUE);
                  googleMap.addMarker(destino, BitmapDescriptorFactory.HUE_ORANGE);
                  googleMap.setCameraBounds(origen, destino);

                  encontrarParadaMasCercanaA(origen);
                  //encontrarParadaMasCercanaA(destino);

              }
        }

    }

    private  class SpinnerEventHandler implements OnItemSelectedListener, OnTouchListener {

        boolean userSelects = false;

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.i(LOG_TAG, "onItemSelected - userSelects = "+ userSelects);
            if(userSelects){
                userSelects = false;

                Spinner viewParent = (Spinner)parent;
                Location location;

                if((viewParent.getId() == R.id.spinnerOrigen)){
                    if(position == 0){
                        location = listaOrigen.get(position);
                        origen = null;
                    }else{
                        location = listaOrigen.get(position-1);
                        origen = new LatLng(location.getLatitud(), location.getLongitud());
                    }
                }else{
                    if(position == 0){
                        location = listaDestino.get(position);
                        destino = null;
                    }else{
                        location = listaDestino.get(position-1);
                        destino = new LatLng(location.getLatitud(), location.getLongitud());
                    }
                }

                Log.i(LOG_TAG, "origen = "+ origen);
                Log.i(LOG_TAG, "destino = "+ destino);
                //Toast.makeText(MapFragment.this.getContext(), location.getDescripcion(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            userSelects = true;
            Log.i(LOG_TAG, "onTouch - userSelects = "+ userSelects);
            return false;
        }


        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Toast.makeText(MapFragment.this.getContext(), "onNothingSelected", Toast.LENGTH_SHORT).show();
        }


        //*/

    }

    private ArrayList<Location> initListaOrigen(){
        ArrayList<Location> listaOrigen = new ArrayList<Location>();
        Location origen1 = new Location(18.881441,	-99.176303	,"Zocalo de Jiutepec");
        Location origen2 = new Location(18.859951,	-99.179757,	"La calera");
        Location origen3 = new Location(18.896293,	-99.166606,	"CBTis 166");

        listaOrigen.add(origen1);
        listaOrigen.add(origen2);
        listaOrigen.add(origen3);

        return listaOrigen;
    }

    private ArrayList<Location> initListaDestino(){
        ArrayList<Location> listaDestino = new ArrayList<Location>();
        Location origen1 = new Location(18.930245,	-99.230771,	"Terminal Pullman La Selva");
        Location origen2 = new Location(18.924891,	-99.233131,	"Mercado Adolfo Lopez Mateos");
        Location origen3 = new Location(18.924131,	-99.213982,	"Hospital #3 IMSS");

        listaDestino.add(origen1);
        listaDestino.add(origen2);
        listaDestino.add(origen3);

        return listaDestino;
    }

    private void initViews(View view, Bundle savedInstanceState){
        spinnerOrigen = (Spinner) view.findViewById(R.id.spinnerOrigen);
        spinnerDestino = (Spinner) view.findViewById(R.id.spinnerDestino);
        btnOk = (Button)view.findViewById(R.id.btnOk);

        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        googleMap = GMap.getInstance(getActivity());
        googleMap.init(mapView);
    }

    private void setSpinnerAdapters(){
        spinnerOrigenAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arrayOrigen);
        spinnerDestinoAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arrayDestino);

        spinnerOrigen.setAdapter(spinnerOrigenAdapter);
        spinnerDestino.setAdapter(spinnerDestinoAdapter);
    }

    private void setEventListeners(){
        SpinnerEventHandler spinnerEventHandler = new SpinnerEventHandler();
        ButtonEventHandler buttonEventHandler = new ButtonEventHandler();

        spinnerOrigen.setOnItemSelectedListener(spinnerEventHandler);
        spinnerOrigen.setOnTouchListener(spinnerEventHandler);

        spinnerDestino.setOnItemSelectedListener(spinnerEventHandler);
        spinnerDestino.setOnTouchListener(spinnerEventHandler);

        btnOk.setOnClickListener(buttonEventHandler);
    }

    private void initGraph(){

        String url = "http://10.0.0.6:8085/PublicTransitWS/db/paradas/grafo";

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        Type type = new TypeToken<ArrayList<Stop>>() {}.getType();
                        ArrayList<Stop> verticesGrafo = gson.fromJson(jsonArray.toString(), type);
                        try{
                            grafo = new Grafo(verticesGrafo);
                        }catch(Exception e){
                            Toast.makeText(getActivity(), "Error al inicializar el grafo", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getActivity(), "Error al inicializar el grafo...", Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(request);
    }

    private void encontrarParadaMasCercanaA(LatLng location){
        String url = DistanceMatrixRequest.getUrl(location, grafo.getListaDeVertices());

        CustomVolleyRequest customVolleyRequest = new CustomVolleyRequest(url, DistanceMatrixResponse.class, null, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                DistanceMatrixResponse distancematrix = (DistanceMatrixResponse)response;
                ArrayList<Distance>  distances = distancematrix.getDistanceList();
                for(Distance d: distances){
                    Log.i(LOG_TAG, "value: "+d.getValue());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error al obtener la respuesta DistanceMatrix", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(customVolleyRequest);
    }

}
