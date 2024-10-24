package com.example.animaciones.rotar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.animaciones.R;


public class Rotar1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotar1);

        ImageView imagen = findViewById(R.id.my_image);

        // Cargar la animación de rotación
        Animation rotar = AnimationUtils.loadAnimation(this, R.anim.animation_rotar1);

        // Iniciar la animación automáticamente al cargar la actividad
        imagen.startAnimation(rotar);

        // Cargar el cuadrado
//        View squareView = findViewById(R.id.my_square);

        // Cargar la animación de rotación
//        Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.animation_rotar1);

        // Iniciar la animación
//        squareView.startAnimation(rotateAnimation);
    }
}
