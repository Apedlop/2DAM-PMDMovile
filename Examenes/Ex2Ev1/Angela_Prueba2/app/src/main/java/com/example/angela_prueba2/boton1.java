package com.example.angela_prueba2;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class boton1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boton1);

        // Cargar la imagen
        ImageView squareView = findViewById(R.id.imageView);

        // Cargar la animación de movimeinto
        Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.boton1);

        // Iniciar la animación
        squareView.startAnimation(rotateAnimation);

    }
}