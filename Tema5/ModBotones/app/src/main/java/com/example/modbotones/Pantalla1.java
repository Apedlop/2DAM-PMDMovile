package com.example.modbotones;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Pantalla1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla1);
    }

    public void boton (View v) {
        TextView texto = (TextView) findViewById(R.id.texto);
        texto.setText("BOTÓN PULSADO");
        texto.setTextColor(Color.RED);
    }
}