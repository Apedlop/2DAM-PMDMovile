package com.example.modbotones;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Pantalla2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla2);
    }

    public void boton1 (View v) {
        TextView texto = findViewById(R.id.texto);
        texto.setText("BOTÓN 1 PULSADO");
        texto.setTextColor(Color.RED);
    }

    public void boton2 (View v) {
        TextView texto = findViewById(R.id.texto);
        texto.setText("BOTÓN 2 PULSADO");
        texto.setTextColor(Color.GREEN);
    }
}