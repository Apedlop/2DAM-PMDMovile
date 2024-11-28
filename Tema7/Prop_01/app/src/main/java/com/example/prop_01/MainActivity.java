package com.example.prop_01;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button definirPreferencias = findViewById(R.id.boton1);
        Button recuperarPreferencias = findViewById(R.id.boton2);

        // Botón para abrir la pantalla de preferencias
        definirPreferencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PreferenciasActivity.class);
                startActivity(intent);
            }
        });

        // Botón para recuperar y mostrar las preferencias
        recuperarPreferencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

                boolean unicoSO = prefs.getBoolean("clave1", false);
                String sistemaOperativo = prefs.getString("sistema_operativo", "No seleccionado");
                String version = prefs.getString("version_sistema", "No especificada");

                // Mostrar los valores en el Logcat
                Log.d("Preferencias", "Único Sistema Operativo: " + unicoSO);
                Log.d("Preferencias", "Sistema Operativo: " + sistemaOperativo);
                Log.d("Preferencias", "Versión del Sistema: " + version);
            }
        });
    }
}