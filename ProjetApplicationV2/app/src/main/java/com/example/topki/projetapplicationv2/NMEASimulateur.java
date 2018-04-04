package com.example.topki.projetapplicationv2;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Julien on 03/04/2018.
 */

class NMEASimulateur extends AsyncTask<String, LatLng, Void> {

    String host;
    BufferedReader is;
    String responseLine;

    public NMEAParser nmeaParser;

    public NMEASimulateur(String host){
        this.host = host;
        doInBackground();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            Socket s = new Socket(this.host, 55555);
            is = new BufferedReader(new InputStreamReader(s.getInputStream()));
            while ((responseLine = is.readLine()) != null) {
                String[] trame_rmc = responseLine.split(",");
                if(trame_rmc[0].equals("$GPRMC")){
                    nmeaParser = new NMEAParser(trame_rmc);
                    Log.d("NMEA: ",nmeaParser.toString());
                    //Log.d("Socket","Server: " + responseLine);
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void onPostExecute(String result) {

    }
}

