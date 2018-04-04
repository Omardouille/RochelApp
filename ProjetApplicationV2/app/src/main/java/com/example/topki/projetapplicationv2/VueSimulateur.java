package com.example.topki.projetapplicationv2;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class VueSimulateur extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private NMEASimulateur simulateur;
    private TextView latitude;
    private TextView longitude;
    private TextView vitesse;
    private TextView cap;
    private String lat, longi, vit, c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vue_simulateur);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        simulateur = new NMEASimulateur("127.0.0.1");
        latitude = (TextView)findViewById(R.id.latitude);
        longitude = (TextView)findViewById(R.id.longitude);
        vitesse = (TextView)findViewById(R.id.vitesse);
        cap = (TextView)findViewById(R.id.destination);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng lr = new LatLng(46.152994, -1.1560821);
        float zoom = 15.0f;
        mMap.addMarker(new MarkerOptions().position(lr).title("La Rochelle"));
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lr, zoom));
    }

    protected void affichageInfo(){
        longi =  simulateur.nmeaParser.getLongitude();
        lat = simulateur.nmeaParser.getLatitude();
        vit = simulateur.nmeaParser.getVitesse();
        c = simulateur.nmeaParser.getCap();
        latitude.setText(lat);
        longitude.setText(longi);
        vitesse.setText(vit);
        cap.setText(c);
    }


}
