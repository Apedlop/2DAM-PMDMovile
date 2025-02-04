package com.example.prop_01;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtener resolución de la pantalla
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int ancho = metrics.widthPixels;
        int alto = metrics.heightPixels;

        // Mostrar en un TextView
        TextView textView = findViewById(R.id.textResolucion);
        textView.setText("Resolución: " + ancho + " x " + alto + " píxeles");
    }
}