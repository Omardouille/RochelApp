package com.example.topki.projetapplicationv2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity{

    public Button boutonInfos;
    public Button  boutonPiloter;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            //Création de la vue

            boutonInfos = (Button)findViewById(R.id.button2);
            boutonPiloter = (Button) findViewById(R.id.button3);

            //VueAccueil vueAccueil = new VueAccueil(this, boutonInfos, boutonPiloter);



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
