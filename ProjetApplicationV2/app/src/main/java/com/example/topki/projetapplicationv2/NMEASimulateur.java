package com.example.topki.projetapplicationv2;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Julien on 03/04/2018.
 */

class NMEASimulateur extends AsyncTask<String, Void, String> {

    String host;
    public NMEAParser nmeaParser;

    public NMEASimulateur(String host){
        this.host = host;
        //doInBackground();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            Socket s = new Socket(this.host, 55555);
            DataInputStream is = new DataInputStream(s.getInputStream());
            String responseLine;
            while ((responseLine = is.readLine()) != null) {
                String[] trame_rmc = responseLine.split(",");
                if(trame_rmc[0].equals("$GPRMC")){
                    nmeaParser = new NMEAParser(trame_rmc);
                    Log.d("NMEA: ",nmeaParser.toString());
                    //Log.d("Socket","Server: " + responseLine);
                }
            }
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return null;
    }

    protected void onPostExecute(String result) {

    }
}

