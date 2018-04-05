package com.example.topki.projetapplicationv2;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Julien on 03/04/2018.
 */

public class NMEAParser {

    private String time_utc;
    private String alerte;
    /*private String latitude;
    private String longitude;*/
    private LatLng coordonnee;
    private String vitesse;
    private String cap;
    private String date_du_fix;

    private String checksum;

    public  NMEAParser(String[] trame){
        time_utc = trame[1];
        alerte = trame[2];
        if(this.alerte.equals("V")){
            Log.d("WARNING",  "Alerte du logiciel !");
        }
        /*latitude = trame[3]+","+trame[4];
        longitude = trame[5]+","+trame[6];*/
        coordonnee = convertDMStoDD(trame[3],trame[4].charAt(0),trame[5],trame[6].charAt(0));
        vitesse = trame[7];
        cap = trame[8];
        date_du_fix = trame[9];
        //declinaison_magnetique = trame[10]+","+trame[11];
        checksum = trame[12];
    }

    public String getTime_utc() {
        return time_utc;
    }

    public String getAlerte() {
        return alerte;
    }
/*
    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
    */

    public LatLng getCoordonnee() {
        return coordonnee;
    }

    public String getVitesse() {
        return vitesse;
    }

    public String getCap() {
        return cap;
    }

    public String getDate_du_fix() {
        return date_du_fix;
    }
    /*
        public String getDeclinaison_magnetique() {
            return declinaison_magnetique;
        }
    */
    public String getChecksum() {
        return checksum;
    }

    @Override
    public String toString() {
        return "NMEAParser{" +
                "time_utc='" + time_utc + '\'' +
                ", alerte='" + alerte + '\'' +
                "coordonnee"+coordonnee.toString()+
                ", vitesse='" + vitesse + '\'' +
                ", cap='" + cap + '\'' +
                ", date_du_fix='" + date_du_fix + '\'' +
                ", checksum='" + checksum + '\'' +
                '}';
    }

    public LatLng convertDMStoDD(String lat, char latDir, String lon, char lonDir) {
        double latitude = convertLat(lat);
        double longitude = convertLon(lon);

        if(latDir == 'S'){
            latitude = -latitude;
        }
        if(lonDir == 'W'){
            longitude = -longitude;
        }
        return new LatLng(latitude,longitude);
    }

    private double convertLat(String strLat) {
        double degree;
        double minute;
        double seconde;
        double latitude;
        strLat = strLat.replace(".","");
        double dLat = Double.valueOf(strLat);
        if (strLat.length() ==7) {
            degree = Double.valueOf(strLat.substring(0, 3));
            minute = Double.valueOf(strLat.substring(4, 6));
            seconde = Double.valueOf(strLat.substring(6));
        } else if(strLat.length() == 6) {
            degree = Double.valueOf(strLat.substring(0, 2));
            minute = Double.valueOf(strLat.substring(2, 4));
            seconde = Double.valueOf(strLat.substring(4));
        }else{
            degree = Double.valueOf(strLat.substring(0, 1));
            minute = Double.valueOf(strLat.substring(1, 3));
            seconde = Double.valueOf(strLat.substring(3));
        }
        minute = (minute + (seconde/60)) / 60;
        latitude = degree + (minute);
        return latitude;
    }
    private double convertLon(String strLon) {
        double degree;
        double minute;
        double seconde;
        double latitude;
        degree = Double.valueOf(strLon.substring(0, 3));
        minute = Double.valueOf(strLon.substring(3, 5));
        seconde = Double.valueOf(strLon.substring(5));

        minute = (minute + (seconde/60)) / 60;
        latitude = degree + (minute);
        return latitude;
    }

}
