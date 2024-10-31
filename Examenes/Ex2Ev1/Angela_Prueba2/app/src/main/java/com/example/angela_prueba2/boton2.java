package com.example.angela_prueba2;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class boton2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boton2);

        // Cargar la imagen
        View squareView = findViewById(R.id.imageView);

        // Cargar la animación de rotación
        Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.boton2);

        // Iniciar la animación
        squareView.startAnimation(rotateAnimation);
    }
}