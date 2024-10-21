package com.example.botonestexto;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Boton4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boton4);
        TextView fuente = findViewById(R.id.textoFuente);
        Typeface fuenteRoja = Typeface.createFromAsset(getAssets(), "fonts/umbrella.ttf");
        fuente.setTypeface(fuenteRoja);
        fuente.setTextColor(Color.RED);
    }
}