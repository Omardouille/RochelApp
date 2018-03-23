package com.example.topki.projetapplicationv2;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by TOPki on 12/03/2018.
 */

public class VueAccueil extends LinearLayout
{
    TextView text;

    //Constructeur de la vueAccueil

    public VueAccueil(Context c, Button boutonInfos, Button boutonPiloter)
    {
        super(c);
        this.setOrientation(LinearLayout.VERTICAL);
        text = new TextView(c);

        text.setText("ACCUEIL LES BROS");
        text.setGravity(1);

        boutonInfos.setText("Simulateur NMEA");
        boutonPiloter.setText("Pilotage Main");

        this.addView(text);
        this.addView(boutonInfos);
        this.addView(boutonPiloter);

    }

}
