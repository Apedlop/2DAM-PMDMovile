package com.example.botaruniforme;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private DinamicaView dinamica;
    private Thread hilo;
    private boolean continuar = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        float s = getResources().getDisplayMetrics().density;
        dinamica = new DinamicaView(this, s);
        setContentView(dinamica);

        hilo = new Thread(dinamica);
        hilo.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        continuar = false;
        dinamica.detenerAnimacion();
    }

    @Override
    protected void onResume() {
        super.onResume();
        continuar = true;
        if (hilo == null || !hilo.isAlive()) {
            hilo = new Thread(dinamica);
            hilo.start();
        }
    }
}
