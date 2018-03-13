package com.example.topki.projetapplicationv2;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            //Création de la vue
            Button boutonInfos;
            Button  boutonPiloter;

            boutonInfos = new Button(this);
            boutonPiloter = new Button(this);

            VueAccueil vueAccueil = new VueAccueil(this,boutonInfos,boutonPiloter);

            super.onCreate(savedInstanceState);
            setContentView(vueAccueil);
    }
}
