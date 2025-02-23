package com.example.botaracelerado;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private DinamicaView dinamica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dinamica = new DinamicaView(this);
        setContentView(dinamica);
        dinamica.iniciarAnimacion();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dinamica.detenerAnimacion();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dinamica.iniciarAnimacion();
    }
}