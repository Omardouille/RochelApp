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

class NMEASimulateur extends AsyncTask<String, Void, String> {

    String host;
    Socket s;
    DataInputStream is;
    String responseLine;
    String[] trame_rmc;
    VueSimulateur vueSimulateur;

    public NMEAParser nmeaParser;

    public NMEASimulateur(String host, VueSimulateur vueSimulateur){
        this.host = host;
        this.vueSimulateur = vueSimulateur;
        //doInBackground();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            s = new Socket(this.host, 55555);
            is = new DataInputStream(s.getInputStream());
            while ((responseLine = is.readLine()) != null) {
                trame_rmc = responseLine.split(",");
                if(trame_rmc[0].equals("$GPRMC")){
                    NMEAParser nmeaParser = new NMEAParser(trame_rmc);
                    Log.d("NMEA: ",nmeaParser.toString());
                    //Log.d("Socket","Server: " + responseLine);
                    this.vueSimulateur.affichageInfo(nmeaParser);
                }
            }
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }


}

