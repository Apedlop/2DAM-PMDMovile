package com.example.botonestexto;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Boton3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boton3);
        TextView rojoAzul = findViewById(R.id.textoRojoAzul);
        rojoAzul.setTextColor(Color.RED);
        rojoAzul.append("\nTexto añadido con Append desde Java");
        rojoAzul.setTextColor(Color.BLUE);
    }
}