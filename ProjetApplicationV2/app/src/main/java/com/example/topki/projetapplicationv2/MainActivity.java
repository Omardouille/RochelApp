package com.example.topki.projetapplicationv2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity{

    Button boutonInfos;
    Button  boutonPiloter;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            //Création de la vue


            boutonInfos = new Button(this);
            boutonPiloter = new Button(this);

            VueAccueil vueAccueil = new VueAccueil(this, boutonInfos, boutonPiloter);

            super.onCreate(savedInstanceState);
            setContentView(vueAccueil);

            boutonPiloter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, VuePilotage.class));
                }
            });

            boutonInfos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, VueSimulateur.class));
                }
            });

        }
}
