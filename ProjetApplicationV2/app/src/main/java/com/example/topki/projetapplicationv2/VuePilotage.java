package com.example.topki.projetapplicationv2;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.util.ArrayList;

import java.io.IOException;

public class VuePilotage extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener, GoogleMap.InfoWindowAdapter {

    private GoogleMap mMap;
    ArrayList<LatLng> points = new ArrayList<LatLng>();
    ArrayList<Marker> markers;
    SeekBar s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vue_pilotage);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setInfoWindowAdapter(this);
        // Add a marker in Sydney and move the camera
        LatLng lr = new LatLng(46.151194, -1.161005);
        float zoom = 14.2f;
        //mMap.addMarker(new MarkerOptions().position(lr).title("La Rochelle"));
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lr, zoom));
        markers = new ArrayList<>();
    }

    @Override
    public void onMapClick(LatLng point) {
        points.add(point);
        Marker lastMarker = mMap.addMarker(new MarkerOptions().position(point).title("Point"+points.size()));
        markers.add(lastMarker);
        if(points.size()>1)
        {
            PolylineOptions polyLineOptions = new PolylineOptions();
            polyLineOptions.width(7 * 1);
            polyLineOptions.geodesic(true);
            polyLineOptions.color(Color.RED);
            polyLineOptions.addAll(points);
            Polyline polyline = mMap.addPolyline(polyLineOptions);
            polyline.setGeodesic(true);
        }
        try {
            writeFile();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void onMapLongClick(LatLng point) {
        mMap.clear();
        markers.get(markers.size()-1).remove();
        markers.remove(markers.size()-1);
        points.remove(points.size()-1);
        for (Marker m:markers) {
            mMap.addMarker(new MarkerOptions().position(m.getPosition()).title(m.getTitle()));
        }
        PolylineOptions polyLineOptions = new PolylineOptions();
        polyLineOptions.width(7 * 1);
        polyLineOptions.geodesic(true);
        polyLineOptions.color(Color.RED);
        polyLineOptions.addAll(points);
        Polyline polyline = mMap.addPolyline(polyLineOptions);
        polyline.setGeodesic(true);
    }

    private void writeFile() throws IOException
    {
        JSONObject obj = new JSONObject();
        for (int i=0;i<points.size();i++)
        {
            LatLng latlng = points.get(i);
            try {
                obj.put("Waypoint: ",i);
                obj.put("Latitude: ", latlng.latitude);
                obj.put("Longitude: ", latlng.longitude);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

        }
        FileWriter file = new FileWriter("coord.txt");
        try{
            file.write(obj.toString());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally {
            file.flush();
            file.close();
        }

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();

        return true;
    }

    @Override
    public View getInfoWindow(Marker marker) {

        View v = getLayoutInflater().inflate(R.layout.info_window_layout,null);
        Button b1 = (Button) v.findViewById(R.id.buttonPlus);
        return v;
    }

    @Override
    public View getInfoContents(Marker marker) {

        return null;
    }

}
